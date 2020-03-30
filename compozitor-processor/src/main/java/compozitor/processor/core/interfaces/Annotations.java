package compozitor.processor.core.interfaces;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.AnnotationMirror;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Annotations extends ModelIterable<AnnotationModel> {
  private final JavaModel  javaModel;

  Annotations(List<? extends AnnotationMirror> annotations, JavaModel javaModel) {
    super(new AnnotationsSupplier(annotations, javaModel));
    this.javaModel = javaModel;
  }

  public Optional<AnnotationModel> getBy(TypeModel typeModel){
    return this.get(predicateOf(typeModel.getQualifiedName())) ;
  }

  public Optional<AnnotationModel> getBy(Class<?> typeClass) {
    return this.get(predicateOf(typeClass.getName()));
  }

  public Optional<AnnotationModel> getBy(TypeName typeName) {
    return this.get(predicateOf(typeName.toString()));
  }

  public Optional<AnnotationModel> getBy(String typeName) {
    return this.get(predicateOf(typeName.toString()));
  }

  private Predicate<AnnotationModel> predicateOf(String type) {
    TypeModel typeModel = javaModel.getType(type);
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
