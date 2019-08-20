package compozitor.processor.core.interfaces;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class LazyLoadProxy<T> {
  private State state;
  
  private Supplier<T> supplier;
  
  public LazyLoadProxy(Supplier<T> supplier) {
    this.state = State.INITIAL;
    this.supplier = supplier;
  }
  
  public synchronized void run(Consumer<Supplier<T>> consumer) {
    if(State.INITIAL.equals(this.state)) {
      consumer.accept(this.supplier);
      this.state = State.RAN;
    }
  }
  
  enum State {
    INITIAL,
    RAN;
  }
}
