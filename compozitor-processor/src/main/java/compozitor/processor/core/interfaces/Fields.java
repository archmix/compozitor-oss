package compozitor.processor.core.interfaces;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;

public class Fields extends ModelIterable<FieldModel> {
  private FieldModelBuilder builder;

  public Fields(ProcessingEnvironment environment) {
    this.builder = new FieldModelBuilder(environment);
  }

  void add(VariableElement element) {
    this.add(this.builder.build(element));
  }
}
