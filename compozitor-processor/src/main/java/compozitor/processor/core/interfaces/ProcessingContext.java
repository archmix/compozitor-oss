package compozitor.processor.core.interfaces;

import lombok.Getter;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;
import javax.tools.JavaFileManager.Location;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public class ProcessingContext implements Types, Elements, Filer, Logger {
  private final ProcessingEnvironment environment;
  private final Messager logger;
  @Getter
  private final JavaModel javaModel;
  @Getter
  private final JavaTypes javaTypes;

  public ProcessingContext(final ProcessingEnvironment environment, final Messager logger) {
    this.environment = environment;
    this.logger = logger;
    this.javaModel = JavaModel.create(this);
    this.javaTypes = JavaTypes.create(this, javaModel);
  }

  public static ProcessingContext create(ProcessingEnvironment environment) {
    return new ProcessingContext(environment, environment.getMessager());
  }

  public void info(String message, Object... args) {
    this.log(Kind.NOTE, message, args);
  }

  public void error(String message, Object... args) {
    this.log(Kind.ERROR, message, args);
  }

  public void warning(String message, Object... args) {
    this.log(Kind.WARNING, message, args);
  }

  public void mandatoryWarning(String message, Object... args) {
    this.log(Kind.MANDATORY_WARNING, message, args);
  }

  private void log(Kind kind, String message, Object... args) {
    String logMessage = createMessage(message, args);
    this.logger.printMessage(kind, logMessage);
  }

  public void log(Kind kind, Element element, String message, Object... args) {
    String logMessage = createMessage(message, args);
    this.logger.printMessage(kind, logMessage, element);
  }

  private String createMessage(String message, Object... args) {
    String logMessage = MessageFormat.format(message, args);
    System.out.println(logMessage);
    return logMessage;
  }

  @Override
  public Element asElement(TypeMirror t) {
    return this.environment.getTypeUtils().asElement(t);
  }

  @Override
  public boolean isSameType(TypeMirror t1, TypeMirror t2) {
    return this.environment.getTypeUtils().isSameType(t1, t2);
  }

  @Override
  public boolean isSubtype(TypeMirror t1, TypeMirror t2) {
    return this.environment.getTypeUtils().isSubtype(t1, t2);
  }

  @Override
  public boolean isAssignable(TypeMirror t1, TypeMirror t2) {
    return this.environment.getTypeUtils().isAssignable(t1, t2);
  }

  @Override
  public boolean contains(TypeMirror t1, TypeMirror t2) {
    return this.environment.getTypeUtils().contains(t1, t2);
  }

  @Override
  public boolean isSubsignature(ExecutableType m1, ExecutableType m2) {
    return this.environment.getTypeUtils().isSubsignature(m1, m2);
  }

  @Override
  public List<? extends TypeMirror> directSupertypes(TypeMirror t) {
    return this.environment.getTypeUtils().directSupertypes(t);
  }

  @Override
  public TypeMirror erasure(TypeMirror t) {
    return this.environment.getTypeUtils().erasure(t);
  }

  @Override
  public TypeElement boxedClass(PrimitiveType p) {
    return this.environment.getTypeUtils().boxedClass(p);
  }

  @Override
  public PrimitiveType unboxedType(TypeMirror t) {
    return this.environment.getTypeUtils().unboxedType(t);
  }

  @Override
  public TypeMirror capture(TypeMirror t) {
    return this.environment.getTypeUtils().capture(t);
  }

  @Override
  public PrimitiveType getPrimitiveType(TypeKind kind) {
    return this.environment.getTypeUtils().getPrimitiveType(kind);
  }

  @Override
  public NullType getNullType() {
    return this.environment.getTypeUtils().getNullType();
  }

  @Override
  public NoType getNoType(TypeKind kind) {
    return this.environment.getTypeUtils().getNoType(kind);
  }

  @Override
  public ArrayType getArrayType(TypeMirror componentType) {
    return this.environment.getTypeUtils().getArrayType(componentType);
  }

  @Override
  public WildcardType getWildcardType(TypeMirror extendsBound, TypeMirror superBound) {
    return this.environment.getTypeUtils().getWildcardType(extendsBound, superBound);
  }

  @Override
  public DeclaredType getDeclaredType(TypeElement typeElem, TypeMirror... typeArgs) {
    return this.environment.getTypeUtils().getDeclaredType(typeElem, typeArgs);
  }

  @Override
  public DeclaredType getDeclaredType(DeclaredType containing, TypeElement typeElem,
                                      TypeMirror... typeArgs) {
    return this.environment.getTypeUtils().getDeclaredType(containing, typeElem, typeArgs);
  }

  @Override
  public TypeMirror asMemberOf(DeclaredType containing, Element element) {
    return this.environment.getTypeUtils().asMemberOf(containing, element);
  }

  @Override
  public PackageElement getPackageElement(CharSequence name) {
    return this.environment.getElementUtils().getPackageElement(name);
  }

  @Override
  public TypeElement getTypeElement(CharSequence name) {
    return this.environment.getElementUtils().getTypeElement(name);
  }

  @Override
  public Map<? extends ExecutableElement, ? extends AnnotationValue> getElementValuesWithDefaults(
    AnnotationMirror a) {
    return this.environment.getElementUtils().getElementValuesWithDefaults(a);
  }

  @Override
  public String getDocComment(Element e) {
    return this.environment.getElementUtils().getDocComment(e);
  }

  @Override
  public boolean isDeprecated(Element e) {
    return this.environment.getElementUtils().isDeprecated(e);
  }

  @Override
  public Name getBinaryName(TypeElement type) {
    return this.environment.getElementUtils().getBinaryName(type);
  }

  @Override
  public PackageElement getPackageOf(Element type) {
    return this.environment.getElementUtils().getPackageOf(type);
  }

  @Override
  public List<? extends Element> getAllMembers(TypeElement type) {
    return this.environment.getElementUtils().getAllMembers(type);
  }

  @Override
  public List<? extends AnnotationMirror> getAllAnnotationMirrors(Element e) {
    return this.environment.getElementUtils().getAllAnnotationMirrors(e);
  }

  @Override
  public boolean hides(Element hider, Element hidden) {
    return this.environment.getElementUtils().hides(hider, hidden);
  }

  @Override
  public boolean overrides(ExecutableElement overrider, ExecutableElement overridden,
                           TypeElement type) {
    return this.environment.getElementUtils().overrides(overrider, overridden, type);
  }

  @Override
  public String getConstantExpression(Object value) {
    return this.environment.getElementUtils().getConstantExpression(value);
  }

  @Override
  public void printElements(Writer w, Element... elements) {
    this.environment.getElementUtils().printElements(w, elements);
  }

  @Override
  public Name getName(CharSequence cs) {
    return this.environment.getElementUtils().getName(cs);
  }

  @Override
  public boolean isFunctionalInterface(TypeElement type) {
    return this.environment.getElementUtils().isFunctionalInterface(type);
  }

  @Override
  public JavaFileObject createSourceFile(CharSequence name, Element... originatingElements)
    throws IOException {
    return this.environment.getFiler().createSourceFile(name, originatingElements);
  }

  @Override
  public JavaFileObject createClassFile(CharSequence name, Element... originatingElements)
    throws IOException {
    return this.environment.getFiler().createClassFile(name, originatingElements);
  }

  @Override
  public FileObject createResource(Location location, CharSequence pkg, CharSequence relativeName,
                                   Element... originatingElements) throws IOException {
    return this.environment.getFiler().createResource(location, pkg, relativeName,
      originatingElements);
  }

  @Override
  public FileObject getResource(Location location, CharSequence pkg, CharSequence relativeName)
    throws IOException {
    return this.environment.getFiler().getResource(location, pkg, relativeName);
  }
}
