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
    this.context.info("Building model for type {0}", type);
    if (type instanceof PrimitiveType) {
      this.context.info("Type is primitive");
      return this.build(this.context.boxedClass((PrimitiveType) type));
    }
    
    if(type.getKind().equals(TypeKind.ARRAY)) {
      this.context.info("Type is an array");
      String name = type.toString().replace("[]", "");
      return this.build(this.context.getTypeElement(name));
    }

    Element element = this.context.asElement(type);
    if (element instanceof TypeParameterElement) {
      this.context.info("Type has typed parameters");
      return this.build((TypeParameterElement) element);
    }

    if (type.getKind().equals(TypeKind.VOID)) {
      element = this.context.getTypeElement("java.lang.Void");
    }

    return this.build((TypeElement) element);
  }

  public TypeParameterModel build(TypeParameterElement typeParameter) {
    TypeModel typeModel = this.build((TypeElement) typeParameter.getEnclosingElement());
    
    return new TypeParameterModel(context, typeParameter, typeModel);
  }

  public TypeModel build(TypeElement type) {
    this.context.info("Building TypeModel for element {0}", type);
    PackageModel packageModel = this.buildPackage(type);

    Annotations annotations = new Annotations(context);
    type.getAnnotationMirrors().forEach(annotations::add);

    Modifiers modifiers = this.buildModifiers(type);
    
    if (packageModel.getName().startsWith("java")
        || type.getKind().equals(ElementKind.ANNOTATION_TYPE)) {
      return new SimpleTypeModel(context, type, packageModel, annotations, modifiers, null, null, null, null);
    }

    Interfaces interfaces = this.buildInterfaces(type);

    TypeModel superType = this.buildSuperClass(type);

    Fields fields = new Fields(context);
    Methods methods = new Methods(context);

    this.buildFields(fields, type);
    this.buildMethods(methods, type);

    return new SimpleTypeModel(this.context, type, packageModel, annotations, modifiers, superType,
        interfaces, fields, methods);
  }

  private Methods buildMethods(Methods methods, TypeElement type) {
    this.context.info("Building methods for type {0}", type);
    ElementFilter.methodsIn(type.getEnclosedElements()).forEach(methods::add);

    return methods;
  }

  private Fields buildFields(Fields fields, TypeElement type) {
    this.context.info("Building fields for type {0}", type);
    ElementFilter.fieldsIn(type.getEnclosedElements()).forEach(fields::add);
    return fields;
  }

  private TypeModel buildSuperClass(TypeElement type) {
    this.context.info("Building super class for type {0}", type);
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
    this.context.info("Building interfaces for type {0}", type);
    Interfaces interfaces = new Interfaces(this.context);

    type.getInterfaces().forEach(interfaces::add);

    return interfaces;
  }

  private Modifiers buildModifiers(TypeElement type) {
    this.context.info("Building modifiers for type {0}", type);
    return new Modifiers(type.getModifiers());
  }

  private PackageModel buildPackage(TypeElement type) {
    this.context.info("Building package for type {0}", type);
    return new PackageModel(this.context, this.context.getPackageOf(type));
  }
}
