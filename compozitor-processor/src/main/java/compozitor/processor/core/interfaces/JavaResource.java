package compozitor.processor.core.interfaces;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "create")
@Getter
public final class JavaResource {
  private final PackageName packageName;

  private final Name name;

  public String toString() {
    return packageName.accept(this.name);
  }
}
