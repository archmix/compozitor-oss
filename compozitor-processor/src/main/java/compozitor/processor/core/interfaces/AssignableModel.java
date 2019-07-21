package compozitor.processor.core.interfaces;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

class AssignableModel<M extends Element> extends Model<M>{

	AssignableModel(ProcessingEnvironment environment, M element) {
		super(environment, element);
	}
	
	public boolean instanceOf(TypeModel type) {
		return this.instanceOf(type.element.asType());
	}
	
	boolean instanceOf(TypeMirror type) {
		return this.types.isAssignable(this.element.asType(), type);
	}
}
