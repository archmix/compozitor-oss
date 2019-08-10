package compozitor.processor.core.interfaces;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import lombok.Getter;

public class TypeModel extends AssignableModel<TypeElement> {
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

  TypeModel(ProcessingEnvironment environment, TypeElement element, PackageModel packageModel,
      Annotations annotations, Modifiers modifiers, TypeModel superType, Interfaces interfaces,
      Fields fields, Methods methods) {
    super(environment, element);
    this.packageModel = packageModel;
    this.annotations = annotations;
    this.modifiers = modifiers;
    this.name = element.getSimpleName().toString();
    this.qualifiedName = element.getQualifiedName().toString();
    this.superType = superType;
    this.interfaces = interfaces;
    this.fields = fields;
    this.methods = methods;
  }

  public PackageModel getPackage() {
    return this.packageModel;
  }
}
