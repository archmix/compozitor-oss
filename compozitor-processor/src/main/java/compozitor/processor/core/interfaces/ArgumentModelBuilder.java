package compozitor.processor.core.interfaces;

import javax.lang.model.element.VariableElement;

public class ArgumentModelBuilder {
  private final ProcessingContext context;

  private final TypeModelBuilder typeBuilder;

  public ArgumentModelBuilder(ProcessingContext context) {
    this.context = context;
    this.typeBuilder = new TypeModelBuilder(context);
  }

  public ArgumentModel build(VariableElement argument) {
    Annotations annotations = new Annotations(this.context);
    argument.getAnnotationMirrors().forEach(annotations::add);

    TypeModel type = this.typeBuilder.build(argument.asType());

    return new ArgumentModel(this.context, argument, annotations, type);
  }
}
