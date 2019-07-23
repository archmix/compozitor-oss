package compozitor.processor.core.application;

import javax.annotation.processing.SupportedAnnotationTypes;

import org.junit.Assert;

import compozitor.processor.core.interfaces.CompozitorProcessor;
import compozitor.processor.core.interfaces.FieldModel;
import compozitor.processor.core.interfaces.MethodModel;
import compozitor.processor.core.interfaces.TypeModel;

@SupportedAnnotationTypes("compozitor.processor.core.test.MethodAnnotation")
public class MethodProcessor extends CompozitorProcessor {
	@Override
	protected void process(TypeModel model) {
		Assert.fail();
	}

	@Override
	protected void process(FieldModel model) {
		Assert.fail();
	}

	@Override
	protected void process(MethodModel model) {
		Assert.assertNotNull(model);
	}
}
