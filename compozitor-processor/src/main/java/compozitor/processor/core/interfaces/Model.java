package compozitor.processor.core.interfaces;

class Model<E> {
  protected final ProcessingContext context;
  protected final E element;

  Model(ProcessingContext context, E element) {
    this.context = context;
    this.element = element;
  }

  @Override
  public String toString() {
    return this.element.toString();
  }
}
