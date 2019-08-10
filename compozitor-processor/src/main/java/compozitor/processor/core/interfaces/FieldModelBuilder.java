package compozitor.processor.core.interfaces;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class FieldModelBuilder {
  private final ProcessingEnvironment environment;

  private final TypeModelBuilder typeBuilder;

  FieldModelBuilder(ProcessingEnvironment environment) {
    this.environment = environment;
    this.typeBuilder = new TypeModelBuilder(environment);
  }

  public FieldModel build(VariableElement element) {

    Annotations annotations = new Annotations(environment);

    element.getAnnotationMirrors().forEach(annotations::add);

    TypeModel type = this.typeBuilder.build(element.asType());

    Modifiers modifiers = new Modifiers(element.getModifiers());

    return new FieldModel(this.environment, element, annotations, modifiers, type);
  }
}
