package compozitor.template.core.interfaces;

import compozitor.template.directive.interfaces.ValueDirective;

class TrimAll extends ValueDirective {
	@Override
	public String getName() {
		return "trimAll";
	}

	@Override
	public String toString(Object value) {
		return value.toString().replaceAll(" ", "");
	}
}