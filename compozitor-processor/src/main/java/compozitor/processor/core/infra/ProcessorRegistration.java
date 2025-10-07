package compozitor.processor.core.infra;

import com.google.auto.service.AutoService;
import compozitor.processor.core.interfaces.Processor;
import compozitor.processor.core.interfaces.ServiceProcessor;

import java.util.Arrays;
import java.util.Set;

@AutoService(javax.annotation.processing.Processor.class)
public class ProcessorRegistration extends ServiceProcessor {
  @Override
  public final Set<String> getSupportedAnnotationTypes() {
    return Set.of(Processor.class.getName());
  }

  @Override
  protected final Iterable<Class<?>> serviceClasses() {
    return Arrays.asList(javax.annotation.processing.Processor.class);
  }
}
