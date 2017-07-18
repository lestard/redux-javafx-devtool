package eu.lestard.redux_javafx_devtool.state;

import eu.lestard.redux_javafx_devtool.updater.stateparser.StateParser;
import io.vavr.collection.Array;
import io.vavr.collection.Seq;
import io.vavr.control.Option;

public class AppState {

	private final Seq<StateHistoryEntry> stateHistory;
	private final Option<ClientAction> selectedAction;

	private final StateParser stateParser = StateParser.create();

	public static AppState create() {
		return new AppState(Array.empty(), Option.none());
	}

	private AppState(Seq<StateHistoryEntry> stateHistory,
		Option<ClientAction> selectedAction) {
		this.stateHistory = stateHistory;
		this.selectedAction = selectedAction;
	}

	public AppState withNewAction(ClientAction clientAction, Object clientState) {
		final StateHistoryEntry newHistoryEntry = StateHistoryEntry.create(clientAction, stateParser.parse("root", clientState));
		return new AppState(this.stateHistory.append(newHistoryEntry),
			selectedAction);
	}

	public AppState withSelectedAction(ClientAction action) {
		return new AppState(this.stateHistory, Option.of(action));
	}

	public Seq<StateHistoryEntry> getStateHistory() {
		return this.stateHistory;
	}

	public AppState withStateHistory(Seq<StateHistoryEntry> history) {
		return new AppState(history, this.selectedAction);
	}

	public Option<ClientAction> getSelectedAction() {
		return selectedAction;
	}
}
