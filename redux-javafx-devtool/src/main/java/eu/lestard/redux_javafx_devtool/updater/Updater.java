package eu.lestard.redux_javafx_devtool.updater;

import eu.lestard.redux_javafx_devtool.actions.ClientActionDispatchedAction;
import eu.lestard.redux_javafx_devtool.actions.ClientActionSelectedAction;
import eu.lestard.redux_javafx_devtool.state.AppState;
import eu.lestard.redux_javafx_devtool.state.ClientAction;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

public class Updater {
	private Updater() {
	}

	public static AppState update(AppState state, Object action) {
		return Match(action).of(
			Case($(instanceOf(ClientActionDispatchedAction.class)),
				clientActionDispatchedAction -> {
					final Object clientAction = clientActionDispatchedAction.getClientAction();
					final Object clientState = clientActionDispatchedAction.getClientState();

					return state.withNewAction(
						ClientAction.create(clientAction),
						clientState
					);
				}
			),

			Case($(instanceOf(ClientActionSelectedAction.class)),
				clientActionSelectedAction -> state.withSelectedAction(clientActionSelectedAction.getClientAction())
			),

			Case($(), state)
		);
	}
}
