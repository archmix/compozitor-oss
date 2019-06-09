package compozitor.generator.core.interfaces;

import java.io.InputStream;

import org.apache.velocity.VelocityContext;

import compozitor.template.core.infra.StringInputStream;
import compozitor.template.core.interfaces.Template;
import compozitor.template.core.interfaces.TemplateBuilder;
import compozitor.template.core.interfaces.VelocityContextBuilder;
import compozitor.template.core.interfaces.TemplateBuilder.TemplateProxy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TemplateSource {
	private final Template config;
	@Getter
	private final String name;

	private InputStream content;

	public TemplateSource(String name, Template config) {
		this.name = name;
		this.config = config;
	}

	public String getResourceName() {
		try {
			String fileName = this.toFileName();

			return fileName.substring(0, fileName.indexOf("."));
		} catch (Exception e) {
			return null;
		}
	}

	public void setContent(InputStream content) {
		this.content = content;
	}

	public GeneratedSource toGeneratedSource() {
		GeneratedSource bean;

		bean = new GeneratedSource();
		bean.setContent(this.content);
		bean.setFileName(this.toFileName());
		bean.setPath(this.toPath());
		bean.setResource(this.config.getResource());
		bean.setTestArtifact(this.config.getTestArtifact());
		return bean;
	}

	private String toPath() {
		return this.getNamespace().replace('.', '/');
	}

	public String getNamespace() {
		VelocityContext context = VelocityContextBuilder.create().add(this.config.toKeyValue()).build();
		context.put("Source", this);

		return this.getTemplate(this.config.getNamespace()).mergeToString(context);
	}

	private String toFileName() {
		VelocityContext context = VelocityContextBuilder.create().add(this.config.toKeyValue()).build();
		context.put("Source", this);

		return this.getTemplate(this.config.getFile()).mergeToString(context);
	}

	private TemplateProxy getTemplate(String attribute) {
		return TemplateBuilder.create("Source").withResourceLoader(new StringInputStream(attribute)).build();
	}
}