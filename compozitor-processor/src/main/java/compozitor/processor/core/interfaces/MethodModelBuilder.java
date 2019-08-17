package compozitor.processor.core.interfaces;

import javax.lang.model.element.ExecutableElement;

public class MethodModelBuilder {
  private final ProcessingContext context;

  private final TypeModelBuilder typeBuilder;

  MethodModelBuilder(ProcessingContext context) {
    this.context = context;
    this.typeBuilder = new TypeModelBuilder(context);
  }

  public MethodModel build(ExecutableElement element) {
    Annotations annotations = new Annotations(this.context);
    element.getAnnotationMirrors().forEach(annotations::add);

    Modifiers modifiers = new Modifiers(element.getModifiers());

    TypeModel returnType = this.typeBuilder.build(element.getReturnType());

    Arguments arguments = new Arguments(this.context);
    element.getParameters().forEach(arguments::add);

    return new MethodModel(context, element, annotations, modifiers, returnType, arguments);
  }
}
