package compozitor.processor.core.interfaces;

import lombok.RequiredArgsConstructor;

import javax.annotation.processing.Filer;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;

@RequiredArgsConstructor(staticName = "create")
public class JavaFiles {
  private final Filer filer;

  public FileObject resourceFile(JavaResource javaResource) {
    try {
      FileObject resourceFile = filer.getResource(StandardLocation.CLASS_OUTPUT, javaResource.getPackageName().toString(), javaResource.getName().toString());
      resourceFile.openInputStream().close();
      return resourceFile;
    } catch (IOException e) {
      return this.createResource(javaResource);
    }
  }

  public FileObject sourceFile(JavaResource javaResource) {
    try {
      return filer.createSourceFile(javaResource.toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private FileObject createResource(JavaResource javaResource) {
    try {
      return filer.createResource(StandardLocation.CLASS_OUTPUT, javaResource.getPackageName().toString(), javaResource.getName().toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
