package eu.lestard.redux_javafx_devtool.state;

import java.time.LocalDateTime;

public class ClientAction {

	private final Object action;
	private final LocalDateTime dispatchTime;

	private ClientAction(Object action, LocalDateTime dispatchTime) {
		this.action = action;
		this.dispatchTime = dispatchTime;
	}

	public static ClientAction create(Object action) {
		return new ClientAction(action, LocalDateTime.now());
	}

	public static ClientAction create(Object action, LocalDateTime time) {
		return new ClientAction(action, time);
	}

	public Object getAction() {
		return action;
	}

	public LocalDateTime getDispatchTime() {
		return dispatchTime;
	}


}
