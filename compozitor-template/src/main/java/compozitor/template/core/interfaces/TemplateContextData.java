package compozitor.template.core.interfaces;

public interface TemplateContextData<T extends TemplateContextData<T>> {
  String key();
}