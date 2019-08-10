package compozitor.processor.core.interfaces;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

public class Interfaces extends ModelIterable<TypeModel> {
  private final Types types;
  private final TypeModelBuilder builder;

  Interfaces(ProcessingEnvironment environment) {
    this.types = environment.getTypeUtils();
    this.builder = new TypeModelBuilder(environment);
  }

  void add(TypeMirror interfaceType) {
    TypeElement type = (TypeElement) this.types.asElement(interfaceType);
    this.add(this.builder.build(type));
  }
}
