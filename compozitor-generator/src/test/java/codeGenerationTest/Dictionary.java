package codeGenerationTest;

import compozitor.template.core.interfaces.TemplateContextData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "create")
@Getter
public class Dictionary implements TemplateContextData<Dictionary> {
  private final String description;

  public String key() {
    return "Dictionary";
  }
}
