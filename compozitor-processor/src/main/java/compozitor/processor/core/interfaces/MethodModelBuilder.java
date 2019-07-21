package compozitor.processor.core.interfaces;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;

public class MethodModelBuilder {
	private final ProcessingEnvironment environment;
	
	private final TypeModelBuilder typeBuilder;
	
	MethodModelBuilder(ProcessingEnvironment environment) {
		this.environment = environment;
		this.typeBuilder = new TypeModelBuilder(environment);
	}
	
	public MethodModel build(ExecutableElement element) {
		Annotations annotations = new Annotations(this.environment);
		element.getAnnotationMirrors().forEach(annotations::add);
		
		Modifiers modifiers = new Modifiers(element.getModifiers());
		
		TypeModel returnType = this.typeBuilder.build(element.getReturnType());
		
		Arguments arguments = new Arguments(this.environment);
		element.getParameters().forEach(arguments::add);
		
		return new MethodModel(environment, element, annotations, modifiers, returnType, arguments);
	}
}
