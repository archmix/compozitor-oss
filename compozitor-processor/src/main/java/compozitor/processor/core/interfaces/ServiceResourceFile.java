package compozitor.processor.core.interfaces;

import com.google.common.collect.Sets;
import com.google.common.io.CharStreams;

import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.SortedSet;

public class ServiceResourceFile implements AutoCloseable {
  private static final String RESOURCE_FILE_URI_PATTERN = "META-INF/services/%s";

  private final TypeModel providerInterface;

  private final JavaResource serviceFile;

  private final SortedSet<String> services;

  private final ProcessingContext context;

  public ServiceResourceFile(ProcessingContext context, TypeModel providerInterface) {
    this.providerInterface = providerInterface;
    this.serviceFile = JavaResources.create(Resource.ResourceName.create(String.format(RESOURCE_FILE_URI_PATTERN, providerInterface.getQualifiedName())));
    this.services = Sets.newTreeSet();
    this.context = context;
  }

  public String providerInterface() {
    return this.providerInterface.getQualifiedName();
  }

  private OutputStream openFile() {
    try {

      FileObject serviceFile = context.getJavaFiles().resourceFile(this.serviceFile);
      this.loadServiceFile(serviceFile);
      return serviceFile.openOutputStream();
    } catch (IOException e) {
      context.error("Error on opening stream to service file {0} with message {1}.", this.serviceFile, e.getMessage());
      throw new RuntimeException(e);
    }
  }

  public void add(TypeModel service) {
    this.add(service, expectedInterface -> {
      this.context.log(Kind.ERROR, service.getElement(), "You need to implement this type {0}", expectedInterface.getQualifiedName());
    });
  }

  public void add(TypeModel service, ErrorHandler handler) {
    if (!shouldRegister(service)) {
      handler.handle(this.providerInterface);
      return;
    }

    String serviceName = service.getQualifiedName();
    if (this.services.contains(serviceName)) {
      return;
    }
    this.write(serviceName);
  }

  private boolean shouldRegister(TypeModel service) {
    boolean instance = service.getSuperType().instanceOf(this.providerInterface);

    if (!instance) {
      instance = service.getInterfaces().stream().anyMatch((iface) -> {
        return iface.instanceOf(this.providerInterface);
      });
    }

    return instance;
  }

  private void write(String serviceName) {
    this.services.add(serviceName);
  }

  @Override
  public void close() throws Exception {
    if (this.services.isEmpty()) {
      return;
    }

    try (final BufferedWriter serviceFile = new BufferedWriter(new OutputStreamWriter(this.openFile()))) {
      this.context.info("Creating serviceFile");
      for (String serviceName : this.services) {
        serviceFile.write(serviceName);
        serviceFile.newLine();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void loadServiceFile(FileObject serviceFile) {
    InputStream inputStream = null;
    try {
      inputStream = serviceFile.openInputStream();
    } catch (Exception e) {
      return;
    }

    try {
      this.services.addAll(
        CharStreams.readLines(new InputStreamReader(inputStream))
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @FunctionalInterface
  public static interface ErrorHandler {
    void handle(TypeModel providerInterface);
  }
}
