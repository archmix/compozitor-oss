package compozitor.processor.core.interfaces;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;

import lombok.Getter;

@Getter
public class ArgumentModel extends Model<VariableElement>{
	private final Annotations annotations;
	
	private final TypeModel type;
	
	private final String name;

	public ArgumentModel(ProcessingEnvironment environment, VariableElement element, Annotations annotations,
			TypeModel type) {
		super(environment, element);
		this.annotations = annotations;
		this.type = type;
		this.name = element.getSimpleName().toString();
	}
}
