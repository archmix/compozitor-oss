package compozitor.processor.core.interfaces;

import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

public class AnnotationModel extends Model<AnnotationMirror> {

	AnnotationModel(ProcessingEnvironment environment, AnnotationMirror annotation) {
		super(environment, annotation);
	}
	
	public AnnotationValue getValue(String key){
		Map<? extends ExecutableElement, ? extends AnnotationValue> values = this.element.getElementValues();
		for(ExecutableElement keyElement : values.keySet()) {
			if(keyElement.getSimpleName().contentEquals(key)) {
				return values.get(keyElement);
			}
		}
		
		return null;
	}
	
	public boolean instanceOf(TypeMirror type) {
		return this.types.isAssignable(this.element.getAnnotationType(), type);
	}
}
