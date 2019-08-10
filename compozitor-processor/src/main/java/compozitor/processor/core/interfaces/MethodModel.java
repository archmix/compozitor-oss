package compozitor.processor.core.interfaces;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import lombok.Getter;

@Getter
public class MethodModel extends Model<ExecutableElement> {
  private final Annotations annotations;

  private final Modifiers modifiers;

  private final TypeModel returnType;

  private final String name;

  private Arguments arguments;

  public MethodModel(ProcessingEnvironment environment, ExecutableElement element,
      Annotations annotations, Modifiers modifiers, TypeModel returnType, Arguments arguments) {
    super(environment, element);
    this.annotations = annotations;
    this.modifiers = modifiers;
    this.returnType = returnType;
    this.name = element.getSimpleName().toString();
    this.arguments = arguments;
  }
}
