package compozitor.processor.core.interfaces;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "create")
public class PackageName implements Name {
  private final String value;

  public static PackageName root() {
    return PackageName.create("");
  }

  public String accept(Name name) {
    if (this.value.isEmpty()) {
      return name.value();
    }
    return new StringBuilder(this.value).append(".").append(name.value()).toString();
  }

  @Override
  public String value() {
    return this.value;
  }

  public String toString() {
    return this.value;
  }
}