package compozitor.processor.core.interfaces;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.ExecutableElement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

public class Methods extends ModelIterable<MethodModel> {
  Methods(List<ExecutableElement> methods, JavaModel javaModel) {
    super(new MethodsSupplier(methods, javaModel));
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
