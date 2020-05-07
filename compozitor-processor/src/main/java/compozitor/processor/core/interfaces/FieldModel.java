package compozitor.processor.core.interfaces;

import lombok.Getter;

import javax.lang.model.element.VariableElement;

public class FieldModel extends Model<VariableElement> {
  @Getter
  private final Annotations annotations;

  @Getter
  private final Modifiers modifiers;

  @Getter
  private final TypeModel type;

  @Getter
  private final String name;

  private final Object constantValue;

  @Getter
  private final Boolean constant;

  public FieldModel(ProcessingContext context, VariableElement element,
                    Annotations annotations, Modifiers modifiers, TypeModel type) {
    super(context, element);
    this.annotations = annotations;
    this.modifiers = modifiers;
    this.type = type;
    this.name = element.getSimpleName().toString();
    this.constantValue = element.getConstantValue();
    this.constant = this.modifiers.isStatic() && this.modifiers.isFinal();
  }

  public <T> T getConstantValue(){
    return (T) this.constantValue;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
