package compozitor.generator.core.interfaces;

import compozitor.template.core.infra.StringInputStream;
import compozitor.template.core.interfaces.Template;
import compozitor.template.core.interfaces.TemplateBuilder;
import compozitor.template.core.interfaces.TemplateContext;
import compozitor.template.core.interfaces.TemplateEngine;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor(staticName = "create")
@EqualsAndHashCode(of = "value")
public class Namespace {
  private static final String SLASH = "/";
  private static final String DOT = ".";

  private final String value;

  public static Namespace create(Path path) {
    return Namespace.create(path.toString().replace(SLASH, DOT));
  }

  public static Namespace root(){
    return Namespace.create("");
  }

  public Namespace merge(TemplateEngine engine, TemplateContext context){
    Template namespaceTemplate = TemplateBuilder.create(engine, "Code")
        .withResourceLoader(new StringInputStream(this.value.toString()))
        .build();

    return Namespace.create(namespaceTemplate.mergeToString(context));
  }

  public Path toPath() {
    return Paths.get(this.value.replace(DOT, SLASH));
  }

  @Override
  public String toString() {
    return this.value;
  }

  String accept(Filename filename){
    return new StringBuilder(this.value).append(DOT).append(filename).toString();
  }
}
