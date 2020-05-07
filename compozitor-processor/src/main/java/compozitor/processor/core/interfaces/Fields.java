package compozitor.processor.core.interfaces;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.VariableElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Fields extends ModelIterable<FieldModel> {

  Fields(List<VariableElement> fields, Predicate<FieldModel> fieldPredicate, JavaModel javaModel) {
    super(new FieldsSupplier(fields, fieldPredicate, javaModel));
  }

  public static Predicate<FieldModel> constant() {
    return fieldModel -> fieldModel.getConstant();
  }

  public static Predicate<FieldModel> regular() {
    return fieldModel -> !fieldModel.getConstant();
  }

  public Optional<FieldModel> get(String name) {
    return this.stream()
      .filter(field -> field.getName().equalsIgnoreCase(name))
      .findFirst();
  }

  @RequiredArgsConstructor(access = AccessLevel.PACKAGE)
  static class FieldsSupplier implements ListSupplier<FieldModel> {
    private final List<VariableElement> fields;

    private final Predicate<FieldModel> fieldPredicate;

    private final JavaModel javaModel;

    @Override
    public List<FieldModel> get() {
      List<FieldModel> models = new ArrayList<>();

      this.fields.forEach(element -> {
        FieldModel fieldModel = javaModel.getField(element);
        if (this.fieldPredicate.test(fieldModel)) {
          models.add(fieldModel);
        }
      });

      return models;
    }
  }

}
