package compozitor.processor.core.interfaces;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.VariableElement;
import java.util.ArrayList;
import java.util.List;

public class Fields extends ModelIterable<FieldModel> {

  Fields(List<VariableElement> fields, JavaModel javaModel) {
    super(new FieldsSupplier(fields, javaModel));
  }

  @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
  static class FieldsSupplier implements ListSupplier<FieldModel> {
    private final List<VariableElement> fields;

    private final JavaModel javaModel;

    @Override
    public List<FieldModel> get() {
      List<FieldModel> models = new ArrayList<>();

      this.fields.forEach(element -> {
        models.add(javaModel.getField(element));
      });

      return models;
    }
  }

}
