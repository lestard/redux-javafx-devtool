package eu.lestard.redux_javafx_devtool.examples.todolist.actions;

public class SelectItemAction {
	private final Integer itemId;

	public SelectItemAction(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getItemId() {
		return itemId;
	}
}
