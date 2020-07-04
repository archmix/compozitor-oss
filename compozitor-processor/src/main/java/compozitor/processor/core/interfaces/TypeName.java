package compozitor.processor.core.interfaces;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "create")
public class TypeName {
  private final String value;

  public String value() {
    return this.value;
  }

  public String toString() {
    return this.value;
  }
}
