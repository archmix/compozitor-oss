package compozitor.processor.core.interfaces;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.List;

public class Interfaces extends ModelIterable<TypeModel> {

  Interfaces(List<? extends TypeMirror> interfaces, JavaModel javaModel) {
    super(new InterfacesSupplier(interfaces, javaModel));
  }

  @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
  static class InterfacesSupplier implements ListSupplier<TypeModel> {
    private final List<? extends TypeMirror> interfaces;
    private final JavaModel javaModel;

    @Override
    public List<TypeModel> get() {
      List<TypeModel> models = new ArrayList<>();
      this.interfaces.forEach(iface -> {
        models.add(this.javaModel.getInterface(iface));
      });

      return models;
    }
  }
}
