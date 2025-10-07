package compozitor.processor.core.interfaces;

import lombok.RequiredArgsConstructor;

import javax.annotation.processing.Filer;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.nio.file.Paths;

@RequiredArgsConstructor(staticName = "create")
public class JavaFiles {
  private final Filer filer;

  public FileObject resourceFile(JavaResource javaResource) {
    try {
      return this.createResource(javaResource);
    } catch (IOException e) {
      try {
        return this.getResource(javaResource);
      } catch (IOException e2) {
        throw new RuntimeException(e2);
      }
    }
  }

  public FileObject sourceFile(JavaResource javaResource) {
    try {
      return filer.createSourceFile(javaResource.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  FileObject createResource(JavaResource javaResource) throws IOException {
      return filer.createResource(StandardLocation.CLASS_OUTPUT, javaResource.getPackageName().toString(), javaResource.getName().toString());
  }

  FileObject getResource(JavaResource javaResource) throws IOException {
    return filer.getResource(StandardLocation.CLASS_OUTPUT, javaResource.getPackageName().toString(), javaResource.getName().toString());
  }
}
