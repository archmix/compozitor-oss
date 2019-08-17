package compozitor.processor.core.interfaces;

import javax.lang.model.element.AnnotationMirror;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AnnotationModelBuilder {
  private final ProcessingContext context;

  public AnnotationModel build(AnnotationMirror annotation) {
    return new AnnotationModel(context, annotation);
  }
}
