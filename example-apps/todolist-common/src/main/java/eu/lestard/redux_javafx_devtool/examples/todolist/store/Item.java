package eu.lestard.redux_javafx_devtool.examples.todolist.store;

public class Item {

	private final int id;
	private final String text;

	private Item(int id, String text) {
		this.id = id;
		this.text = text;
	}

	public static Item create(int id) {
		return new Item(id, "");
	}

	public int getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public Item withText(String newValue) {
		return new Item(this.id, newValue);
	}
}
