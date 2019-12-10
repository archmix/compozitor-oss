package pluginsServiceProcessorTest;

import compozitor.generator.core.interfaces.CodeGenerationCategory;
import compozitor.processor.core.interfaces.TypeModel;
import compozitor.template.core.interfaces.TemplateContextData;
import compozitor.engine.core.interfaces.ProcessorPlugin;
import compozitor.engine.core.interfaces.TypeModelPlugin;
import compozitor.engine.processor.infra.TestCategory;
import compozitor.engine.processor.infra.TestTemplateContextData;

@ProcessorPlugin
public class PluginsServiceRegistration implements TypeModelPlugin<TestTemplateContextData> {
  @Override
  public TestTemplateContextData accept(TypeModel typeModel) {
    return null;
  }

  @Override
  public CodeGenerationCategory category() {
    return TestCategory.INSTANCE;
  }
}
