package compozitor.processor.core.interfaces;

public interface Logger {
  void info(String message, Object... args);

  void error(String message, Object... args);

  void warning(String message, Object... args);

  void mandatoryWarning(String message, Object... args);
}
