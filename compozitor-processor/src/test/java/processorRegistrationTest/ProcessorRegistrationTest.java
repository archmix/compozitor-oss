package processorRegistrationTest;

import compozitor.processor.core.infra.ProcessorRegistration;
import compozitor.processor.core.interfaces.CompilationBuilder;
import compozitor.processor.core.interfaces.CompileAssertion;
import compozitor.processor.core.interfaces.TestResources;
import org.junit.Test;

import javax.annotation.processing.Processor;

public class ProcessorRegistrationTest {
  @Test
  public void givenProcessorAnnotationWhenCompileThenGenerateServiceFile() {
    TestResources resources = TestResources.create(this.getClass());

    CompileAssertion compilation =
      CompilationBuilder.create()
        .withProcessors(new ProcessorRegistration())
        .withJavaSource(
          resources.testFile("ProcessorTest.java")
        ).build();

    compilation.assertSuccess();
    compilation.assertGeneratedFiles(2);
    compilation.serviceFileAssertion(Processor.class).assertContains(resources.packageClass("ProcessorTest"));
  }
}
