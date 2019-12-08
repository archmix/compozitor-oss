package compozitor.processor.core.interfaces;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;

public class TypeParameters extends ModelIterable<TypeModel> {

  TypeParameters(List<? extends TypeMirror> parameters, JavaModel javaModel) {
    super(new TypeParametersSupplier(parameters, javaModel));
  }

  @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
  static class TypeParametersSupplier implements ListSupplier<TypeModel> {
    private final List<? extends TypeMirror> parameters;
    private final JavaModel javaModel;

    @Override
    public List<TypeModel> get() {
      List<TypeModel> models = new ArrayList<>();
      this.parameters.forEach(type -> {
        models.add(this.javaModel.getType(type));
      });

      return models;
    }
  }
}
