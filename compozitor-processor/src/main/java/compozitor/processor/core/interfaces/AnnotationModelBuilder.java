package compozitor.processor.core.interfaces;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AnnotationModelBuilder {
	private final ProcessingEnvironment environment;
	
	public AnnotationModel build(AnnotationMirror annotation) {
		return new AnnotationModel(environment, annotation);
	}
}
