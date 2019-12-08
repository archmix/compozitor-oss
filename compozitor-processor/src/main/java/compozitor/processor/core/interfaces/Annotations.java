package compozitor.processor.core.interfaces;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.AnnotationMirror;
import java.util.ArrayList;
import java.util.List;

public class Annotations extends ModelIterable<AnnotationModel> {

  Annotations(List<? extends AnnotationMirror> annotations, JavaModel javaModel) {
    super(new AnnotationsSupplier(annotations, javaModel));
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
