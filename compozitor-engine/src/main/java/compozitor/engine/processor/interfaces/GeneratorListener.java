package compozitor.engine.processor.interfaces;

@FunctionalInterface
public interface GeneratorListener {
  void accept(String sourceCode);
}
