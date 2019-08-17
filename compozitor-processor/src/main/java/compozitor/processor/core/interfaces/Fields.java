package compozitor.processor.core.interfaces;

import javax.lang.model.element.VariableElement;

public class Fields extends ModelIterable<FieldModel> {
  private FieldModelBuilder builder;

  public Fields(ProcessingContext context) {
    this.builder = new FieldModelBuilder(context);
  }

  void add(VariableElement element) {
    this.add(this.builder.build(element));
  }
}
