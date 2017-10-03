package eu.lestard.redux_javafx_devtool.state;

import java.time.LocalDateTime;

public class ClientAction {
	private final String id;
	private final Object action;
	private final LocalDateTime dispatchTime;

	// the ActionView allows time-traveling by jumping to actions/states the past.
	// this flag signals whether this action was incorporated to determine the currently active state of
	// if it lays "in the future".
	private final boolean active;

	private ClientAction(String id, Object action, LocalDateTime dispatchTime, boolean active) {
		this.id = id;
		this.action = action;
		this.dispatchTime = dispatchTime;
		this.active = active;
	}

	public static ClientAction create(String id, Object action) {
		return new ClientAction(id, action, LocalDateTime.now(), true);
	}

	public static ClientAction create(String id, Object action, LocalDateTime time) {
		return new ClientAction(id, action, time, true);
	}

	public String getId() {
		return id;
	}

	public Object getAction() {
		return action;
	}

	public LocalDateTime getDispatchTime() {
		return dispatchTime;
	}

	public boolean isActive() {
		return active;
	}

	public ClientAction withActiveFlag(boolean active) {
		return new ClientAction(this.id, this.action, this.dispatchTime, active);
	}
}
