package compozitor.processor.core.interfaces;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Methods extends ModelIterable<MethodModel> {
  private final JavaModel javaModel;

  Methods(List<ExecutableElement> methods, JavaModel javaModel) {
    super(new MethodsSupplier(methods, javaModel));
    this.javaModel = javaModel;
  }

  public List<MethodModel> annotatedWith(Class<? extends Annotation> annotationClass) {
    return this.stream().filter(methodModel -> methodModel.getAnnotations().getBy(annotationClass).isPresent()).collect(Collectors.toList());
  }

  public Optional<MethodModel> getBy(Name name) {
    return this.stream().filter(methodModel -> methodModel.getName().equalsIgnoreCase(name.value())).findFirst();
  }

  @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
  static class MethodsSupplier implements ListSupplier<MethodModel> {
    private final List<ExecutableElement> methods;

    private final JavaModel javaModel;

    @Override
    public List<MethodModel> get() {
      List<MethodModel> models = new ArrayList<>();

      this.methods.forEach(element -> {
        models.add(javaModel.getMethod(element));
      });

      return models;
    }
  }
}
