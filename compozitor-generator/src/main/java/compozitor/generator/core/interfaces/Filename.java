package compozitor.generator.core.interfaces;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(of="value")
@ToString(of = "value")
@RequiredArgsConstructor(staticName = "create")
public class Filename {
  private final String value;
}
