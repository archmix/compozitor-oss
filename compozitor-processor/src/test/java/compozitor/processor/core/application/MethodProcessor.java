package compozitor.processor.core.application;

import compozitor.processor.core.interfaces.AnnotationProcessor;
import compozitor.processor.core.interfaces.FieldModel;
import compozitor.processor.core.interfaces.MethodModel;
import compozitor.processor.core.interfaces.TypeModel;
import org.junit.Assert;

import java.util.HashSet;
import java.util.Set;

public class MethodProcessor extends AnnotationProcessor {
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

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    Set<String> types = new HashSet<>();
    types.add("compozitor.processor.core.test.MethodAnnotation");
    return types;
  }
}
