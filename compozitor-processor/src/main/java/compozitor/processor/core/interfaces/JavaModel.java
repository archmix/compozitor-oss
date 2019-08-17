package compozitor.processor.core.interfaces;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "create")
public class JavaModel {
  protected final ProcessingContext context;

  public TypeModel getClass(Element element) {
    if (!element.getKind().equals(ElementKind.CLASS)) {
      return null;
    }

    return new TypeModelBuilder(this.context).build((TypeElement) element);
  }

  public TypeModel getInterface(Element element) {
    if (!element.getKind().equals(ElementKind.INTERFACE)) {
      return null;
    }

    return new TypeModelBuilder(this.context).build((TypeElement) element);
  }

  public FieldModel getField(Element element) {
    if (!element.getKind().equals(ElementKind.FIELD)) {
      return null;
    }

    return new FieldModelBuilder(this.context).build((VariableElement) element);
  }

  public MethodModel getMethod(Element element) {
    if (!element.getKind().equals(ElementKind.METHOD)) {
      return null;
    }

    return new MethodModelBuilder(this.context).build((ExecutableElement) element);
  }
}
