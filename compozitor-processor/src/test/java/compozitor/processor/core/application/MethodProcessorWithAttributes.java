package compozitor.processor.core.application;

import compozitor.processor.core.interfaces.AnnotationModel;
import compozitor.processor.core.interfaces.AnnotationProcessor;
import compozitor.processor.core.interfaces.FieldModel;
import compozitor.processor.core.interfaces.MethodModel;
import compozitor.processor.core.interfaces.TypeModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import org.junit.Assert;

public class MethodProcessorWithAttributes extends AnnotationProcessor {
	private static final String ANNOTATION_CLASS_NAME = "compozitor.processor.core.test.MethodAnnotationWithAttributes";

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

		final TypeModel annotationType = javaTypes.getAnnotationType(ANNOTATION_CLASS_NAME);

		final AnnotationModel annotationModel = model.getAnnotations()
				.get(annotation -> annotation.instanceOf(annotationType))
				.get();

		Assert.assertEquals("first", annotationModel.getValue("firstName").getValue());
		final List<AnnotationMirror> surnames = (List<AnnotationMirror>) annotationModel
				.getValue("surnames").getValue();
		Assert.assertTrue(surnames.isEmpty());
	}
	
	@Override
    public Set<String> getSupportedAnnotationTypes() {
      Set<String> types = new HashSet<>();
      types.add(ANNOTATION_CLASS_NAME);
      return types;
    }
}
