package compozitor.template.core.interfaces;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.velocity.app.event.implement.IncludeRelativePath;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;
import compozitor.template.core.infra.JoinableClassLoader;
import compozitor.template.core.infra.MacrosLoader;
import compozitor.template.core.infra.S3Resource;
import compozitor.template.core.infra.S3ResourceLoader;

@SuppressWarnings("unchecked")
public class TemplateEngineBuilder {
  private static final String USER_DIRECTIVE = "userdirective";

  public static final String USERDIRECTIVE_TEMPLATES_LOCATION = "userdirective.templates.location";

  public static final String VELOCIMACRO_LOCATION = "velocimacro.library";

  public static final String VELOCIMACRO_ALLOW_INLINE_TEMPLATE =
      "velocimacro.permissions.allow.inline";

  private final RuntimeServices target;

  private final Set<Class<? extends Directive>> directives = new HashSet<>();
  
  private final Set<String> macros = new HashSet<>();

  public static TemplateEngineBuilder create() {
    return new TemplateEngineBuilder();
  }

  private TemplateEngineBuilder() {
    this.target = RuntimeSingleton.getRuntimeServices();
    this.init();
  }

  private void init() {
    this.target.addProperty(RuntimeConstants.VM_LIBRARY, "");
    this.target.addProperty(RuntimeConstants.RUNTIME_LOG_REFERENCE_LOG_INVALID, "true");
    this.target.addProperty(RuntimeConstants.VM_PERM_ALLOW_INLINE, "true");
    this.target.addProperty(RuntimeConstants.VM_PERM_ALLOW_INLINE_REPLACE_GLOBAL, "true");
    this.target.addProperty(RuntimeConstants.EVENTHANDLER_INCLUDE,
        IncludeRelativePath.class.getName());
    this.target.addProperty("runtime.log.logsystem.log4j.logger", "root");

    this.target.setProperty(VELOCIMACRO_ALLOW_INLINE_TEMPLATE, "true");

    this.addDirectives(Capitalize.class, LowerCase.class, Render.class, TrimAll.class,
        Uncapitalize.class, UpperCase.class);
  }

  public TemplateEngineBuilder withClasspathTemplateLoader() {
    this.target.addProperty(RuntimeConstants.RESOURCE_LOADER, "cp");
    this.target.addProperty("cp.resource.loader.class", ClasspathResourceLoader.class.getName());
    return this;
  }

  public TemplateEngineBuilder withPathTemplateLoader(Path location) {
    this.target.addProperty(RuntimeConstants.RESOURCE_LOADER, "file");
    this.target.addProperty("file.resource.loader.class", FileResourceLoader.class.getName());
    this.target.addProperty("file.resource.loader.path", location.toString());
    this.target.addProperty("file.resource.loader.cache", "false");
    return this;
  }

  public TemplateEngineBuilder withS3TemplateLoader(S3Bucket s3Bucket) {
    this.target.addProperty(RuntimeConstants.RESOURCE_LOADER, S3Resource.resourceName());
    this.target.addProperty(S3Resource.loader.propertyKey(), S3ResourceLoader.class.getName());
    this.target.addProperty(S3Resource.bucket.propertyKey(), s3Bucket.name());
    return this;
  }

  public TemplateEngineBuilder addDirectives(Collection<Class<? extends Directive>> directives) {
    this.directives.addAll(directives);
    this.target.addProperty(USER_DIRECTIVE, this.toUserDirectiveValue());
    return this;
  }

  public TemplateEngineBuilder addDirectives(Class<? extends Directive>... directives) {
    return this.addDirectives(Arrays.asList(directives));
  }

  public TemplateEngineBuilder withDirectivePath(Path path) {
    this.target.setProperty(USERDIRECTIVE_TEMPLATES_LOCATION, path.toString());
    return this;
  }

  public TemplateEngineBuilder addMacros(Path path, Consumer<Iterable<String>> added) {
    Stream<String> macroFiles = MacrosLoader.create().list(path);
    macroFiles.forEach(this.macros::add);
    
    added.accept(this.macros);
    this.target.setProperty(VELOCIMACRO_LOCATION, this.macros.stream().collect(Collectors.joining(",")));

    return this;
  }

  public TemplateEngine build() {
    Thread thread = Thread.currentThread();
    ClassLoader loader = thread.getContextClassLoader();

    try {
      // This is needed for OSGI environments
      JoinableClassLoader joinedClassLoader =
          JoinableClassLoader.create().join(loader).join(this.getClass().getClassLoader());
      thread.setContextClassLoader(joinedClassLoader);

      this.target.init();
    } finally {
      thread.setContextClassLoader(loader);
    }

    return new TemplateEngine(this.target);
  }

  public Iterable<Class<? extends Directive>> directives() {
    return this.directives;
  }

  private String toUserDirectiveValue() {

    StringBuffer csv = new StringBuffer();

    for (Class<? extends Directive> value : this.directives) {
      csv.append(value.getName()).append(",");
    }

    csv.delete(csv.length() - 1, csv.length());

    return csv.toString();
  }
}
