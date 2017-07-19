package eu.lestard.redux_javafx_devtool.state;

import io.vavr.collection.Seq;
import io.vavr.control.Option;

public class Selectors {

	/**
	 * Returns the client's state at the current time-travel-position parsed as {@link StateNode}
	 */
	public static Option<StateNode> getClientStateNode(AppState appState) {
		return appState.getStateHistory()
			.filter(entry -> entry.getAction().isActive())
			.lastOption()
			.map(StateHistoryEntry::getState);
	}

	/**
	 * Returns the root object of the client's state.
	 */
	public static Object getClientStateObject(AppState appState) {
		return getClientStateNode(appState).map(StateNode::getValue).getOrNull();
	}

	public static Seq<ClientAction> getClientActions(AppState state) {
		return state.getStateHistory().map(StateHistoryEntry::getAction);
	}

}
