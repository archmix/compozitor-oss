package compozitor.engine.processor.infra;

import com.google.auto.service.AutoService;
import com.google.common.collect.Sets;
import compozitor.engine.core.interfaces.FieldModelPlugin;
import compozitor.engine.core.interfaces.MethodModelPlugin;
import compozitor.engine.core.interfaces.ProcessorPlugin;
import compozitor.engine.core.interfaces.TargetAnnnotationsPlugin;
import compozitor.engine.core.interfaces.TemplateEnginePlugin;
import compozitor.engine.core.interfaces.TemplatePlugin;
import compozitor.engine.core.interfaces.TypeModelPlugin;
import compozitor.processor.core.interfaces.ServiceProcessor;

import javax.annotation.processing.Processor;
import java.util.Arrays;
import java.util.Set;

//@AutoService(Processor.class)
public class PluginsServiceProcessor extends ServiceProcessor {
  @Override
  protected Iterable<Class<?>> serviceClasses() {
    return Arrays.asList(FieldModelPlugin.class, MethodModelPlugin.class, TargetAnnnotationsPlugin.class, TemplateEnginePlugin.class, TemplatePlugin.class, TypeModelPlugin.class);
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Sets.newHashSet(ProcessorPlugin.class.getName());
  }
}
