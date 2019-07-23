package compozitor.processor.core.interfaces;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

class TypeModelBuilder {
	private final ProcessingEnvironment environment;
	
	TypeModelBuilder(ProcessingEnvironment environment) {
		this.environment = environment;
	}

	public TypeModel build(TypeMirror type) {
		Types types = this.environment.getTypeUtils();
		Elements elements = this.environment.getElementUtils();
		
		if(type instanceof PrimitiveType) {
			return this.build(types.boxedClass((PrimitiveType) type));
		}
		
		Element element = types.asElement(type);
		if(type.getKind().equals(TypeKind.VOID)) {
			element = elements.getTypeElement("java.lang.Void");
		}
		
		if(element instanceof TypeParameterElement) {
			return this.build((TypeParameterElement) element);
		}
		
		return this.build((TypeElement) element);
	}
	
	public TypeParameterModel build(TypeParameterElement typeParameter) {
		TypeElement type = (TypeElement) typeParameter.getEnclosingElement();
		
		PackageModel packageModel = this.buildPackage(type);
		
		Annotations annotations = new Annotations(environment);
		type.getAnnotationMirrors().forEach(annotations::add);
		
		Modifiers modifiers = this.buildModifiers(type);
		
		Interfaces interfaces = this.buildInterfaces(type);
		
		TypeModel superType = this.buildSuperClass(type);
		
		Fields fields = new Fields(environment);
		Methods methods = new Methods(environment);
		
		if(!packageModel.getName().startsWith("java")) {
			this.buildFields(fields, type);
			this.buildMethods(methods, type);
		}
		
		return new TypeParameterModel(environment, typeParameter, packageModel, annotations, modifiers, superType, interfaces, fields, methods);
	}
	
	public TypeModel build(TypeElement type) {
		PackageModel packageModel = this.buildPackage(type);
		
		Annotations annotations = new Annotations(environment);
		type.getAnnotationMirrors().forEach(annotations::add);
		
		Modifiers modifiers = this.buildModifiers(type);
		
		Interfaces interfaces = this.buildInterfaces(type);
		
		TypeModel superType = this.buildSuperClass(type);
		
		Fields fields = new Fields(environment);
		Methods methods = new Methods(environment);
		
		if(!packageModel.getName().startsWith("java")) {
			this.buildFields(fields, type);
			this.buildMethods(methods, type);
		}
		
		return new TypeModel(this.environment, type, packageModel, annotations, modifiers, superType, interfaces, fields, methods);
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
		Types types = this.environment.getTypeUtils();
		Elements elements = this.environment.getElementUtils();
		TypeElement object = elements.getTypeElement("java.lang.Object");
		
		TypeMirror superType = type.getSuperclass();
		
		if(TypeKind.NONE.equals(superType.getKind()) || types.isAssignable(object.asType(), superType)) {
			return null;
		}
		
		TypeElement superElement = (TypeElement) types.asElement(superType);
		return this.build(superElement);
	}
	
	private Interfaces buildInterfaces(TypeElement type) {
		Interfaces interfaces = new Interfaces(this.environment);
		
		type.getInterfaces().forEach(interfaces::add);
		
		return interfaces;
	}
	
	private Modifiers buildModifiers(TypeElement type) {
		return new Modifiers(type.getModifiers());
	}

	private PackageModel buildPackage(TypeElement type) {
		Elements elements = environment.getElementUtils();
		
		return new PackageModel(this.environment, elements.getPackageOf(type));
	}
}
