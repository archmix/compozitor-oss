package compozitor.processor.core.interfaces;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

public class Interfaces extends ModelIterable<TypeModel> {
  private final ProcessingContext types;
  private final TypeModelBuilder builder;

  Interfaces(ProcessingContext context) {
    this.types = context;
    this.builder = new TypeModelBuilder(context);
  }

  void add(TypeMirror interfaceType) {
    TypeElement type = (TypeElement) this.types.asElement(interfaceType);
    this.add(this.builder.build(type));
  }
}
