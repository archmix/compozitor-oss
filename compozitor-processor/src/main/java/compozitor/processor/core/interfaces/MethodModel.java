package compozitor.processor.core.interfaces;

import lombok.Getter;

import javax.lang.model.element.ExecutableElement;

@Getter
public class MethodModel extends Model<ExecutableElement> {
  private final Annotations annotations;

  private final Modifiers modifiers;

  private final TypeModel returnType;

  private final String name;

  private Arguments arguments;

  public MethodModel(ProcessingContext context, ExecutableElement element,
                     Annotations annotations, Modifiers modifiers, TypeModel returnType, Arguments arguments) {
    super(context, element);
    this.annotations = annotations;
    this.modifiers = modifiers;
    this.returnType = returnType;
    this.name = element.getSimpleName().toString();
    this.arguments = arguments;
  }
}
