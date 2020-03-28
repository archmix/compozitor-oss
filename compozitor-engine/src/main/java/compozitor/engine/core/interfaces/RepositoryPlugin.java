package compozitor.engine.core.interfaces;

import compozitor.processor.core.interfaces.AnnotationRepository;
import compozitor.processor.core.interfaces.ProcessingContext;
import compozitor.template.core.interfaces.TemplateContextData;

import java.util.Collection;

public interface RepositoryPlugin<T extends TemplateContextData<T>> extends CodeGenerationCategoryPlugin {
  Collection<T> accept(ProcessingContext context, AnnotationRepository annotationRepository);
}
