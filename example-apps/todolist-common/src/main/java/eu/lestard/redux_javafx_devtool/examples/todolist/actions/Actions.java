package eu.lestard.redux_javafx_devtool.examples.todolist.actions;

import eu.lestard.redux_javafx_devtool.examples.todolist.store.Item;

public class Actions {

	public static AddItemAction addItem() {
		return new AddItemAction();
	}

	public static RemoveSelectedItemAction removeSelectedItem() {
		return new RemoveSelectedItemAction();
	}

	public static UpdateTextFieldAction updateTextField(String newValue) {
		return new UpdateTextFieldAction(newValue);
	}

	public static SelectItemAction selectItem(Item item) {
		return new SelectItemAction(item.getId());
	}

	public static SelectItemAction unselectItem() {
		return new SelectItemAction(null);
	}

	public static InitAction initAction() {
		return new InitAction();
	}
}
