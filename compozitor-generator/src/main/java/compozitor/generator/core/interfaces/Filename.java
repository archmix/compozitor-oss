package compozitor.generator.core.interfaces;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(of="value")
@RequiredArgsConstructor(staticName = "create")
public class Filename {
  private final String value;

  @Override
  public String toString() {
    return this.value;
  }
}
