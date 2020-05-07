package compozitor.processor.core.interfaces;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@RequiredArgsConstructor(staticName = "create", access = AccessLevel.PACKAGE)
public class JavaModel {
  private final ProcessingContext context;

  private final Map<String, TypeModel> typeCache = new HashMap<>();

  public TypeModel getEnum(Element element){
    if(element.getKind() != ElementKind.ENUM){
      return null;
    }
    return this.getType((TypeElement) element);
  }

  public AnnotationModel getAnnotation(AnnotationMirror annotation) {
    return AnnotationModel.create(context, annotation);
  }

  public TypeModel getType(Element element) {
    TypeModel typeModel = this.getClass(element);
    if (typeModel == null) {
      typeModel = this.getInterface(element);
    }
    if(typeModel == null){
      typeModel = this.getEnum(element);
    }
    return typeModel;
  }

  public TypeModel getClass(Element element) {
    if (!element.getKind().equals(ElementKind.CLASS)) {
      return null;
    }

    return this.getType((TypeElement) element);
  }

  public TypeModel getClass(TypeMirror element) {
    return this.getType(element);
  }

  public TypeModel getInterface(Element element) {
    if (!element.getKind().equals(ElementKind.INTERFACE)) {
      return null;
    }

    return this.getType((TypeElement) element);
  }

  public TypeModel getInterface(TypeMirror mirror) {
    return this.getType(mirror);
  }

  public TypeModel getType(String name) {
    TypeElement element = this.context.getTypeElement(name);
    return this.getType(element);
  }

  public TypeModel getType(TypeMirror type) {
    List<TypeMirror> typeParameters = new ArrayList<>();

    if (type instanceof PrimitiveType) {
      return this.getType(this.context.boxedClass((PrimitiveType) type), typeParameters);
    }

    if (type.getKind().equals(TypeKind.ARRAY)) {
      String name = type.toString().replace("[]", "");
      return this.getType(this.context.getTypeElement(name), typeParameters);
    }

    if (type.getKind().equals(TypeKind.DECLARED)) {
      DeclaredType declared = (DeclaredType) type;
      typeParameters.addAll(declared.getTypeArguments());
    }

    Element element = this.context.asElement(type);
    if (type.getKind().equals(TypeKind.VOID)) {
      element = this.context.getTypeElement("java.lang.Void");
    }

    return this.getType((TypeElement) element, typeParameters);
  }

  public TypeModel getType(TypeElement type) {
    return this.getType(type, new ArrayList<>());
  }

  public TypeModel getType(TypeElement type, List<? extends TypeMirror> typeParameters) {
    String typeName = getQualifiedName(type, typeParameters);

    TypeModel typeModel = this.typeCache.get(typeName);
    if (typeModel != null) {
      return typeModel;
    }

    PackageModel packageModel = this.getPackage(type);

    Annotations annotations = new Annotations(type.getAnnotationMirrors(), this);

    Modifiers modifiers = new Modifiers(type.getModifiers());

    Interfaces interfaces = new Interfaces(type.getInterfaces(), this);

    Methods methods = new Methods(ElementFilter.methodsIn(type.getEnclosedElements()), this);

    Fields fields = new Fields(ElementFilter.fieldsIn(type.getEnclosedElements()), this);

    TypeParameters parameters = new TypeParameters(typeParameters, this);

    TypeModel superType = getSuperTypeModel(type);

    if (type.getKind().equals(ElementKind.CLASS) && !typeName.startsWith("java")) {
      superType = this.getType(type.getSuperclass());
    }

    SimpleTypeModel simpleType = new SimpleTypeModel(this.context, type, packageModel, annotations, modifiers, superType,
      interfaces, fields, methods, parameters);

    this.typeCache.put(typeName, simpleType);

    return simpleType;
  }

  private TypeModel getSuperTypeModel(TypeElement type) {
    if (type.getSuperclass().getKind() == TypeKind.NONE) {
      return null;
    }

    return getType(type.getSuperclass());
  }

  private String getQualifiedName(TypeElement type, List<? extends TypeMirror> typeParameters) {
    String qualifiedName = type.getQualifiedName().toString();

    String parameters = typeParameters.stream().map(TypeMirror::toString).collect(joining(","));
    if (parameters.isEmpty()) {
      return qualifiedName;
    }

    return String.format("%s<%s>", qualifiedName, parameters);
  }

  public FieldModel getField(Element element) {
    if (!element.getKind().equals(ElementKind.FIELD)) {
      return null;
    }

    return this.getField((VariableElement) element);
  }

  public FieldModel getField(VariableElement element) {
    Annotations annotations = new Annotations(element.getAnnotationMirrors(), this);

    TypeModel type = this.getType(element.asType());

    Modifiers modifiers = new Modifiers(element.getModifiers());

    return new FieldModel(this.context, element, annotations, modifiers, type);
  }

  public MethodModel getMethod(Element element) {
    if (!element.getKind().equals(ElementKind.METHOD)) {
      return null;
    }

    return this.getMethod((ExecutableElement) element);
  }

  public MethodModel getMethod(ExecutableElement element) {
    Annotations annotations = new Annotations(element.getAnnotationMirrors(), this);

    Modifiers modifiers = new Modifiers(element.getModifiers());

    TypeModel returnType = this.getType(element.getReturnType());

    Arguments arguments = new Arguments(element.getParameters(), this);

    return new MethodModel(context, element, annotations, modifiers, returnType, arguments);
  }

  public ArgumentModel getArgument(VariableElement element) {
    Annotations annotations = new Annotations(element.getAnnotationMirrors(), this);

    TypeMirror argumentType = element.asType();
    TypeModel type = this.getType(argumentType);

    return new ArgumentModel(this.context, element, annotations, type,
      argumentType.getKind().equals(TypeKind.ARRAY));
  }

  private PackageModel getPackage(TypeElement type) {
    return new PackageModel(this.context, this.context.getPackageOf(type));
  }
}
