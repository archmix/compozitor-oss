package compozitor.processor.core.application;

import compozitor.processor.core.interfaces.AnnotationModel;
import compozitor.processor.core.interfaces.AnnotationProcessor;
import compozitor.processor.core.interfaces.FieldModel;
import compozitor.processor.core.interfaces.MethodModel;
import compozitor.processor.core.interfaces.TypeModel;
import org.junit.Assert;

import javax.lang.model.element.AnnotationMirror;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    final TypeModel annotationType = context.getJavaModel().getType(ANNOTATION_CLASS_NAME);

    final AnnotationModel annotationModel = model.getAnnotations()
      .get(annotation -> annotation.instanceOf(annotationType))
      .get();

    Assert.assertEquals("first", annotationModel.value("firstName"));
    final List<AnnotationMirror> surnames = annotationModel.annotations("surnames");
    Assert.assertTrue(surnames.isEmpty());
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    Set<String> types = new HashSet<>();
    types.add(ANNOTATION_CLASS_NAME);
    return types;
  }
}
