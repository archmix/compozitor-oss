package compozitor.processor.core.interfaces;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

class AssignableModel<M extends Element> extends Model<M> {

  AssignableModel(ProcessingContext context, M element) {
    super(context, element);
  }

  public boolean instanceOf(TypeModel type) {
    return this.instanceOf(type.getElement().asType());
  }

  boolean instanceOf(TypeMirror type) {
    return this.context.isAssignable(this.element.asType(), type);
  }
}
