package compozitor.generator.core.interfaces;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class QualifiedName {
  private final String value;

  public static QualifiedName create(Namespace namespace, Filename filename){
    return new QualifiedName(namespace.accept(filename));
  }
}
