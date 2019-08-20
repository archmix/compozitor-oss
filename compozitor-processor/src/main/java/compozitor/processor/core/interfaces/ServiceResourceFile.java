package compozitor.processor.core.interfaces;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.SortedSet;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import com.google.common.collect.Sets;
import com.google.common.io.CharStreams;
import com.google.common.io.Closer;

public class ServiceResourceFile implements AutoCloseable {
  private static final String RESOURCE_FILE_URI_PATTERN = "META-INF/services/%s";

  private final TypeModel providerInterface;

  private final String resourceFileUri;

  private final SortedSet<String> services;

  private final BufferedWriter serviceFile;

  private final ProcessingContext context;

  public ServiceResourceFile(ProcessingContext context, TypeModel providerInterface) {
    this.providerInterface = providerInterface;
    this.resourceFileUri =
        String.format(RESOURCE_FILE_URI_PATTERN, providerInterface.getQualifiedName());
    this.services = Sets.newTreeSet();
    this.context = context;
    this.serviceFile = new BufferedWriter(new OutputStreamWriter(this.openFile()));
  }

  private OutputStream openFile() {
    try {
      FileObject serviceFile =
          context.getResource(StandardLocation.CLASS_OUTPUT, "", this.resourceFileUri);
      this.loadServiceFile(serviceFile.openInputStream());
      return serviceFile.openOutputStream();
    } catch (IOException e) {
      context.info("Services file does not exist {0}. I will create a new one.",
          this.resourceFileUri);
    }

    try {
      return context.createResource(StandardLocation.CLASS_OUTPUT, "", this.resourceFileUri)
          .openOutputStream();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void add(TypeModel service, ErrorHandler handler) {
    if (!service.getInterfaces().contains(this.providerInterface)) {
      handler.handle(this.providerInterface, service);
      return;
    }

    String serviceName = service.getQualifiedName();
    if (this.services.contains(serviceName)) {
      return;
    }
    this.write(serviceName);
  }

  private void write(String serviceName) {
    try {
      this.services.add(serviceName);
      this.serviceFile.write(serviceName);
      this.serviceFile.newLine();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void close() throws Exception {
    this.serviceFile.flush();
    this.serviceFile.close();
  }

  private void loadServiceFile(InputStream input) throws IOException {
    Closer closer = Closer.create();
    Reader reader = closer.register(new InputStreamReader(input));
    this.services.addAll(CharStreams.readLines(reader));
    closer.close();
  }

  @FunctionalInterface
  public static interface ErrorHandler {
    void handle(TypeModel providerInterface, TypeModel service);
  }
}
