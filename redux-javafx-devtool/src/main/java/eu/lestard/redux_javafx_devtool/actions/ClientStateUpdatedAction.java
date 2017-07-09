package eu.lestard.redux_javafx_devtool.actions;

public class ClientStateUpdatedAction {

	private final Object clientState;

	public ClientStateUpdatedAction(Object clientState) {
		this.clientState = clientState;
	}

	public Object getClientState() {
		return clientState;
	}
}

