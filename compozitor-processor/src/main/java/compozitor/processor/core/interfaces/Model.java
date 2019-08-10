package compozitor.processor.core.interfaces;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

class Model<E> {
  protected final Elements elements;

  protected final Types types;

  protected final E element;

  Model(ProcessingEnvironment environment, E element) {
    this.elements = environment.getElementUtils();
    this.types = environment.getTypeUtils();
    this.element = element;
  }

  @Override
  public String toString() {
    return this.element.toString();
  }
}
