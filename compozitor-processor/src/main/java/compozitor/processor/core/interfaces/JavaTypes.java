package compozitor.processor.core.interfaces;

import java.util.HashMap;
import java.util.Map;
import javax.lang.model.element.TypeElement;

public class JavaTypes {
  private final ProcessingContext context;

  private final TypeModelBuilder builder;

  private final Map<String, TypeElement> cache;

  JavaTypes(ProcessingContext context) {
    this.context = context;
    this.builder = new TypeModelBuilder(context);
    this.cache = new HashMap<>();
  }

  public static JavaTypes create(ProcessingContext context) {
    return new JavaTypes(context);
  }

  public TypeModel getObjectType() {
    return this.getType("java.lang.Object");
  }

  public TypeModel getType(String name) {
    TypeElement element = this.element(name);
    return this.builder.build(element);
  }

  public TypeModel getAnnotationType(String name) {
    TypeElement element = this.element(name);
    return this.builder.build(element);
  }

  private TypeElement element(String name) {
    TypeElement element = this.cache.get(name);
    if (element == null) {
      element = this.context.getTypeElement(name);
      this.cache.put(name, element);
    }

    return element;
  }
}
