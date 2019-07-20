package compozitor.generator.core.interfaces;

import java.io.InputStream;

import compozitor.template.core.infra.StringInputStream;
import compozitor.template.core.interfaces.Template;
import compozitor.template.core.interfaces.TemplateBuilder;
import compozitor.template.core.interfaces.TemplateContext;
import compozitor.template.core.interfaces.TemplateContextData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class Code {
	private final TemplateMetadata metadata;

	private InputStream content;

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

	public GeneratedCode toGeneratedCode() {
		GeneratedCode generatedCode = new GeneratedCode();
		generatedCode.setContent(this.content);
		generatedCode.setFileName(this.toFileName());
		generatedCode.setPath(this.toPath());
		generatedCode.setResource(this.metadata.getResource());
		generatedCode.setTestArtifact(this.metadata.getTestArtifact());
		
		return generatedCode;
	}

	private String toPath() {
		return this.getNamespace().replace('.', '/');
	}

	public String getNamespace() {
		TemplateContext context = TemplateContext.create();
		context.add(this.metadata.toContextData()).add(toTemplateContextData());

		return this.getTemplate(this.metadata.getNamespace()).mergeToString(context);
	}

	private String toFileName() {
		TemplateContext context = TemplateContext.create();
		context.add(this.metadata.toContextData()).add(toTemplateContextData());

		return this.getTemplate(this.metadata.getFileName()).mergeToString(context);
	}

	private Template getTemplate(String attribute) {
		return TemplateBuilder.create("Source").withResourceLoader(new StringInputStream(attribute)).build();
	}
	
	public TemplateContextData toTemplateContextData() {
		return TemplateContextData.of("Code", this);
	}
}