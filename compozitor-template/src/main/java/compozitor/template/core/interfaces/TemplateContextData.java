package compozitor.template.core.interfaces;

public class TemplateContextData {
	private final String key;

	private final Object value;

	public TemplateContextData(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public static TemplateContextData of(String key, Object value) {
		return new TemplateContextData(key, value);
	}

	public String key() {
		return key;
	}

	public Object value() {
		return value;
	}
}