package methodProcessorTest;

import annotations.MethodProcessor;
import compozitor.processor.core.interfaces.CompilationBuilder;
import compozitor.processor.core.interfaces.CompileAssertion;
import compozitor.processor.core.interfaces.TestResources;
import org.junit.Test;

public class MethodProcessorTest {
  @Test
  public void givenMethodAnnotationWhenCompileThenProcessMethod() {
    TestResources resources = TestResources.create(this.getClass());

    CompileAssertion compilation =
      CompilationBuilder.create()
        .withProcessors(new MethodProcessor())
        .withJavaSource(
          resources.testFile("MethodTest.java")
        ).build();

    compilation.assertSuccess();
    compilation.assertGeneratedFiles(1);
  }
}
