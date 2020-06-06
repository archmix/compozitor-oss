package compozitor.template.core.infra;

import java.util.Optional;

public class Classes {
  public static Optional<Class<?>> find(String className) {
    try {
      return Optional.of(Class.forName(className));
    } catch (ClassNotFoundException e) {
      return Optional.empty();
    }
  }
}
