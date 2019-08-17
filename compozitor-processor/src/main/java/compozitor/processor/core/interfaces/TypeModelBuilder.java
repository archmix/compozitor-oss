package compozitor.processor.core.interfaces;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

class TypeModelBuilder {
  private final ProcessingContext context;

  TypeModelBuilder(ProcessingContext context) {
    this.context = context;
  }

  public TypeModel build(TypeMirror type) {

    if (type instanceof PrimitiveType) {
      return this.build(this.context.boxedClass((PrimitiveType) type));
    }

    Element element = this.context.asElement(type);
    if (type.getKind().equals(TypeKind.VOID)) {
      element = this.context.getTypeElement("java.lang.Void");
    }

    if (element instanceof TypeParameterElement) {
      return this.build((TypeParameterElement) element);
    }

    return this.build((TypeElement) element);
  }

  public TypeParameterModel build(TypeParameterElement typeParameter) {
    TypeElement type = (TypeElement) typeParameter.getEnclosingElement();

    PackageModel packageModel = this.buildPackage(type);

    Annotations annotations = new Annotations(context);
    type.getAnnotationMirrors().forEach(annotations::add);

    Modifiers modifiers = this.buildModifiers(type);

    Interfaces interfaces = this.buildInterfaces(type);

    TypeModel superType = this.buildSuperClass(type);

    Fields fields = new Fields(context);
    Methods methods = new Methods(context);

    if (!packageModel.getName().startsWith("java")) {
      this.buildFields(fields, type);
      this.buildMethods(methods, type);
    }

    return new TypeParameterModel(context, typeParameter, packageModel, annotations, modifiers,
        superType, interfaces, fields, methods);
  }

  public TypeModel build(TypeElement type) {
    this.context.info("Building TypeModel for element {0}", type);
    PackageModel packageModel = this.buildPackage(type);

    Annotations annotations = new Annotations(context);
    type.getAnnotationMirrors().forEach(annotations::add);

    Modifiers modifiers = this.buildModifiers(type);

    Interfaces interfaces = this.buildInterfaces(type);

    TypeModel superType = this.buildSuperClass(type);

    Fields fields = new Fields(context);
    Methods methods = new Methods(context);

    if (!packageModel.getName().startsWith("java")
        && !type.getKind().equals(ElementKind.ANNOTATION_TYPE)) {
      this.buildFields(fields, type);
      this.buildMethods(methods, type);
    }

    return new TypeModel(this.context, type, packageModel, annotations, modifiers, superType,
        interfaces, fields, methods);
  }

  private Methods buildMethods(Methods methods, TypeElement type) {
    ElementFilter.methodsIn(type.getEnclosedElements()).forEach(methods::add);

    return methods;
  }

  private Fields buildFields(Fields fields, TypeElement type) {
    ElementFilter.fieldsIn(type.getEnclosedElements()).forEach(fields::add);
    return fields;
  }

  private TypeModel buildSuperClass(TypeElement type) {
    TypeElement object = this.context.getTypeElement("java.lang.Object");

    TypeMirror superType = type.getSuperclass();

    if (TypeKind.NONE.equals(superType.getKind())
        || this.context.isAssignable(object.asType(), superType)) {
      return null;
    }

    TypeElement superElement = (TypeElement) this.context.asElement(superType);
    return this.build(superElement);
  }

  private Interfaces buildInterfaces(TypeElement type) {
    Interfaces interfaces = new Interfaces(this.context);

    type.getInterfaces().forEach(interfaces::add);

    return interfaces;
  }

  private Modifiers buildModifiers(TypeElement type) {
    return new Modifiers(type.getModifiers());
  }

  private PackageModel buildPackage(TypeElement type) {
    return new PackageModel(this.context, this.context.getPackageOf(type));
  }
}
