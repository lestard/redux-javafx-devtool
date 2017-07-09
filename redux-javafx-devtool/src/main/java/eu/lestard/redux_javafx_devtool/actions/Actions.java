package eu.lestard.redux_javafx_devtool.actions;

public class Actions {

	public static ClientActionDispatchedAction clientActionDispatched(Object action) {
		return new ClientActionDispatchedAction(action);
	}

	public static ClientStateUpdatedAction clientStateUpdated(Object newState) {
		return new ClientStateUpdatedAction(newState);
	}
}
