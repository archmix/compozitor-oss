package compozitor.generator.core.interfaces;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor(staticName = "create")
@EqualsAndHashCode(of = "value")
public class Namespace {
  private static final String SLASH = "/";
  private static final String DOT = ".";

  private final String value;

  private final Path path;

  public static Namespace create(Path path) {
    return Namespace.create(path.toString().replace(SLASH, DOT), path);
  }

  public static Namespace create(String value){
    return Namespace.create(Paths.get(value.replace(DOT, SLASH)));
  }

  String accept(Filename filename){
    return new StringBuilder(this.value).append(DOT).append(filename).toString();
  }

  public Path toPath() {
    return this.path;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
