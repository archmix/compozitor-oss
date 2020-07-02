package compozitor.generator.core.interfaces;

import toolbox.resources.interfaces.StringInputStream;
import compozitor.template.core.interfaces.Template;
import compozitor.template.core.interfaces.TemplateBuilder;
import compozitor.template.core.interfaces.TemplateContext;
import compozitor.template.core.interfaces.TemplateEngine;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "value")
public class Namespace {
  private static final String SLASH = "/";
  private static final String DOT = ".";

  private final String value;

  public static Namespace create(String path) {
    return new Namespace(path.replace(SLASH, DOT));
  }

  public static Namespace create(Path path) {
    return create(path.toString());
  }

  public static Namespace root() {
    return create("");
  }

  public Namespace merge(TemplateEngine engine, TemplateContext context) {
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

  String accept(Filename filename) {
    return new StringBuilder(this.value).append(DOT).append(filename).toString();
  }
}
