package compozitor.engine.core.interfaces;

import compozitor.processor.core.interfaces.AnnotationRepository;
import compozitor.processor.core.interfaces.ProcessingContext;
import compozitor.processor.core.interfaces.TypeModel;
import compozitor.template.core.interfaces.TemplateContextData;

import javax.annotation.processing.RoundEnvironment;

public interface TypeModelPlugin<T extends TemplateContextData<T>> extends CodeGenerationCategoryPlugin {
  T accept(ProcessingContext context, TypeModel typeModel);

  default void accept(ProcessingContext context, AnnotationRepository annotationRepository){}
}
