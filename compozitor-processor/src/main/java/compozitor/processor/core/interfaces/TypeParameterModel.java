package compozitor.processor.core.interfaces;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;

public class TypeParameterModel extends TypeModel {
	private final TypeParameterElement parameter;

	TypeParameterModel(ProcessingEnvironment environment, TypeParameterElement element, PackageModel packageModel,
			Annotations annotations, Modifiers modifiers, TypeModel superType, Interfaces interfaces, Fields fields,
			Methods methods) {
		super(environment, (TypeElement) element.getEnclosingElement(), packageModel, annotations, modifiers, superType, interfaces, fields, methods);
		this.parameter = element;
	}

}
