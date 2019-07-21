package compozitor.processor.core.application;

import javax.annotation.processing.SupportedAnnotationTypes;

import org.junit.Assert;

import compozitor.processor.core.interfaces.CompozitorProcessor;
import compozitor.processor.core.interfaces.TypeModel;

@SupportedAnnotationTypes("compozitor.processor.core.test.TypeAnnotation")
public class TypeProcessor extends CompozitorProcessor {
	@Override
	protected void process(TypeModel model) {
		Assert.assertNotNull(model);
	}
}
