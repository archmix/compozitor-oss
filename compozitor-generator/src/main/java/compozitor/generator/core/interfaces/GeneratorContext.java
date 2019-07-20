package compozitor.generator.core.interfaces;

import compozitor.template.core.interfaces.TemplateContext;
import lombok.Getter;

@Getter
public class GeneratorContext {
	private final TemplateContext templateContext;
	
	private final TemplateMetadata templateMetadata;

	GeneratorContext(TemplateContext context, TemplateMetadata metadata) {
		this.templateContext = context;
		this.templateMetadata = metadata;
	}
}
