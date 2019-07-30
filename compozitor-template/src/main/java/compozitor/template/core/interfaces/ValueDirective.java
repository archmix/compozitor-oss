package compozitor.template.core.interfaces;

import java.util.List;

public abstract class ValueDirective extends LineDirective {

	@Override
	protected String doRender(List<Variable> variables) {
		Object value = variables.get(0).getValue();
		if (value != null) {
			return this.toString(value);
		}

		return "";
	}

	public abstract String toString(Object value);
}