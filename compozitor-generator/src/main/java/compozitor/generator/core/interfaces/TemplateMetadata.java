package compozitor.generator.core.interfaces;

import compozitor.template.core.interfaces.Template;
import compozitor.template.core.interfaces.TemplateContextData;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TemplateMetadata {
	private String criteria;
	private Boolean enabled;
	private String fileName;
	private String namespace;
	private Boolean resource;
	private Template template;
	private Boolean testArtifact;
	
	public TemplateContextData toContextData() {
		return TemplateContextData.of("Template", this);
	}
}