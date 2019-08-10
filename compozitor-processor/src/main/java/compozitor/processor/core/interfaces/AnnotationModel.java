package compozitor.processor.core.interfaces;

import java.util.Map;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;

public class AnnotationModel extends Model<AnnotationMirror> {

  AnnotationModel(ProcessingEnvironment environment, AnnotationMirror annotation) {
    super(environment, annotation);
  }

  public AnnotationValue getValue(String key) {
    Map<? extends ExecutableElement, ? extends AnnotationValue> values =
        this.element.getElementValues();
    for (ExecutableElement keyElement : values.keySet()) {
      if (keyElement.getSimpleName().contentEquals(key)) {
        return values.get(keyElement);
      }
    }

    return null;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (obj instanceof TypeModel) {
      TypeModel type = (TypeModel) obj;
      return this.types.isSameType(this.element.getAnnotationType(), type.element.asType());
    }

    if (obj instanceof AnnotationModel) {
      AnnotationModel annotation = (AnnotationModel) obj;
      return this.types.isSameType(this.element.getAnnotationType(),
          annotation.element.getAnnotationType());
    }

    return super.equals(obj);
  }

  public boolean instanceOf(AnnotationModel annotation) {
    return this.types.isAssignable(this.element.getAnnotationType(),
        annotation.element.getAnnotationType());
  }

  public boolean instanceOf(TypeModel type) {
    return this.types.isAssignable(this.element.getAnnotationType(), type.element.asType());
  }
}
