package compozitor.processor.core.application;

import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import compozitor.processor.core.interfaces.AnnotationProcessor;
import compozitor.processor.core.interfaces.FieldModel;
import compozitor.processor.core.interfaces.MethodModel;
import compozitor.processor.core.interfaces.TypeModel;

public class TypeProcessor extends AnnotationProcessor {
	@Override
	protected void process(TypeModel model) {
		Assert.assertNotNull(model);
	}

	@Override
	protected void process(FieldModel model) {
		Assert.fail();
	}

	@Override
	protected void process(MethodModel model) {
		Assert.fail();
	}
	
	@Override
	public Set<String> getSupportedAnnotationTypes() {
	  Set<String> types = new HashSet<>();
	  types.add("compozitor.processor.core.test.TypeAnnotation");
	  return types;
	}
}
