package eu.lestard.redux_javafx_devtool.examples.todolist.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UpdateTextFieldAction {
	private final String newValue;

	public UpdateTextFieldAction(String newValue) {
		this.newValue = newValue;
	}

	public String getNewValue() {
		return newValue;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
			.append("newValue", newValue)
			.toString();
	}
}
