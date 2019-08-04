package compozitor.engine.core.interfaces;

@FunctionalInterface
public interface StateHandler {
	void accept(IllegalStateException ise);
}
