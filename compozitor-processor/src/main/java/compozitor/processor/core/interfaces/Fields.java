package compozitor.processor.core.interfaces;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.VariableElement;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Fields extends ModelIterable<FieldModel> {
  private final JavaModel javaModel;

  Fields(List<VariableElement> fields, Predicate<FieldModel> fieldPredicate, JavaModel javaModel) {
    super(new FieldsSupplier(fields, fieldPredicate, javaModel));
    this.javaModel = javaModel;
  }

  public static Predicate<FieldModel> constant() {
    return fieldModel -> fieldModel.getConstant();
  }

  public static Predicate<FieldModel> field() {
    return fieldModel -> true;
  }

  public List<FieldModel> annotatedWith(Class<? extends Annotation> annotationClass) {
    return this.stream().filter(fieldModel -> fieldModel.getAnnotations().getBy(annotationClass).isPresent()).collect(Collectors.toList());
  }

  public List<FieldModel> getConstants() {
    return this.stream().filter(this.constant()).collect(Collectors.toList());
  }

  public List<FieldModel> getAllBy(Class<?> typeClass) {
    return this.getAllBy(this.javaModel.getType(typeClass.getName()));
  }

  public List<FieldModel> getAllBy(TypeModel typeModel) {
    return this.stream().filter(fieldModel -> fieldModel.getType().equals(typeModel)).collect(Collectors.toList());
  }

  public Optional<FieldModel> getBy(Name name) {
    return this.stream()
      .filter(field -> field.getName().equalsIgnoreCase(name.value()))
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
