package compozitor.processor.core.interfaces;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;

import lombok.Getter;

@Getter
public class FieldModel extends Model<VariableElement> {
	private final Annotations annotations;
	
	private final Modifiers modifiers;
	
	private final TypeModel type;
	
	private final String name;
	
	public FieldModel(ProcessingEnvironment environment, VariableElement element, Annotations annotations,
			Modifiers modifiers, TypeModel type) {
		super(environment, element);
		this.annotations = annotations;
		this.modifiers = modifiers;
		this.type = type;
		this.name = element.getSimpleName().toString();
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
