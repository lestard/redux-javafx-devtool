package eu.lestard.redux_javafx_devtool.actions;

import eu.lestard.redux_javafx_devtool.state.ClientAction;

public class JumpToClientActionAction {

	private final ClientAction clientAction;

	public JumpToClientActionAction(ClientAction clientAction) {
		this.clientAction = clientAction;
	}

	public ClientAction getClientAction() {
		return clientAction;
	}
}
