package compozitor.processor.core.interfaces;

import java.io.IOException;
import java.io.InputStream;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import com.google.testing.compile.Compilation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "create")
public class FileObjectStringfy {
  private final Compilation compilation;

  public String sourcetoString(String path) {
    String resourceNotFound = String.format("Resource not found %s", path);
    JavaFileObject generatedFile = compilation.generatedFile(StandardLocation.SOURCE_OUTPUT, path)
        .orElseThrow(() -> new RuntimeException(resourceNotFound));
    return this.toString(generatedFile);
  }

  public String toString(JavaFileObject javaFile) {
    try(InputStream input = javaFile.openInputStream()){
      byte[] available = new byte[input.available()];
      input.read(available);
      return new String(available);
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }
}
