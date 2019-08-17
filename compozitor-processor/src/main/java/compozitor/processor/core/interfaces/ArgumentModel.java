package compozitor.processor.core.interfaces;

import javax.lang.model.element.VariableElement;
import lombok.Getter;

@Getter
public class ArgumentModel extends Model<VariableElement> {
  private final Annotations annotations;

  private final TypeModel type;

  private final String name;

  public ArgumentModel(ProcessingContext context, VariableElement element,
      Annotations annotations, TypeModel type) {
    super(context, element);
    this.annotations = annotations;
    this.type = type;
    this.name = element.getSimpleName().toString();
  }
}
