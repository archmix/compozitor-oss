package enumTypeTest;

import compozitor.processor.core.interfaces.CompilationBuilder;
import compozitor.processor.core.interfaces.CompileAssertion;
import compozitor.processor.core.interfaces.TestResources;
import org.junit.Test;

public class EnumTypeTest {
  @Test
  public void givenEnumWhenCompileThenGetTypeModel() {
    TestResources resources = TestResources.create(this.getClass());

    CompileAssertion compilation = CompilationBuilder.create()
      .withProcessors(new EnumTypeProcessor())
      .withJavaSource(
        resources.testFile("EnumType.java")
      ).build();

    compilation
      .assertSuccess()
      .assertGeneratedFiles(1);
  }
}
