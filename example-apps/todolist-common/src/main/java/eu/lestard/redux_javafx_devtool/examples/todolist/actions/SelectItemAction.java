package eu.lestard.redux_javafx_devtool.examples.todolist.actions;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SelectItemAction {
	private final Integer itemId;

	public SelectItemAction(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getItemId() {
		return itemId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
			.append("itemId", itemId)
			.toString();
	}
}
