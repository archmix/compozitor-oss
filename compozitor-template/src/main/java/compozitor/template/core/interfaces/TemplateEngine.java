package compozitor.template.core.interfaces;

import org.apache.velocity.runtime.RuntimeServices;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TemplateEngine {
  private final RuntimeServices engine;

  TemplateEngine(RuntimeServices runtimeServices) {
    this.engine = runtimeServices;
  }

  RuntimeServices getRuntimeServices() {
    return engine;
  }

  public Template getTemplate(String name) {
    return this.getTemplate(name, StandardCharsets.UTF_8);
  }

  public Template getTemplate(String name, Charset charset) {
    return new Template(this.engine.getTemplate(name, charset.name()));
  }
}
