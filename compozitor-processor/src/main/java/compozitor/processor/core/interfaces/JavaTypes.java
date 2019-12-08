package compozitor.processor.core.interfaces;

import lombok.RequiredArgsConstructor;

import javax.lang.model.element.TypeElement;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor(staticName = "create")
public class JavaTypes {
  private final ProcessingContext context;

  private final JavaModel javaModel;

  public TypeModel getObjectType() {
    return this.javaModel.getType(Object.class.getName());
  }

  public TypeModel getStringType() {
    return this.javaModel.getType(String.class.getName());
  }

  public TypeModel getNumberType() {
    return this.javaModel.getType(Number.class.getName());
  }

  public TypeModel getIntegerType() {
    return this.javaModel.getType(Integer.class.getName());
  }

  public TypeModel getDoubleType() {
    return this.javaModel.getType(Double.class.getName());
  }

  public TypeModel getFloatType() {
    return this.javaModel.getType(Float.class.getName());
  }

  public TypeModel getDateType() {
    return this.javaModel.getType(Date.class.getName());
  }

  public TypeModel getIterableType() {
    return this.javaModel.getType(Iterable.class.getName());
  }

  public TypeModel getCollectionType() {
    return this.javaModel.getType(Collection.class.getName());
  }

  public TypeModel getListType() {
    return this.javaModel.getType(List.class.getName());
  }

  public TypeModel getSetType() {
    return this.javaModel.getType(Set.class.getName());
  }

  public TypeModel getMapType() {
    return this.javaModel.getType(Map.class.getName());
  }

  private TypeElement element(String name) {
    return this.context.getTypeElement(name);
  }
}
