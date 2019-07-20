package compozitor.template.directive.interfaces;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.TemplateInitException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.parser.node.Node;

import compozitor.template.core.interfaces.Template;
import compozitor.template.core.interfaces.TemplateBuilder;
import compozitor.template.core.interfaces.TemplateContext;
import compozitor.template.core.interfaces.TemplateEngineBuilder;

public abstract class Directive extends org.apache.velocity.runtime.directive.Directive {
	public static final String DIRECTIVE_FILE_EXTENSION = ".cdf";

	private File source;
	
	@Override
	public void init(RuntimeServices rs, InternalContextAdapter context, Node node) throws TemplateInitException {
		super.init(rs, context, node);
		this.resolve(rs, this.getName());
	}

	protected void resolve(RuntimeServices rs, String directiveName) {
		this.source = new File(rs.getString(TemplateEngineBuilder.USERDIRECTIVE_TEMPLATES_LOCATION), directiveName + DIRECTIVE_FILE_EXTENSION);
	}

	protected File source() {
		return this.source;
	}

	public static void setSource(RuntimeServices rs, File location) {
		rs.setProperty(TemplateEngineBuilder.USERDIRECTIVE_TEMPLATES_LOCATION, location.getAbsolutePath());
	}

	@Override
	public String getName() {
		return StringUtils.uncapitalize(this.getClass().getSimpleName());
	}

	@Override
	public final boolean render(InternalContextAdapter context, Writer writer, Node node) throws ResourceNotFoundException {
		try {
			List<Variable> variables = new ArrayList<Variable>();

			for (int i = 0; i < node.jjtGetNumChildren(); i++) {
				Variable variable = new Variable(node.jjtGetChild(i).literal(), node.jjtGetChild(i).value(context));
				variables.add(variable);
			}

			String generated = this.doRender(variables);
			writer.write(generated);
			
			return true;
		} catch (Exception e) {
			throw new ResourceNotFoundException(e);
		}
	}

	protected boolean merge(VelocityContext context, Writer writer) {
		try {
			byte[] sourceBytes = Files.readAllBytes(this.source.toPath());
			InputStream loader = new ByteArrayInputStream(sourceBytes);
			Template directive = TemplateBuilder.create(this.getName()).withResourceLoader(loader).build();
			directive.merge(TemplateContext.valueOf(context), writer);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static class Variable {
		private String name;

		private Object value;

		public Variable(String name, Object value) {
			super();
			this.name = name.replace("$", "").replace("{", "").replace("}", "");
			this.value = value;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public Object getValue() {
			return value;
		}
	}

	protected abstract String doRender(List<Variable> variables);
}