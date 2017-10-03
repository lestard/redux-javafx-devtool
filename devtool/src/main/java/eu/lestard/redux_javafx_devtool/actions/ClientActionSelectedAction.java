package eu.lestard.redux_javafx_devtool.actions;

import eu.lestard.redux_javafx_devtool.state.ClientAction;

public class ClientActionSelectedAction {

	private final ClientAction clientAction;

	public ClientActionSelectedAction(ClientAction clientAction) {
		this.clientAction = clientAction;
	}

	public ClientAction getClientAction() {
		return clientAction;
	}
}
