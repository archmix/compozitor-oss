package compozitor.template.core.interfaces;

import org.apache.commons.lang.StringUtils;

public class Capitalize extends ValueDirective {
	@Override
	public String getName() {
		return "capitalize";
	}

	@Override
	public String toString(Object value) {
		return StringUtils.capitalize(value.toString());
	}
}