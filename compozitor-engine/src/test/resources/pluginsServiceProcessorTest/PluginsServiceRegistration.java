package pluginsServiceProcessorTest;

import compozitor.generator.core.interfaces.CodeGenerationCategory;
import compozitor.processor.core.interfaces.AnnotationRepository;
import compozitor.processor.core.interfaces.ProcessingContext;
import compozitor.processor.core.interfaces.TypeModel;
import compozitor.engine.core.interfaces.ProcessorPlugin;
import compozitor.engine.core.interfaces.TypeModelPlugin;

@ProcessorPlugin
public class PluginsServiceRegistration implements TypeModelPlugin<TestTemplateContextData> {
  @Override
  public TestTemplateContextData accept(ProcessingContext context, TypeModel typeModel, AnnotationRepository annotationRepository) {
    return null;
  }

  @Override
  public CodeGenerationCategory category() {
    return TestCategory.INSTANCE;
  }
}
