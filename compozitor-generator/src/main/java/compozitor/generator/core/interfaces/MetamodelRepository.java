package compozitor.generator.core.interfaces;

import compozitor.template.core.interfaces.TemplateContextData;

public interface MetamodelRepository<T> {
	Iterable<T> list();
	
	TemplateContextData toTemplateContextData(T value);
}
