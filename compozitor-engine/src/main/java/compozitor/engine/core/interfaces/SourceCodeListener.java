package compozitor.engine.core.interfaces;

@FunctionalInterface
public interface SourceCodeListener {
  void accept(String sourceCode);
}
