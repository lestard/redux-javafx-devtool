package eu.lestard.redux_javafx_devtool.actions;

public class ClientActionDispatchedAction {
	private final Object clientAction;
	private final Object clientState;

	public ClientActionDispatchedAction(Object clientAction, Object clientState) {
		this.clientAction = clientAction;
		this.clientState = clientState;
	}

	public Object getClientAction() {
		return clientAction;
	}

	public Object getClientState() {
		return clientState;
	}
}
