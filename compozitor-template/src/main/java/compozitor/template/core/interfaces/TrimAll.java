package compozitor.template.core.interfaces;

public class TrimAll extends ValueDirective {
  @Override
  public String getName() {
    return "trimAll";
  }

  @Override
  public String toString(Object value) {
    return value.toString().replaceAll(" ", "");
  }
}
