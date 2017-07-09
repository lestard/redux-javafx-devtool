package eu.lestard.redux_javafx_devtool.state;

import eu.lestard.redux_javafx_devtool.updater.stateparser.StateParser;
import io.vavr.collection.Array;
import io.vavr.collection.Seq;

public class AppState {

	private final Seq<ClientAction> clientActions;
	private final StateNode clientState;

	private final StateParser stateParser = StateParser.create();

	public static AppState create() {
		return new AppState(Array.empty(), StateNode.create("root", null));
	}

	private AppState(Seq<ClientAction> clientActions, StateNode clientState) {
		this.clientActions = clientActions;
		this.clientState = clientState;
	}

	public AppState withNewAction(ClientAction userAction) {
		return new AppState(clientActions.append(userAction), clientState);
	}

	public Seq<ClientAction> getClientActions() {
		return clientActions;
	}

	public AppState withClientState(Object newState) {
		return new AppState(clientActions, stateParser.parse("root", newState));
	}

	public StateNode getClientState() {
		return clientState;
	}

}
