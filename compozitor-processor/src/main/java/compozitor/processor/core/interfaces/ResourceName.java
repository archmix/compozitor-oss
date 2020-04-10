package compozitor.processor.core.interfaces;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "create")
public class ResourceName implements Name {
  private final String value;

  @Override
  public String value() {
    return this.value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
