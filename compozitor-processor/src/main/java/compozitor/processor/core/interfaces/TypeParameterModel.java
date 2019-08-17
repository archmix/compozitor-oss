package compozitor.processor.core.interfaces;

import javax.lang.model.element.TypeParameterElement;
import lombok.Getter;

public class TypeParameterModel extends AssignableModel<TypeParameterElement> implements TypeModel {
  @Getter
  private final TypeModel type;

  public TypeParameterModel(ProcessingContext context, TypeParameterElement element,
      TypeModel type) {
    super(context, element);
    this.type = type;
  }

  @Override
  public Annotations getAnnotations() {
    return this.type.getAnnotations();
  }

  @Override
  public Modifiers getModifiers() {
    return this.type.getModifiers();
  }

  @Override
  public String getName() {
    return this.type.getName();
  }

  @Override
  public String getQualifiedName() {
    return this.type.getQualifiedName();
  }

  @Override
  public TypeModel getSuperType() {
    return this.type.getSuperType();
  }

  @Override
  public Interfaces getInterfaces() {
    return this.type.getInterfaces();
  }

  @Override
  public Fields getFields() {
    return this.type.getFields();
  }

  @Override
  public Methods getMethods() {
    return this.type.getMethods();
  }

  @Override
  public PackageModel getPackage() {
    return this.type.getPackage();
  }
  
  @Override
  public TypeParameterElement getElement() {
    return this.element;
  }
}
