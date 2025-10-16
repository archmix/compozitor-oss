package compozitor.engine.processor.infra;

import compozitor.engine.core.interfaces.FieldModelPlugin;
import compozitor.engine.core.interfaces.MethodModelPlugin;
import compozitor.engine.core.interfaces.ProcessorPlugin;
import compozitor.engine.core.interfaces.TemplateEnginePlugin;
import compozitor.engine.core.interfaces.TemplatePlugin;
import compozitor.engine.core.interfaces.TypeModelPlugin;
import compozitor.processor.core.interfaces.Processor;
import compozitor.processor.core.interfaces.ServiceProcessor;

import java.util.Arrays;
import java.util.Set;

@Processor
public class PluginsServiceProcessor extends ServiceProcessor {
  public PluginsServiceProcessor() {
    this.traverseAncestors();
  }

  @Override
  protected Iterable<Class<?>> serviceClasses() {
    return Arrays.asList(FieldModelPlugin.class, MethodModelPlugin.class, TemplateEnginePlugin.class, TemplatePlugin.class, TypeModelPlugin.class);
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Set.of(ProcessorPlugin.class.getName());
  }
}
