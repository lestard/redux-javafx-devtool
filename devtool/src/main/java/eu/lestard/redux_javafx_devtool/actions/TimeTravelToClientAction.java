package eu.lestard.redux_javafx_devtool.actions;

import eu.lestard.redux_javafx_devtool.state.ClientAction;

public class TimeTravelToClientAction {

	private final ClientAction clientAction;

	public TimeTravelToClientAction(ClientAction clientAction) {
		this.clientAction = clientAction;
	}

	public ClientAction getClientAction() {
		return clientAction;
	}
}
