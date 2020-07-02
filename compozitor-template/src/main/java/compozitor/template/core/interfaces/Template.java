package compozitor.template.core.interfaces;

import toolbox.resources.interfaces.StringInputStream;

import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;

public class Template {
  private final org.apache.velocity.Template template;

  public Template(org.apache.velocity.Template template) {
    this.template = template;
  }

  public void merge(TemplateContext context, Writer writer) {
    this.template.merge(context.getVelocityContext(), writer);
  }

  public String mergeToString(TemplateContext context) {
    StringWriter writer = new StringWriter();
    this.template.merge(context.getVelocityContext(), writer);
    return writer.toString();
  }

  public InputStream mergeToStream(TemplateContext context) {
    return new StringInputStream(this.mergeToString(context));
  }
}
