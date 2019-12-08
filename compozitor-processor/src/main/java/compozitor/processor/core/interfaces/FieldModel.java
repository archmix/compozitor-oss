package compozitor.processor.core.interfaces;

import lombok.Getter;

import javax.lang.model.element.VariableElement;

@Getter
public class FieldModel extends Model<VariableElement> {
  private final Annotations annotations;

  private final Modifiers modifiers;

  private final TypeModel type;

  private final String name;

  public FieldModel(ProcessingContext context, VariableElement element,
                    Annotations annotations, Modifiers modifiers, TypeModel type) {
    super(context, element);
    this.annotations = annotations;
    this.modifiers = modifiers;
    this.type = type;
    this.name = element.getSimpleName().toString();
  }

  @Override
  public String toString() {
    return this.name;
  }
}
