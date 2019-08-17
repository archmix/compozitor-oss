package compozitor.processor.core.interfaces;

import javax.lang.model.element.VariableElement;
import lombok.Getter;

@Getter
public class ArgumentModel extends Model<VariableElement> {
  private final Annotations annotations;

  private final TypeModel type;

  private final String name;

  private final boolean array;
  
  public ArgumentModel(ProcessingContext context, VariableElement element,
      Annotations annotations, TypeModel type, Boolean array) {
    super(context, element);
    this.annotations = annotations;
    this.type = type;
    this.name = element.getSimpleName().toString();
    this.array = array;
  }
}
