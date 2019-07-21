package compozitor.processor.core.interfaces;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;

public class Arguments extends ModelIterable<ArgumentModel> {
	private final ArgumentModelBuilder builder;
	
	public Arguments(ProcessingEnvironment environment) {
		this.builder = new ArgumentModelBuilder(environment);
	}
	
	void add(VariableElement element) {
		this.add(this.builder.build(element));
	}
}
