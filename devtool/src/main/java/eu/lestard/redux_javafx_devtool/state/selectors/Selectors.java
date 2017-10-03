package eu.lestard.redux_javafx_devtool.state.selectors;

import eu.lestard.redux_javafx_devtool.state.AppState;
import eu.lestard.redux_javafx_devtool.state.ClientAction;
import eu.lestard.redux_javafx_devtool.state.StateHistoryEntry;
import eu.lestard.redux_javafx_devtool.state.StateNode;
import io.vavr.collection.Seq;
import io.vavr.control.Option;

public class Selectors {

	/**
	 * @param appState the app state
	 * @return the client's state at the current time-travel-position parsed as {@link StateNode}
	 */
	public static Option<StateNode> getClientStateNode(AppState appState) {
		return appState.getStateHistory()
			.filter(entry -> entry.getAction().isActive())
			.lastOption()
			.map(StateHistoryEntry::getState);
	}

	/**
	 * @param appState the app state
	 * @return the root object of the client's state.
	 */
	public static Object getClientStateObject(AppState appState) {
		return getClientStateNode(appState).map(StateNode::getValue).getOrNull();
	}

	public static Seq<ClientAction> getClientActions(AppState state) {
		return state.getStateHistory().map(StateHistoryEntry::getAction);
	}

	/**
	 * If a specific client action is selected ({@link AppState#getSelectedAction()}),
	 * this action is returned.
	 * Otherwise the action at the current time-travel-position is returned.
	 * By default this is the last action.
	 * <p>
	 * If no action was dispatched yet this selector returns {@link Option#none()}.
	 *
	 * @param state the app state
	 * @return the selected client action
	 */
	public static Option<ClientAction> getSelectedAction(AppState state) {
		return state
			.getSelectedAction()
			.orElse(
				state
					.getStateHistory()
					.filter(entry ->
						entry.getAction().isActive())
					.lastOption()
					.map(StateHistoryEntry::getAction)
			);
	}

}
