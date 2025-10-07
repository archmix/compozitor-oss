package compozitor.processor.core.interfaces;

import com.google.common.io.CharStreams;

import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.SortedSet;
import java.util.TreeSet;

public class ServiceResourceFile {
  private static final String RESOURCE_FILE_URI_PATTERN = "META-INF/services/%s";

  private final TypeModel providerInterface;

  private final JavaResource serviceFile;

  private final SortedSet<String> services;

  private final ProcessingContext context;

  public ServiceResourceFile(ProcessingContext context, TypeModel providerInterface) {
    this.providerInterface = providerInterface;
    this.serviceFile = JavaResources.create(Resource.ResourceName.create(String.format(RESOURCE_FILE_URI_PATTERN, providerInterface.getQualifiedName())));
    this.services = new TreeSet<>();
    this.context = context;
  }

  public void preload() {
    if (this.services.isEmpty()) {
      return;
    }

    try {
      this.context.info("Preloading service file {0}", this.serviceFile);

      FileObject serviceFile = context.getJavaFiles().getResource(this.serviceFile);
      var inputStream = serviceFile.openInputStream();
      this.services.addAll(
        CharStreams.readLines(new InputStreamReader(inputStream))
      );
    } catch (IOException e) {
      context.info("File {0} not found, creating it.", this.serviceFile);
    }
  }

  public String providerInterface() {
    return this.providerInterface.getQualifiedName();
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
    this.services.add(serviceName);
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

  public void close() {
    if (this.services.isEmpty()) {
      return;
    }

    try (final BufferedWriter serviceFile = new BufferedWriter(new OutputStreamWriter(this.openFile()))) {
      this.context.info("Writing content to files");
      for (String serviceName : this.services) {
        serviceFile.write(serviceName);
        serviceFile.newLine();
      }
    } catch (IOException e) {
      this.context.error(e.getMessage());
    }
  }

  private OutputStream openFile() {
    try {
      FileObject serviceFile = context.getJavaFiles().createResource(this.serviceFile);
      context.info("Opening stream to service file {0}", this.serviceFile);
      return serviceFile.openOutputStream();
    } catch (IOException e) {
      context.error("Error on opening stream to service file {0} with message {1}.", this.serviceFile, e.getMessage());
      throw new RuntimeException(e);
    }
  }

  @FunctionalInterface
  public static interface ErrorHandler {
    void handle(TypeModel providerInterface);
  }
}
