package compozitor.processor.core.interfaces;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;

public class TypeParameterModel extends TypeModel {
  private final TypeParameterElement parameter;

  TypeParameterModel(ProcessingContext context, TypeParameterElement element,
      PackageModel packageModel, Annotations annotations, Modifiers modifiers, TypeModel superType,
      Interfaces interfaces, Fields fields, Methods methods) {
    super(context, (TypeElement) element.getEnclosingElement(), packageModel, annotations,
        modifiers, superType, interfaces, fields, methods);
    this.parameter = element;
  }

}
