package compozitor.generator.core.interfaces;

import compozitor.template.core.interfaces.TemplateContextData;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

public class MetaModelRepository<T extends TemplateContextData<T>> implements Iterable<T> {
  private final Set<T> dataList;

  private MetaModelRepository() {
    this.dataList = new HashSet<>();
  }

  public static MetaModelRepository create() {
    return new MetaModelRepository();
  }

  public void add(T data) {
    this.dataList.add(data);
  }

  public void add(Collection<T> data) {
    this.dataList.addAll(data);
  }

  @Override
  public Iterator<T> iterator() {
    return this.dataList.iterator();
  }

  public Stream<T> stream() {
    return this.dataList.stream();
  }

  public void clear() {
    this.dataList.clear();
  }
}
