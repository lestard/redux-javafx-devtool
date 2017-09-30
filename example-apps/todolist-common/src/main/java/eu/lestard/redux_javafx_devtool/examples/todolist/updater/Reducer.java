package eu.lestard.redux_javafx_devtool.examples.todolist.updater;

import eu.lestard.redux_javafx_devtool.examples.todolist.actions.AddItemAction;
import eu.lestard.redux_javafx_devtool.examples.todolist.actions.RemoveSelectedItemAction;
import eu.lestard.redux_javafx_devtool.examples.todolist.actions.SelectItemAction;
import eu.lestard.redux_javafx_devtool.examples.todolist.actions.UpdateTextFieldAction;
import eu.lestard.redux_javafx_devtool.examples.todolist.store.AppState;
import eu.lestard.redux_javafx_devtool.examples.todolist.store.Item;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

public class Reducer {

	private Reducer () {}

	public static AppState reduce(AppState state, Object action) {
		return Match(action).of(
			Case($(instanceOf(UpdateTextFieldAction.class)),
				updateAction -> state.withTextFieldValue(updateAction.getNewValue())
			),

			Case($(instanceOf(AddItemAction.class)),
				state.withItems(
					state.getItems()
						.append(Item
							.create(calcNextItemId(state))
							.withText(state.getTextFieldValue())
						)
					).withTextFieldValue("")
			),

			Case($(instanceOf(RemoveSelectedItemAction.class)),
				removeAction -> state.getSelectedItemId()
					.map(id ->
						state.withItems(
							state.getItems().filter(
								item -> item.getId() != id
							)
						).withSelectionRemoved()
					).getOrElse(state)
			),

			Case($(instanceOf(SelectItemAction.class)),
				selectAction -> {
					if(selectAction.getItemId() == null) {
						return state.withSelectionRemoved();
					} else {
						return state.withSelectedItemId(selectAction.getItemId());
					}
				}
			),

			Case($(), state)
		);
	}

	private static int calcNextItemId(AppState state) {
		final Integer maxId = state.getItems()
			.map(Item::getId)
			.max()
			.getOrElse(0);

		return maxId + 1;
	}
}
