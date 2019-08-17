package compozitor.processor.core.interfaces;

import javax.lang.model.element.VariableElement;

public class Arguments extends ModelIterable<ArgumentModel> {
  private final ArgumentModelBuilder builder;

  public Arguments(ProcessingContext context) {
    this.builder = new ArgumentModelBuilder(context);
  }

  void add(VariableElement element) {
    this.add(this.builder.build(element));
  }
}
