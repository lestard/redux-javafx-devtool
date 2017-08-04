package eu.lestard.redux_javafx_devtool.examples.todolist.store;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("id", id)
				.append("text", text)
				.toString();
	}
}
