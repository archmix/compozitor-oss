package compozitor.processor.core.interfaces;

import javax.lang.model.element.TypeElement;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "create")
public class JavaTypes {
  private final JavaModel javaModel;
  
  public TypeModel getObjectType() {
    return this.getType("java.lang.Object");
  }

  public TypeModel getType(String name) {
    TypeElement element = this.element(name);
    return this.javaModel.getType(element);
  }

  public TypeModel getAnnotationType(String name) {
    TypeElement element = this.element(name);
    return this.javaModel.getType(element);
  }

  private TypeElement element(String name) {
      return this.javaModel.getContext().getTypeElement(name);
  }
}
