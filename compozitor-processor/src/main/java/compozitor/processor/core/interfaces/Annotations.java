package compozitor.processor.core.interfaces;

import javax.lang.model.element.AnnotationMirror;

public class Annotations extends ModelIterable<AnnotationModel> {
  private final AnnotationModelBuilder builder;

  Annotations(ProcessingContext context) {
    this.builder = new AnnotationModelBuilder(context);
  }

  void add(AnnotationMirror annotation) {
    this.add(this.builder.build(annotation));
  }
}
