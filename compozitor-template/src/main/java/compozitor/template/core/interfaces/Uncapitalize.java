package compozitor.template.core.interfaces;

import org.apache.commons.lang.StringUtils;

import compozitor.template.directive.interfaces.ValueDirective;

public class Uncapitalize extends ValueDirective {
	@Override
	public String getName() {
		return "uncapitalize";
	}

	@Override
	public String toString(Object value) {
		return StringUtils.uncapitalize(value.toString());
	}
}