package compozitor.template.engine.interfaces;

import org.apache.commons.lang.StringUtils;

import compozitor.template.directive.interfaces.ValueDirective;

class Uncapitalize extends ValueDirective {
	@Override
	public String getName() {
		return "uncapitalize";
	}

	@Override
	public String toString(Object value) {
		return StringUtils.uncapitalize(value.toString());
	}
}