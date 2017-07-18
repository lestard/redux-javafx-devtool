package eu.lestard.redux_javafx_devtool.state;

import io.vavr.collection.Seq;
import io.vavr.control.Option;

public class Selectors {

	public static Option<StateNode> getClientState(AppState appState) {
		final Option<ClientAction> selectedAction = appState.getSelectedAction();

		if(selectedAction.isEmpty()) {
			return appState.getStateHistory().lastOption().map(StateHistoryEntry::getState);
		} else {
			final ClientAction action = selectedAction.get();

			return appState.getStateHistory()
				.find(historyEntry -> historyEntry.getAction().getId().equals(action.getId()))
				.map(StateHistoryEntry::getState);
		}
	}

	public static Seq<ClientAction> getClientActions(AppState state) {
		return state.getStateHistory().map(StateHistoryEntry::getAction);
	}

}
