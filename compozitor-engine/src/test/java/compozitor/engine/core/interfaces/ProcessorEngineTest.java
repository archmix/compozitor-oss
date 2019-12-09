package compozitor.engine.core.interfaces;

import com.google.testing.compile.Compilation;
import compozitor.processor.core.interfaces.CompilationBuilder;
import compozitor.processor.core.interfaces.FileAssertion;
import org.junit.Test;

public class ProcessorEngineTest {

  @Test
  public void givenCodeGenerationCategoryWhenProcessThenSourceCodeGenerated(){
    Compilation compilation = CompilationBuilder.create().withJavaSource("processorEngineTest/City.java")
      .withProcessors(processor()).build();

  }

  private ProcessorEngine processor() {
    TableProcessor tableProcessor = new TableProcessor();
    tableProcessor.listen(source ->{
      FileAssertion.withResourceFile("/processorEngineTest/InsertCommand.java").assertEquals(source);
    });

    return tableProcessor;
  }

}
