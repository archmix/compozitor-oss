package compozitor.engine.core.interfaces;

import compozitor.processor.core.interfaces.AnnotationRepository;
import compozitor.processor.core.interfaces.FieldModel;
import compozitor.processor.core.interfaces.ProcessingContext;
import compozitor.template.core.interfaces.TemplateContextData;

import java.util.ArrayList;
import java.util.Collection;

public interface FieldModelPlugin<T extends TemplateContextData<T>> extends CodeGenerationCategoryPlugin {
  default T accept(ProcessingContext context, FieldModel fieldModel, AnnotationRepository annotationRepository){
    return null;
  }

  default Collection<T> collect(ProcessingContext context, FieldModel fieldModel, AnnotationRepository annotationRepository){
    return new ArrayList<>();
  }
}
