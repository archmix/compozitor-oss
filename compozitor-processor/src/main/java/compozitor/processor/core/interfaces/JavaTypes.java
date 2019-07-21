package compozitor.processor.core.interfaces;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

public class JavaTypes {
	private final ProcessingEnvironment environment;
	
	private final TypeModelBuilder builder;
	
	JavaTypes(ProcessingEnvironment environment) {
		this.environment = environment;
		this.builder = new TypeModelBuilder(environment);
	}
	
	public TypeModel getObjectType() {
		return this.getType("java.lang.Object");
	}

	public TypeModel getType(String name) {
		TypeElement type = this.environment.getElementUtils().getTypeElement(name);
		return this.builder.build(type);
	}
}
