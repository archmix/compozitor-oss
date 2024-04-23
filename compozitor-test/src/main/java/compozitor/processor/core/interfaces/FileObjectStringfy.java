package compozitor.processor.core.interfaces;

import com.google.testing.compile.Compilation;
import lombok.RequiredArgsConstructor;

import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

@RequiredArgsConstructor(staticName = "create")
class FileObjectStringfy {
  private final Compilation compilation;

  public static String toString(FileObject javaFile) {
    try (InputStream input = javaFile.openInputStream()) {
      byte[] available = new byte[input.available()];
      input.read(available);
      return new String(available);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public String serviceFile(Class<?> serviceClass) {
    return this.resourceToString(new StringBuilder("META-INF/services/").append(serviceClass.getName()).toString()).toString();
  }

  public String sourceToString(String path) {
    String resourceNotFound = String.format("Resource not found %s", path);
    FileObject generatedFile = compilation.generatedFile(StandardLocation.SOURCE_OUTPUT, path)
      .orElseThrow(() -> new RuntimeException(resourceNotFound));
    return this.toString(generatedFile);
  }

  public String resourceToString(String path) {
    String resourceNotFound = String.format("Resource not found %s", path);
    FileObject generatedFile = compilation.generatedFile(StandardLocation.CLASS_OUTPUT, path)
      .orElseThrow(() -> new RuntimeException(resourceNotFound));
    return this.toString(generatedFile);
  }
}
