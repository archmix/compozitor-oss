package compozitor.engine.core.interfaces;

import compozitor.generator.core.interfaces.GeneratedCode;

@FunctionalInterface
public interface EngineListener {
	void accept(GeneratedCode code);
}
