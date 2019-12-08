package compozitor.processor.core.test;

import java.util.List;

@TypeAnnotation
public class TypeModel {
  private Long id;

  @FieldAnnotation
  private long code;

  private String name;

  private List<String> values;

  @MethodAnnotation
  public void doSomething(List<String> values) {

  }

  @MethodAnnotationWithAttributes(firstName = "first")
  public void doSomethingWithAttributes() {

  }
}
