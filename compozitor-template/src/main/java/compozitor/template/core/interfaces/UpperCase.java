package compozitor.template.core.interfaces;

public class UpperCase extends ValueDirective {
	@Override
	public String getName() {
		return "uppercase";
	}

	@Override
	public String toString(Object value) {
		return value.toString().toUpperCase();
	}
}