package compozitor.template.core.interfaces;

import compozitor.template.core.interfaces.VelocityContextBuilder.KeyValue;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Template {
	private String code;
	private String name;
	private String namespace;
	private String generator;
	private String file;
	private String criteria;
	private Boolean enabled;
	private Boolean resource;
	private Boolean testArtifact;
	
	public KeyValue toKeyValue() {
		return KeyValue.of("Template", this);
	}
}