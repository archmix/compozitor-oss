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

	private final TemplateContext context;
	
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
		String namespace = this.getNamespace();
		String fileName = this.toFileName();
		String qualifiedName = new StringBuilder(namespace).append(".").append(fileName).toString();
		
		GeneratedCode generatedCode = new GeneratedCode();
		generatedCode.setContent(this.content);
		generatedCode.setFileName(fileName);
		generatedCode.setQualifiedName(qualifiedName);
		generatedCode.setPath(namespace.replace('.', '/'));
		generatedCode.setResource(this.metadata.getResource());
		generatedCode.setTestArtifact(this.metadata.getTestArtifact());
		
		return generatedCode;
	}

	public String getNamespace() {
		context.add(this.metadata.toContextData()).add(toTemplateContextData());

		return this.getTemplate(this.metadata.getNamespace()).mergeToString(context);
	}

	private String toFileName() {
		context.add(this.metadata.toContextData()).add(toTemplateContextData());

		return this.getTemplate(this.metadata.getFileName()).mergeToString(context);
	}

	private Template getTemplate(String attribute) {
		return TemplateBuilder.create("Code").withResourceLoader(new StringInputStream(attribute)).build();
	}
	
	public TemplateContextData toTemplateContextData() {
		return TemplateContextData.of("Code", this);
	}
}