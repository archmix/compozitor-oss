package compozitor.processor.core.interfaces;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnnotationModel extends Model<AnnotationMirror> {

  private AnnotationModel(ProcessingContext context, AnnotationMirror annotation) {
    super(context, annotation);
  }

  static AnnotationModel create(ProcessingContext context, AnnotationMirror annotation){
    return new AnnotationModel(context, annotation);
  }

  public AnnotationModel annotation(String key){
    AnnotationMirror mirror = this.value(key);
    return this.getAnnotation(mirror);
  }

  public List<AnnotationModel> annotations(String key){
    List<AnnotationMirror> mirrors = this.values(key);
    return mirrors.stream().map(this::getAnnotation).collect(Collectors.toList());
  }

  private AnnotationModel getAnnotation(AnnotationMirror mirror){
    return this.context.getJavaModel().getAnnotation(mirror);
  }

  public String enumValue(String key) {
    return this.value(key, true);
  }

  public List<String> enumValues(String key) {
    return this.values(key, true);
  }

  public TypeModel typeValue(String key){
    TypeMirror mirror = this.value(key);
    return this.getTypeModel(mirror);
  }

  public List<TypeModel> typeValues(String key){
    List<TypeMirror> mirrors = this.values(key);
    return mirrors.stream().map(this::getTypeModel).collect(Collectors.toList());
  }

  private TypeModel getTypeModel(TypeMirror typeMirror){
    return this.context.getJavaModel().getType(typeMirror);
  }

  public <T> T value(String key){
    return this.value(key, false);
  }

  private <T> T value(String key, boolean usesToString){
    Object value = this.getValue(key).getValue();
    if(usesToString){
      value = value.toString();
    }
    return (T) value;
  }

  private AnnotationValue getValue(String key) {
    Map<? extends ExecutableElement, ? extends AnnotationValue> values =
      context.getElementValuesWithDefaults(this.element);
    for (ExecutableElement keyElement : values.keySet()) {
      if (keyElement.getSimpleName().contentEquals(key)) {
        return values.get(keyElement);
      }
    }

    return null;
  }

  public <T> List<T> values(String key){
    return this.values(key, false);
  }

  private <T> List<T> values(String key, boolean usesToString){
    List<T> values = new ArrayList<>();

    this.getValues(key).forEach(annotationValue -> {
      Object value = annotationValue.getValue();
      if(usesToString){
        value = value.toString();
      }
      values.add((T) value);
    });

    return values;
  }

  private List<AnnotationValue> getValues(String key){
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
