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
      FileObject resourceFile = this.createResource(javaResource);
      return resourceFile;
    } catch (IOException e) {
      return this.getResource(javaResource);
    }
  }

  public FileObject sourceFile(JavaResource javaResource) {
    try {
      return filer.createSourceFile(javaResource.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private FileObject createResource(JavaResource javaResource) throws IOException {
      return filer.createResource(StandardLocation.CLASS_OUTPUT, javaResource.getPackageName().toString(), javaResource.getName().toString());
  }

  private FileObject getResource(JavaResource javaResource) {
    try {
      return filer.getResource(StandardLocation.CLASS_OUTPUT, javaResource.getPackageName().toString(), javaResource.getName().toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
