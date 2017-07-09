package eu.lestard.redux_javafx_devtool.examples.todolist.store;

public class Selectors {

	public static boolean isAddButtonEnabled(AppState appState) {
		return !appState.getTextFieldValue().trim().isEmpty();
	}

	public static boolean isRemoveButtonEnabled(AppState appState) {
		return appState.getSelectedItemId() != null;
	}

}
