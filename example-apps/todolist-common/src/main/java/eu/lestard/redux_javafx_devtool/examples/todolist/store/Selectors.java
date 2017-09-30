package eu.lestard.redux_javafx_devtool.examples.todolist.store;

import io.vavr.control.Option;

public class Selectors {

	public static boolean isAddButtonEnabled(AppState appState) {
		return !appState.getTextFieldValue().trim().isEmpty();
	}

	public static boolean isRemoveButtonEnabled(AppState appState) {
		return appState.getSelectedItemId().isDefined();
	}

	public static Option<Item> getSelectedItem(AppState appState) {
		return appState.getSelectedItemId().flatMap(id -> appState.getItems().find(item -> item.getId() == id));
	}

}
