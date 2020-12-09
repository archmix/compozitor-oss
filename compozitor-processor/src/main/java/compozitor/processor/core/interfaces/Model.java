package compozitor.processor.core.interfaces;

class Model<E> {
  protected final ProcessingContext context;
  protected final E element;

  Model(ProcessingContext context, E element) {
    this.context = context;
    this.element = element;
  }

  public E getElement() {
    return this.element;
  }

  @Override
  public String toString() {
    return this.element.toString();
  }
}
