package compozitor.processor.core.interfaces;

import javax.lang.model.element.TypeElement;
import lombok.Getter;

public class SimpleTypeModel extends AssignableModel<TypeElement> implements TypeModel {
  private final PackageModel packageModel;

  @Getter
  private final Annotations annotations;

  @Getter
  private final Modifiers modifiers;

  @Getter
  private final String name;

  @Getter
  private final String qualifiedName;

  @Getter
  private final TypeModel superType;
  
  @Getter
  private final Interfaces interfaces;

  @Getter
  private final Fields fields;

  @Getter
  private final Methods methods;

  @Getter
  private final TypeParameters parameters;
  
  SimpleTypeModel(ProcessingContext context, TypeElement element, PackageModel packageModel, Annotations annotations, Modifiers modifiers,
      TypeModel superType, Interfaces interfaces, Fields fields, Methods methods, TypeParameters parameters) {
    super(context, element);
    this.packageModel = packageModel;
    this.annotations = annotations;
    this.modifiers = modifiers;
    this.name = element.getSimpleName().toString();
    this.qualifiedName = element.getQualifiedName().toString();
    this.superType = superType;
    this.interfaces = interfaces;
    this.fields = fields;
    this.methods = methods;
    this.parameters = parameters;
  }

  public PackageModel getPackage() {
    return this.packageModel;
  }

  @Override
  public TypeElement getElement() {
    return this.element;
  }
  
  public boolean isCollection() {
    try {
      return Iterable.class.isAssignableFrom(Class.forName(this.element.toString()));
    } catch (ClassNotFoundException e) {
      throw new IllegalStateException(e);
    }
  }
  
  @Override
  public boolean instanceOf(TypeModel type) {
    boolean instance = super.instanceOf(type);
    
    if(!instance && this.getSuperType() != null) {
      return this.getSuperType().instanceOf(type);
    }
    
    return instance;
  }
}
