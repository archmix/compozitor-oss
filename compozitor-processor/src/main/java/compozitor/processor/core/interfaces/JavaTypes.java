package compozitor.processor.core.interfaces;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

public class JavaTypes {
	private final ProcessingEnvironment environment;
	
	private final TypeModelBuilder builder;
	
	private final Map<String, TypeElement> cache;
	
	JavaTypes(ProcessingEnvironment environment) {
		this.environment = environment;
		this.builder = new TypeModelBuilder(environment);
		this.cache = new HashMap<>();
	}
	
	public static JavaTypes create(ProcessingEnvironment environment) {
		return new JavaTypes(environment);
	}
	
	public TypeModel getObjectType() {
		return this.getType("java.lang.Object");
	}

	public TypeModel getType(String name) {
		TypeElement element = this.element(name);
		return this.builder.build(element);
	}
	
	public TypeModel getAnnotationType(String name) {
		TypeElement element = this.element(name);
		return this.builder.build(element);
	}
	
	private TypeElement element(String name) {
		TypeElement element = this.cache.get(name);
		if(element == null) {
			element = this.environment.getElementUtils().getTypeElement(name);
			this.cache.put(name, element);
		}
		
		return element;
	}
}
