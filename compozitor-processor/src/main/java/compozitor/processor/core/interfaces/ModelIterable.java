package compozitor.processor.core.interfaces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

class ModelIterable<M> implements Iterable<M> {
  private final List<M> models;
  
  private final LazyLoadProxy<List<M>> supplierProxy;

  public ModelIterable(ListSupplier<M> modelsSupplier) {
    this.models = new ArrayList<>();
    this.supplierProxy = new LazyLoadProxy<List<M>>(modelsSupplier);
  }
  
  @Override
  public Iterator<M> iterator() {
    return this.models().iterator();
  }

  public Stream<M> stream() {
    return this.models().stream();
  }

  public boolean contains(M model) {
    return this.models().contains(model);
  }

  public Optional<M> get(Predicate<M> predicate) {
    return this.stream().filter(predicate).findFirst();
  }
  
  private List<M> models(){
    this.supplierProxy.run(models ->{
      this.models.addAll(models.get());
    });
    return this.models;
  }
}
