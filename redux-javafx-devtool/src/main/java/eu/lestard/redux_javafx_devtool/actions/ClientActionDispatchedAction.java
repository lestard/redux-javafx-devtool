package eu.lestard.redux_javafx_devtool.actions;

public class ClientActionDispatchedAction {
	private final Object clientAction;

	public ClientActionDispatchedAction(Object clientAction) {
		this.clientAction = clientAction;
	}

	public Object getClientAction() {
		return clientAction;
	}
}
