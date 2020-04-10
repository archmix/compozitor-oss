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
      .withProcessors(new TableProcessor())
      .withJavaSource(
        resources.testFile("City.java")
      ).build();

    compilation
      .assertSuccess()
      .assertGeneratedFiles(3)
      .generatedSourceAssertion(resources.testFile("CityInsertCommand.java"))
        .assertEquals(resources.testFile("InsertCommand.java"));
  }
}
