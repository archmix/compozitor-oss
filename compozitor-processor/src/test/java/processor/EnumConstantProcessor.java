package processor;

import annotations.EnumConstantAnnotation;
import compozitor.processor.core.interfaces.AnnotationProcessor;
import compozitor.processor.core.interfaces.EnumConstantModel;
import compozitor.processor.core.interfaces.FieldModel;
import compozitor.processor.core.interfaces.MethodModel;
import compozitor.processor.core.interfaces.TypeModel;
import org.junit.Assert;

import java.util.Set;

public class EnumConstantProcessor extends AnnotationProcessor {
  @Override
  protected void process(TypeModel model) {
    Assert.fail();
  }

  @Override
  protected final void process(FieldModel model) {
    Assert.fail();
  }

  @Override
  protected final void process(MethodModel model) {
    Assert.fail();
  }

  @Override
  protected void process(EnumConstantModel enumConstant) {
    Assert.assertNotNull(enumConstant);
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Set.of(EnumConstantAnnotation.class.getName());
  }
}