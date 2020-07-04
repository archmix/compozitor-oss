package compozitor.processor.core.interfaces;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.AnnotationMirror;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Annotations extends ModelIterable<AnnotationModel> {
  private final JavaModel javaModel;

  Annotations(List<? extends AnnotationMirror> annotations, JavaModel javaModel) {
    super(new AnnotationsSupplier(annotations, javaModel));
    this.javaModel = javaModel;
  }

  public Optional<AnnotationModel> getBy(TypeModel typeModel) {
    return this.get(predicateOf(typeModel));
  }

  public Optional<AnnotationModel> getBy(Class<? extends Annotation> typeClass) {
    TypeModel typeModel = this.javaModel.getType(typeClass);
    return this.get(predicateOf(typeModel));
  }

  public Optional<AnnotationModel> getBy(TypeName typeName) {
    TypeModel typeModel = this.javaModel.getType(typeName);
    return this.get(predicateOf(typeModel));
  }

  private Predicate<AnnotationModel> predicateOf(TypeModel typeModel) {
    return (annotation) -> annotation.equals(typeModel);
  }

  @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
  static class AnnotationsSupplier implements ListSupplier<AnnotationModel> {
    private final List<? extends AnnotationMirror> annotations;
    private final JavaModel javaModel;

    @Override
    public List<AnnotationModel> get() {
      List<AnnotationModel> models = new ArrayList<>();
      this.annotations.forEach(annotation -> {
        models.add(this.javaModel.getAnnotation(annotation));
      });

      return models;
    }
  }
}
