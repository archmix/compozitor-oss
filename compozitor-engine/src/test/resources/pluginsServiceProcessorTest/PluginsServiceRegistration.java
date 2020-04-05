package pluginsServiceProcessorTest;

import compozitor.engine.core.interfaces.ProcessorPlugin;
import compozitor.engine.core.interfaces.TypeModelPlugin;
import compozitor.generator.core.interfaces.CodeGenerationCategory;
import compozitor.processor.core.interfaces.ProcessingContext;
import compozitor.processor.core.interfaces.TypeModel;

@ProcessorPlugin
public class PluginsServiceRegistration implements TypeModelPlugin<TestTemplateContextData> {
  @Override
  public TestTemplateContextData accept(ProcessingContext context, TypeModel typeModel) {
    return null;
  }

  @Override
  public CodeGenerationCategory category() {
    return TestCategory.INSTANCE;
  }
}
