package pluginsServiceProcessorTest;

import compozitor.template.core.interfaces.TemplateContextData;

public class TestTemplateContextData implements TemplateContextData<TestTemplateContextData> {
  public String hello(){
    return "Hello";
  }

  @Override
  public String key() {
    return "Test";
  }
}
