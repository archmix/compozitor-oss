package compozitor.processor.core.interfaces;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;

public class ArgumentModelBuilder {
  private final ProcessingEnvironment environment;

  private final TypeModelBuilder typeBuilder;

  public ArgumentModelBuilder(ProcessingEnvironment environment) {
    this.environment = environment;
    this.typeBuilder = new TypeModelBuilder(environment);
  }

  public ArgumentModel build(VariableElement argument) {
    Annotations annotations = new Annotations(this.environment);
    argument.getAnnotationMirrors().forEach(annotations::add);

    TypeModel type = this.typeBuilder.build(argument.asType());

    return new ArgumentModel(this.environment, argument, annotations, type);
  }
}
