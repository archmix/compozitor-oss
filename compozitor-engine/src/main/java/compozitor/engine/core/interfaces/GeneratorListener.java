package compozitor.engine.core.interfaces;

@FunctionalInterface
public interface GeneratorListener {
  void accept(String sourceCode);
}
