package compozitor.engine.core.interfaces;

import com.google.common.collect.Lists;
import compozitor.processor.core.interfaces.AnnotationRepository;
import compozitor.processor.core.interfaces.ProcessingContext;
import compozitor.processor.core.interfaces.TypeModel;
import compozitor.template.core.interfaces.TemplateContextData;

import javax.annotation.processing.RoundEnvironment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public interface TypeModelPlugin<T extends TemplateContextData<T>> extends CodeGenerationCategoryPlugin {
  default T accept(ProcessingContext context, TypeModel typeModel){
    return null;
  }

  default Collection<T> accept(ProcessingContext context, TypeModel typeModel, AnnotationRepository annotationRepository){
    return new ArrayList<>();
  }
}
