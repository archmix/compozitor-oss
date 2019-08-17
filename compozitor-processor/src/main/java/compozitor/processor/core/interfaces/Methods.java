package compozitor.processor.core.interfaces;

import javax.lang.model.element.ExecutableElement;

public class Methods extends ModelIterable<MethodModel> {
  private final MethodModelBuilder builder;

  public Methods(ProcessingContext context) {
    this.builder = new MethodModelBuilder(context);
  }

  void add(ExecutableElement executable) {
    this.add(this.builder.build(executable));
  }
}
