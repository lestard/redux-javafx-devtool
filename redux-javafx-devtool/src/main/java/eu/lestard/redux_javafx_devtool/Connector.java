package eu.lestard.redux_javafx_devtool;

import java.util.function.BiConsumer;

/**
 * This class defines the interface to connect a redux-like implementation
 * to the DevTool by using the {@link ReduxFXDevTool#connect(Connector)} method.
 * <p/>
 * There are many possible redux(like) implementations that may all be used with the DevTool.
 * To connect one of these implementations with the DevTool a connector has to implement this interface.
 * The connector has the responsibility to notify the DevTool about new dispatched actions and the state at this point in time
 * and to replace the current state of the client application with a state defined by the DevTool to implement time-traveling.
 *
 * @param <STATE> the generic type of the client application's state.
 */
public interface Connector<STATE> {

	/**
	 * This method is invoked by the DevTool when a new state
	 * is determined by the DevTool.
	 * For example when the user uses time-traveling to get to a previous point in time,
	 * the DevTool will invoke this method with the state at this point in time.
	 * <p/>
	 * The connector implementation has to replace the state of the application with
	 * the state parameter passed to this method.
	 */
	void pushState(STATE newState);

	/**
	 * This method is invoke a single time immediately after the connector is
	 * connected to the DevTool (see {@link ReduxFXDevTool#connect(Connector)}.
	 * <p/>
	 * The connector implementation has to use the actionPublisher passed to this method
	 * to tell the DevTool about new dispatched actions.
	 * The actionPublisher takes the dispatched action and the state at this point in time
	 * as arguments.
	 *
	 * @param actionPublisher a {@link java.util.function.BiFunction} that takes the dispatched
	 *                        action as first argument and the state at this point in time
	 *                        as the second parameter.
	 */
	void initActionPublisher(BiConsumer<Object, STATE> actionPublisher);
}
