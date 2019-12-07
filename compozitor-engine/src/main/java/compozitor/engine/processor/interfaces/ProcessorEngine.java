package compozitor.engine.processor.interfaces;

import compozitor.engine.core.interfaces.EngineCategory;
import compozitor.engine.core.interfaces.TemplateRepository;
import compozitor.template.core.interfaces.TemplateContextData;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class ProcessorEngine<T extends TemplateContextData<T>> extends MultiProcessorEngine<T> {

  @Override
  protected EngineCategory engineType() {
    return EngineCategory.adapter(this.getTargetAnnotation().getSimpleName());
  }

  @Override
  public final Set<String> getSupportedAnnotationTypes() {
    return new HashSet<String>(Arrays.asList(this.getTargetAnnotation().getCanonicalName()));
  }

  protected abstract Class<? extends Annotation> getTargetAnnotation();

  protected abstract void loadTemplates(TemplateRepository templates);
}
