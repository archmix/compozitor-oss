package compozitor.processor.core.interfaces;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.text.Normalizer;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class JavaFileName implements Name {
  private final String value;

  public static JavaFileName create(String value) {
    String name = value.replace(".java", "");
    name = Normalizer.normalize(name, Normalizer.Form.NFD);
    name = name.replaceAll("[^a-zA-Z0-9]+", "");
    return new JavaFileName(name);
  }

  @Override
  public String value() {
    return this.value;
  }

  public String toQualifiedName(PackageName packageName) {
    return packageName.accept(this);
  }

  public String toString() {
    return this.value;
  }
}