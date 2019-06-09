package compozitor.template.engine.interfaces;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.velocity.app.event.implement.IncludeRelativePath;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;

@SuppressWarnings("unchecked")
public class TemplateEngineBuilder {
	public static final String USERDIRECTIVE_TEMPLATES_LOCATION = "userdirective.templates.location";

	private static final TemplateEngineBuilder current = new TemplateEngineBuilder();

	private final RuntimeServices target;

	private final Set<Class<? extends Directive>> allDirectives = new HashSet<>();

	private TemplateEngineBuilder() {
		this.target = RuntimeSingleton.getRuntimeServices();
		this.init();
	}

	private void init() {
		this.target.addProperty(RuntimeConstants.RUNTIME_LOG_REFERENCE_LOG_INVALID, "true");
		this.target.addProperty(RuntimeConstants.VM_PERM_ALLOW_INLINE, "true");
		this.target.addProperty(RuntimeConstants.VM_PERM_ALLOW_INLINE_REPLACE_GLOBAL, "true");
		this.target.addProperty(RuntimeConstants.EVENTHANDLER_INCLUDE, IncludeRelativePath.class.getName());
		this.target.addProperty("runtime.log.logsystem.log4j.logger", "root" );

		this.withDirectives(Capitalize.class, LowerCase.class, TrimAll.class, Uncapitalize.class, UpperCase.class);
	}
	
	public TemplateEngineBuilder withClasspathTemplateRoot(){
		this.target.addProperty(RuntimeConstants.RESOURCE_LOADER, "cp");
		this.target.addProperty("cp.resource.loader.class", ClasspathResourceLoader.class.getName());
		return this;
	}
	
	public TemplateEngineBuilder withTemplateRoot(Path location){
		this.target.addProperty(RuntimeConstants.RESOURCE_LOADER, "file");
		this.target.addProperty("file.resource.loader.class", FileResourceLoader.class.getName());
		this.target.addProperty("file.resource.loader.path", location.toString());
		this.target.addProperty("file.resource.loader.cache", "false");
		return this;
	}

	public TemplateEngineBuilder withDirectives(Collection<Class<? extends Directive>> directives) {
		this.allDirectives.addAll(directives);
		this.target.addProperty("userdirective", this.toUserDirectiveValue());
		return this;
	}

	public TemplateEngineBuilder withDirectives(Class<? extends Directive>... directives) {
		return this.withDirectives(Arrays.asList(directives));
	}

	public TemplateEngineBuilder withDirectivePath(File path) {
		this.target.addProperty(USERDIRECTIVE_TEMPLATES_LOCATION, path.getAbsolutePath());
		return this;
	}

	public static TemplateEngineBuilder current() {
		return current;
	}

	public RuntimeServices build() {
		Thread thread = Thread.currentThread();
		ClassLoader loader = thread.getContextClassLoader();
		
		try {
			JoinableClassLoader.current().join(loader).join(this.getClass().getClassLoader());
			thread.setContextClassLoader(JoinableClassLoader.current());

			this.target.init();
		} finally {
			thread.setContextClassLoader(loader);
		}

		return this.target;
	}

	public Iterable<Class<? extends Directive>> directives() {
		return this.allDirectives;
	}

	private String toUserDirectiveValue() {

		StringBuffer csv = new StringBuffer();

		for (Class<? extends Directive> value : this.allDirectives) {
			csv.append(value.getName()).append(",");
		}

		csv.delete(csv.length() - 1, csv.length());

		return csv.toString();
	}
}