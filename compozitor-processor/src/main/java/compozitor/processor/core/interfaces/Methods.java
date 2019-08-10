package compozitor.processor.core.interfaces;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;

public class Methods extends ModelIterable<MethodModel> {
  private final MethodModelBuilder builder;

  public Methods(ProcessingEnvironment environment) {
    this.builder = new MethodModelBuilder(environment);
  }

  void add(ExecutableElement executable) {
    this.add(this.builder.build(executable));
  }
}
