package compozitor.engine.core.interfaces;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import compozitor.generator.core.interfaces.MetamodelRepository;
import compozitor.generator.core.interfaces.TemplateMetadata;

public class EngineContext<T> {
	private final Map<EngineType, MetamodelRepository<T>> metadata;
	
	private final Map<EngineType, TemplateMetadata> templates;
	
	EngineContext() {
		this.metadata = new HashMap<>();
		this.templates = new HashMap<>();
	}
	
	public static <T> EngineContext<T> create(){
		return new EngineContext<>();
	}

	public void add(EngineType type, MetamodelRepository<T> repository) {
		this.metadata.put(type, repository);
	}
	
	public void add(EngineType type, TemplateMetadata template) {
		this.templates.put(type, template);
	}
	
	public void apply(BiConsumer<MetamodelRepository<T>, TemplateMetadata> consumer) {
		this.templates.keySet().forEach(type ->{
			consumer.accept(this.metadata.get(type), this.templates.get(type));
		});
	}
}
