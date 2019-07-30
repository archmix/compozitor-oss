package compozitor.template.core.interfaces;

public class LowerCase extends ValueDirective {
	@Override
	public String getName() {
		return "lowercase";
	}

	@Override
	public String toString(Object value) {
		return value.toString().toLowerCase();
	}
}