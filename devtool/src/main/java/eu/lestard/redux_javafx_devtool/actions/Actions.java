package eu.lestard.redux_javafx_devtool.actions;

import eu.lestard.redux_javafx_devtool.state.ClientAction;

public class Actions {

	public static ClientActionDispatchedAction clientActionDispatched(Object action, Object state) {
		return new ClientActionDispatchedAction(action, state);
	}

	public static ClientActionSelectedAction selectClientAction(ClientAction action) {
		return new ClientActionSelectedAction(action);
	}

	public static TimeTravelToClientAction timeTravelToClientAction(ClientAction action) {
		return new TimeTravelToClientAction(action);
	}

	public static TimeTravelToNextActionAction timeTravelToNextAction() {
		return new TimeTravelToNextActionAction();
	}

	public static TimeTravelToPreviousActionAction timeTravelToPreviousAction() {
		return new TimeTravelToPreviousActionAction();
	}

	public static SwitchIgnoreNewActionsAction switchIgnoreNewActions() {
		return new SwitchIgnoreNewActionsAction();
	}
}
