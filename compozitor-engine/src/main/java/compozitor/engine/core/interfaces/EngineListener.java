package compozitor.engine.core.interfaces;

import compozitor.generator.core.interfaces.GeneratedCode;

public interface EngineListener {
	void accept(GeneratedCode code);
	
	void notify(Exception exception);
}
