package compozitor.processor.core.application;

import javax.annotation.processing.SupportedAnnotationTypes;

import org.junit.Assert;

import compozitor.processor.core.interfaces.AnnotationProcessor;
import compozitor.processor.core.interfaces.FieldModel;
import compozitor.processor.core.interfaces.MethodModel;
import compozitor.processor.core.interfaces.TypeModel;

@SupportedAnnotationTypes("compozitor.processor.core.test.FieldAnnotation")
public class FieldProcessor extends AnnotationProcessor {
	@Override
	protected void process(TypeModel model) {
		Assert.fail();
	}
	
	@Override
	protected void process(MethodModel model) {
		Assert.fail();
	}
	
	@Override
	protected void process(FieldModel model) {
		Assert.assertNotNull(model);
	}
}