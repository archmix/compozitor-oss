package compozitor.processor.core.interfaces;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class AnnotatedElements {
  private final Map<TypeElement, Set<? extends Element>> elements;

  public AnnotatedElements() {
    this.elements = new HashMap<>();
  }

  void set(TypeElement annotation, Set<? extends Element> elements) {
    if (elements == null || elements.isEmpty()) {
      return;
    }
    this.elements.put(annotation, elements);
  }

  public Set<? extends Element> get(TypeElement annotation) {
    if (!elements.containsKey(annotation)) {
      return new HashSet<>();
    }
    return this.elements.get(annotation);
  }

  public void forEach(BiConsumer<TypeElement, Set<? extends Element>> consumer) {
    this.elements.forEach(consumer);
  }

  public boolean isEmpty() {
    return this.elements.isEmpty();
  }
}
