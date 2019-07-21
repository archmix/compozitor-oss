package compozitor.engine.core.interfaces;

import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FieldModel {
	private final Name name;
	
	private final TypeMirror type;
	
	public static FieldModel valueOf(VariableElement element) {
		element.getAnnotationMirrors().forEach(annotation ->{
			annotation.getAnnotationType();
		});
		return new FieldModel(element.getSimpleName(), element.asType());
	}
	
	@Override
	public String toString() {
		return this.name.toString();
	}
}
