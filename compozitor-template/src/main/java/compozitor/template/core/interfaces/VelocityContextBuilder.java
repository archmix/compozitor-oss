package compozitor.template.core.interfaces;

import org.apache.velocity.VelocityContext;

public class VelocityContextBuilder {
	private final VelocityContext target;
	
	private VelocityContextBuilder() {
		this.target = new VelocityContext();
	}
	
	public static VelocityContextBuilder create() {
		return new VelocityContextBuilder();
	}
	
	public VelocityContextBuilder add(KeyValue... entries) {
		for(KeyValue entry : entries) {
			this.target.put(entry.key(), entry.value());
		}
		return this;
	}
	
	public VelocityContext build() {
		return target;
	}
	
	public static class KeyValue {
		private final String key;
		
		private final Object value;

		public KeyValue(String key, Object value) {
			this.key = key;
			this.value = value;
		}
		
		public static KeyValue of(String key, Object value){
			return new KeyValue(key, value);
		}
		
		public String key() {
			return key;
		}
		
		public Object value() {
			return value;
		}
	}
}