package typeProcessorTest;

import processor.TypeProcessor;
import compozitor.processor.core.interfaces.CompilationBuilder;
import compozitor.processor.core.interfaces.CompileAssertion;
import compozitor.processor.core.interfaces.TestResources;
import org.junit.Test;

public class TypeProcessorTest {

  @Test
  public void givenTypeAnnotationWhenCompileThenProcessType() {
    TestResources resources = TestResources.create(this.getClass());

    CompileAssertion compilation =
      CompilationBuilder.create()
        .withProcessors(new TypeProcessor())
        .withJavaSource(resources.testFile("TypeTest.java"))
        .build();

    compilation.assertSuccess();
    compilation.assertGeneratedFiles(1);
  }
}
