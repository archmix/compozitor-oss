package processorEngineTest;

import compozitor.processor.core.interfaces.CompilationBuilder;
import compozitor.processor.core.interfaces.CompileAssertion;
import compozitor.processor.core.interfaces.TestResources;
import org.junit.Test;

public class ProcessorEngineTest {
  private TestResources resources = TestResources.create(this.getClass());

  @Test
  public void givenCodeGenerationCategoryWhenProcessThenSourceCodeGenerated() {
    CompileAssertion compilation = CompilationBuilder.create()
        .withJavaSource(resources.testFile("City.java"))
        .withProcessors(new TableProcessor())
        .build();

    compilation.assertSuccess();
    compilation.assertGeneratedFiles(3);
    compilation.generatedSourceAssertion(resources.testFile("CityInsertCommand.java"))
      .assertEquals(resources.testFile("InsertCommand.java"));
  }
}
