package compozitor.template.core.interfaces;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;

import compozitor.template.core.infra.StringInputStream;

public class TemplateBuilder {
	private final Template target;

	private TemplateBuilder(String name) {
		this.target = new Template();
		this.target.setName(name);
		this.target.setRuntimeServices(TemplateEngineBuilder.current().build());
	}

	public static TemplateBuilder create(String templateName) {
		return new TemplateBuilder(templateName);
	}

	public TemplateResourceBuilder withResourceLoader(File file) {
		try {
			byte[] fileBytes = Files.readAllBytes(file.toPath());
			InputStream inputStream = new ByteArrayInputStream(fileBytes);
			return this.withResourceLoader(inputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public TemplateResourceBuilder withResourceLoader(String loader) {
		return withResourceLoader(new StringInputStream(loader));
	}

	public TemplateResourceBuilder withResourceLoader(byte[] bytes) {
		return withResourceLoader(new ByteArrayInputStream(bytes));
	}

	public TemplateResourceBuilder withResourceLoader(InputStream inputStream) {
		this.target.setResourceLoader(new InputStreamLoader(inputStream));
		return new TemplateResourceBuilder();
	}

	public class TemplateResourceBuilder {

		public TemplateProxy build() {
			try {
				TemplateBuilder.this.target.process();
				return new TemplateProxy(TemplateBuilder.this.target);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static class TemplateProxy {
		private final Template template;

		public TemplateProxy(Template template) {
			this.template = template;
		}

		public void merge(VelocityContext context, Writer writer) {
			this.template.merge(context, writer);
		}

		public String mergeToString(VelocityContext context) {
			StringWriter writer = new StringWriter();
			this.template.merge(context, writer);
			return writer.toString();
		}

		public InputStream mergeToStream(VelocityContext context) {
			return new StringInputStream(this.mergeToString(context));
		}
	}

	class InputStreamLoader extends org.apache.velocity.runtime.resource.loader.ResourceLoader {
		private InputStream input;

		public InputStreamLoader(InputStream input) {
			this.input = input;
		}

		@Override
		public long getLastModified(Resource resource) {
			return 0;
		}

		@Override
		public InputStream getResourceStream(String resource) throws ResourceNotFoundException {
			return this.input;
		}

		@Override
		public void init(ExtendedProperties properties) {
			return;
		}

		@Override
		public boolean isSourceModified(Resource resource) {
			return false;
		}
	}
}
