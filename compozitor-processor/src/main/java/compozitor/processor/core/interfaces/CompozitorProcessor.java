package compozitor.processor.core.interfaces;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

import com.google.auto.service.AutoService;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class CompozitorProcessor extends AbstractProcessor {

	@Override
	public final boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
		annotations.forEach(annotation ->{
			Messager messager = processingEnv.getMessager();
			messager.printMessage(Kind.NOTE, "Processing elements for annotation", annotation);
			roundEnvironment.getElementsAnnotatedWith(annotation).forEach(element ->{
				this.process(annotation, element);
			});
			messager.printMessage(Kind.NOTE, "All elements processed for annotation", annotation);
		});
		
		return annotations.size() > 0;
	}

	private void process(TypeElement annotation, Element element) {
		JavaModel javaModel = JavaModel.create(processingEnv);
		if(element.getKind().equals(ElementKind.CLASS)) {
			TypeModel model = javaModel.getClass(element);
			this.process(model);
			return;
		}
		
		if(element.getKind().equals(ElementKind.INTERFACE)) {
			TypeModel model = javaModel.getInterface(element);
			this.process(model);
			return;
		}
		
		if(element.getKind().equals(ElementKind.FIELD)) {
			FieldModel model = javaModel.getField(element);
			this.process(model);
			return;
		}
		
		if(element.getKind().equals(ElementKind.METHOD)) {
			MethodModel model = javaModel.getMethod(element);
			this.process(model);
			return;
		}
	}
	
	protected void process(TypeModel model) {
		return;
	}
	
	protected void process(FieldModel model) {
		return;
	}
	
	protected void process(MethodModel model) {
		return;
	}
}
