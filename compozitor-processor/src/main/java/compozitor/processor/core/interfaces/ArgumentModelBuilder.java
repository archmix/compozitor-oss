package compozitor.processor.core.interfaces;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

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

    TypeMirror argumentType = argument.asType();
    TypeModel type = this.typeBuilder.build(argumentType);

    return new ArgumentModel(this.context, argument, annotations, type, argumentType.getKind().equals(TypeKind.ARRAY));
  }
}
