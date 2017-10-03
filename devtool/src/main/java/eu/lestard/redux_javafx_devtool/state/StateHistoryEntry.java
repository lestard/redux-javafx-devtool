package eu.lestard.redux_javafx_devtool.state;

public class StateHistoryEntry {

	private final ClientAction action;
	private final StateNode state;

	public static StateHistoryEntry create(ClientAction action, StateNode state) {
		return new StateHistoryEntry(action, state);
	}

	private StateHistoryEntry(ClientAction action, StateNode state) {
		this.action = action;
		this.state = state;
	}

	public ClientAction getAction() {
		return action;
	}

	public StateHistoryEntry withAction(ClientAction action) {
		return new StateHistoryEntry(action, this.state);
	}

	public StateNode getState() {
		return state;
	}

	public StateHistoryEntry withState(StateNode state) {
		return new StateHistoryEntry(this.action, state);
	}
}
