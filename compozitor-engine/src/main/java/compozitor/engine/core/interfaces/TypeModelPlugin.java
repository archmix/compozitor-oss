package compozitor.engine.core.interfaces;

import compozitor.processor.core.interfaces.AnnotationRepository;
import compozitor.processor.core.interfaces.ProcessingContext;
import compozitor.processor.core.interfaces.TypeModel;
import compozitor.template.core.interfaces.TemplateContextData;

import java.util.ArrayList;
import java.util.Collection;

public interface TypeModelPlugin<T extends TemplateContextData<T>> extends CodeGenerationCategoryPlugin {
  default void accept(ProcessingContext context, AnnotationRepository annotationRepository) {
  }

  default T accept(ProcessingContext context, TypeModel typeModel) {
    return null;
  }

  default Collection<T> collect(ProcessingContext context, TypeModel typeModel) {
    return new ArrayList<>();
  }

  default void release(ProcessingContext context) {
  }
}
