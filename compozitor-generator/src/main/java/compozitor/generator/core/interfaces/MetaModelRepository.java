package compozitor.generator.core.interfaces;

import compozitor.template.core.interfaces.TemplateContextData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class MetaModelRepository<T extends TemplateContextData<T>> implements Iterable<T> {
  private final List<T> dataList;

  public MetaModelRepository() {
    this.dataList = new ArrayList<>();
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
}
