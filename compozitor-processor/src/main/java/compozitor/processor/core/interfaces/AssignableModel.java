package compozitor.processor.core.interfaces;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

class AssignableModel<M extends Element> extends Model<M> {

  AssignableModel(ProcessingContext context, M element) {
    super(context, element);
  }

  public boolean instanceOf(TypeModel typeModel) {
    return this.instanceOf(typeModel.getElement().asType());
  }

  boolean instanceOf(TypeMirror targetType) {
    TypeMirror type = this.element.asType();
    return this.context.isAssignable(type, targetType) || this.context.isSubtype(targetType, type);
  }
}
