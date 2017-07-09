package eu.lestard.redux_javafx_devtool.examples.todolist.actions;

public class UpdateTextFieldAction {
	private final String newValue;

	public UpdateTextFieldAction(String newValue) {
		this.newValue = newValue;
	}

	public String getNewValue() {
		return newValue;
	}
}
