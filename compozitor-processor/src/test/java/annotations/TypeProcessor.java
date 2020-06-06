package annotations;

import com.google.common.collect.Sets;
import compozitor.processor.core.interfaces.AnnotationProcessor;
import compozitor.processor.core.interfaces.FieldModel;
import compozitor.processor.core.interfaces.MethodModel;
import compozitor.processor.core.interfaces.TypeModel;
import org.junit.Assert;

import java.util.Set;

public class TypeProcessor extends AnnotationProcessor {
  @Override
  protected void process(TypeModel model) {
    Assert.assertNotNull(model);
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
  public Set<String> getSupportedAnnotationTypes() {
    return Sets.newHashSet(TypeAnnotation.class.getName());
  }
}