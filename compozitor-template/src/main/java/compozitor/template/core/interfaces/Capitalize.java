package compozitor.template.core.interfaces;

import org.apache.commons.lang.StringUtils;

import compozitor.template.directive.interfaces.ValueDirective;

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