package processor;

import annotations.MethodAnnotation;
import compozitor.processor.core.interfaces.AnnotationProcessor;
import compozitor.processor.core.interfaces.EnumConstantModel;
import compozitor.processor.core.interfaces.FieldModel;
import compozitor.processor.core.interfaces.MethodModel;
import compozitor.processor.core.interfaces.TypeModel;
import org.junit.Assert;

import java.util.Set;

public class MethodProcessor extends AnnotationProcessor {
  @Override
  protected final void process(TypeModel model) {
    Assert.fail();
  }

  @Override
  protected final void process(FieldModel model) {
    Assert.fail();
  }

  @Override
  protected void process(MethodModel model) {
    Assert.assertNotNull(model);
  }

  @Override
  protected void process(EnumConstantModel enumConstant) {
    Assert.fail();
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Set.of(MethodAnnotation.class.getName());
  }
}