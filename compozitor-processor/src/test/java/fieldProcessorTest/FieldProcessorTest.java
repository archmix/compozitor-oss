package fieldProcessorTest;

import processor.FieldProcessor;
import compozitor.processor.core.interfaces.CompilationBuilder;
import compozitor.processor.core.interfaces.CompileAssertion;
import compozitor.processor.core.interfaces.TestResources;
import org.junit.Test;

public class FieldProcessorTest {

  @Test
  public void givenFieldAnnotationWhenCompileThenProcessField() {
    TestResources resources = TestResources.create(this.getClass());

    CompileAssertion compilation =
      CompilationBuilder.create()
        .withProcessors(new FieldProcessor())
        .withJavaSource(resources.testFile("FieldTest.java"))
        .build();

    compilation.assertSuccess();
    compilation.assertGeneratedFiles(1);
  }
}
