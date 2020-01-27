package compozitor.processor.core.interfaces;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import java.util.List;
import java.util.Map;

public class AnnotationModel extends Model<AnnotationMirror> {

  AnnotationModel(ProcessingContext context, AnnotationMirror annotation) {
    super(context, annotation);
  }

  public AnnotationValue getValue(String key) {
    Map<? extends ExecutableElement, ? extends AnnotationValue> values =
      context.getElementValuesWithDefaults(this.element);
    for (ExecutableElement keyElement : values.keySet()) {
      if (keyElement.getSimpleName().contentEquals(key)) {
        return values.get(keyElement);
      }
    }

    return null;
  }

  public List<AnnotationValue> getValues(String key){
    return (List<AnnotationValue>) this.getValue(key).getValue();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (obj instanceof TypeModel) {
      TypeModel type = (TypeModel) obj;
      return this.context.isSameType(this.element.getAnnotationType(), type.getElement().asType());
    }

    if (obj instanceof AnnotationModel) {
      AnnotationModel annotation = (AnnotationModel) obj;
      return this.context.isSameType(this.element.getAnnotationType(),
        annotation.element.getAnnotationType());
    }

    return super.equals(obj);
  }

  public boolean instanceOf(AnnotationModel annotation) {
    return this.context.isAssignable(this.element.getAnnotationType(),
      annotation.element.getAnnotationType());
  }

  public boolean instanceOf(TypeModel type) {
    return this.context.isAssignable(this.element.getAnnotationType(), type.getElement().asType());
  }
}
