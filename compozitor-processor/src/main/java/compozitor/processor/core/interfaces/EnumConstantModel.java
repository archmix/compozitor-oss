package compozitor.processor.core.interfaces;

import lombok.Getter;

import javax.lang.model.element.VariableElement;

@Getter
public class EnumConstantModel extends Model<VariableElement> {
  private final Annotations annotations;

  private final String name;

  public EnumConstantModel(ProcessingContext context, VariableElement element, Annotations annotations) {
    super(context, element);
    this.name = element.getSimpleName().toString();
    this.annotations = annotations;
  }
}
