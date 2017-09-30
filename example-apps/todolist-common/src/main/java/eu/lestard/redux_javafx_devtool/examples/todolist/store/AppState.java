package eu.lestard.redux_javafx_devtool.examples.todolist.store;

import io.vavr.collection.Array;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class AppState {

	private final String textFieldValue;
	private final Seq<Item> items;
	private final Integer selectedItemId;

	private AppState(String textFieldValue,
		Seq<Item> items, Integer selectedItemId) {
		this.textFieldValue = textFieldValue;
		this.items = items;
		this.selectedItemId = selectedItemId;
	}

	public static AppState create() {
		return new AppState("", Array.empty(), null);
	}

	public String getTextFieldValue() {
		return textFieldValue;
	}

	public AppState withTextFieldValue(String value) {
		return new AppState(value, this.items, this.selectedItemId);
	}

	public Seq<Item> getItems() {
		return items;
	}

	public AppState withItems(Seq<Item> items) {
		return new AppState(this.textFieldValue, items, this.selectedItemId);
	}

	public Option<Integer> getSelectedItemId() {
		return Option.of(selectedItemId);
	}

	public AppState withSelectionRemoved() {
		return withSelectedItemId(null);
	}

	public AppState withSelectedItemId(Integer id) {
		return new AppState(this.textFieldValue, this.items, id);
	}

	public AppState withSelectedItem(Item item) {
		return withSelectedItemId(item.getId());
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("textFieldValue", textFieldValue)
				.append("items", items)
				.append("selectedItemId", selectedItemId)
				.toString();
	}
}
