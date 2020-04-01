package typeProcessorTest;

import com.google.common.collect.Sets;
import com.google.testing.compile.Compilation;
import com.google.testing.compile.Compilation.Status;
import compozitor.processor.core.interfaces.AnnotationProcessor;
import compozitor.processor.core.interfaces.CompilationBuilder;
import compozitor.processor.core.interfaces.CompileAssertion;
import compozitor.processor.core.interfaces.FieldModel;
import compozitor.processor.core.interfaces.MethodModel;
import compozitor.processor.core.interfaces.TestResources;
import compozitor.processor.core.interfaces.TypeModel;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class TypeProcessorTest {

  @Test
  public void givenTypeAnnotationWhenCompileThenProcessType(){
    TestResources resources = TestResources.create(this.getClass());

    CompileAssertion compilation =
      CompilationBuilder.create()
        .withProcessors(new TypeProcessor())
        .withJavaSource(resources.testFile("TypeTest.java"))
        .build();

    compilation.assertSuccess();
    compilation.assertGeneratedFiles(1);
  }

  class TypeProcessor extends AnnotationProcessor {
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
      return Sets.newHashSet("typeProcessorTest.TypeAnnotationTest");
    }
  }
}
