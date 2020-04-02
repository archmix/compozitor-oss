package compozitor.engine.processor.infra;

import compozitor.engine.core.interfaces.TypeModelPlugin;
import compozitor.processor.core.interfaces.CompilationBuilder;
import compozitor.processor.core.interfaces.CompileAssertion;
import compozitor.processor.core.interfaces.TestResources;
import org.junit.Test;

public class PluginsServiceProcessorTest {
  final TestResources resources = TestResources.create(this.getClass());

  @Test
  public void givenAnnotatedPluginWhenProcessThenGenerateServiceFile() {
    CompileAssertion compilation = CompilationBuilder.create()
      .withProcessors(new PluginsServiceProcessor())
      .withJavaSource(
        resources.testFile("PluginsServiceRegistration.java")
      ).build();

    compilation.assertSuccess();
    compilation.assertGeneratedFiles(6);
    compilation.serviceFileAssertion(TypeModelPlugin.class)
      .assertContains(resources.packageClass("PluginsServiceRegistration"));
  }
}
