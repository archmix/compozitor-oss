package compozitor.generator.core.interfaces;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.nio.file.Path;

@Getter
@Setter
public class GeneratedCode {
  private InputStream content;

  private Filename fileName;

  private Path path;

  private Namespace namespace;

  private QualifiedName qualifiedName;

  private String simpleName;

  private Boolean resource;

  private Boolean testArtifact;
}
