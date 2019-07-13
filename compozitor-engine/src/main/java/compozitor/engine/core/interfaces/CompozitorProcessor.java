package compozitor.engine.core.interfaces;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

public abstract class CompozitorProcessor extends AbstractProcessor {

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

	protected abstract void process(TypeElement annotation, Element element);
}
