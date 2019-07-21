package compozitor.processor.core.interfaces;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;

public class Annotations extends ModelIterable<AnnotationModel> {
	private final AnnotationModelBuilder builder;
	
	Annotations(ProcessingEnvironment environment) {
		this.builder = new AnnotationModelBuilder(environment);
	}
	
	void add(AnnotationMirror annotation) {
		this.add(this.builder.build(annotation));
	}
}
