package eu.lestard.redux_javafx_devtool.state;

import eu.lestard.redux_javafx_devtool.updater.stateparser.StateParser;
import io.vavr.collection.LinkedHashMap;
import io.vavr.control.Option;

public class AppState {

	private final LinkedHashMap<ClientAction, StateNode> stateHistory;
	private final Option<ClientAction> selectedAction;

	private final StateParser stateParser = StateParser.create();

	public static AppState create() {
		return new AppState(LinkedHashMap.empty(), Option.none());
	}

	private AppState(LinkedHashMap<ClientAction, StateNode> stateHistory,
		Option<ClientAction> selectedAction) {
		this.stateHistory = stateHistory;
		this.selectedAction = selectedAction;
	}

	public AppState withNewAction(ClientAction clientAction, Object clientState) {
		return new AppState(this.stateHistory.put(clientAction, stateParser.parse("root", clientState)),
			selectedAction);
	}

	public AppState withSelectedAction(ClientAction action) {
		return new AppState(this.stateHistory, Option.of(action));
	}

	public LinkedHashMap<ClientAction, StateNode> getStateHistory() {
		return this.stateHistory;
	}

	public Option<ClientAction> getSelectedAction() {
		return selectedAction;
	}
}
