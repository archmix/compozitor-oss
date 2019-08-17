package compozitor.processor.core.interfaces;

import javax.lang.model.element.VariableElement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class FieldModelBuilder {
  private final ProcessingContext context;

  private final TypeModelBuilder typeBuilder;

  FieldModelBuilder(ProcessingContext context) {
    this.context = context;
    this.typeBuilder = new TypeModelBuilder(context);
  }

  public FieldModel build(VariableElement element) {

    Annotations annotations = new Annotations(context);

    element.getAnnotationMirrors().forEach(annotations::add);

    TypeModel type = this.typeBuilder.build(element.asType());

    Modifiers modifiers = new Modifiers(element.getModifiers());

    return new FieldModel(this.context, element, annotations, modifiers, type);
  }
}
