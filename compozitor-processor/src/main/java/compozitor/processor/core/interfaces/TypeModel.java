package compozitor.processor.core.interfaces;

import javax.lang.model.element.Element;

public interface TypeModel {
  Annotations getAnnotations();

  Modifiers getModifiers();

  String getName();

  String getQualifiedName();

  Interfaces getInterfaces();

  TypeModel getSuperType();

  TypeParameters getParameters();

  Fields getFields();

  Methods getMethods();

  PackageModel getPackage();

  Element getElement();

  boolean instanceOf(TypeModel type);

  boolean isCollection();

  boolean isEnum();
}
