package eu.lestard.redux_javafx_devtool.updater;

import eu.lestard.redux_javafx_devtool.actions.ClientActionDispatchedAction;
import eu.lestard.redux_javafx_devtool.actions.ClientActionSelectedAction;
import eu.lestard.redux_javafx_devtool.actions.JumpToClientActionAction;
import eu.lestard.redux_javafx_devtool.state.AppState;
import eu.lestard.redux_javafx_devtool.state.ClientAction;
import eu.lestard.redux_javafx_devtool.state.StateHistoryEntry;
import eu.lestard.redux_javafx_devtool.state.StateNode;
import eu.lestard.redux_javafx_devtool.util.IdGenerator;
import io.vavr.collection.Seq;
import io.vavr.control.Option;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

public class Updater {
	private static final IdGenerator idGenerator = IdGenerator.create(0);

	private Updater() {
	}

	public static AppState update(AppState state, Object action) {
		return Match(action).of(
			Case($(instanceOf(ClientActionDispatchedAction.class)),
				clientActionDispatchedAction -> {
					final Object clientAction = clientActionDispatchedAction.getClientAction();
					final Object clientState = clientActionDispatchedAction.getClientState();

					return state.withNewAction(
						ClientAction.create(idGenerator.getId(), clientAction),
						clientState
					);
				}
			),

			Case($(instanceOf(ClientActionSelectedAction.class)),
				clientActionSelectedAction -> state.withSelectedAction(clientActionSelectedAction.getClientAction())
			),

			Case($(instanceOf(JumpToClientActionAction.class)),
				jumpToClientActionAction -> {
					// the action that we jump to
					final ClientAction targetAction = jumpToClientActionAction.getClientAction();

					final Option<StateNode> targetStateOption = state.getStateHistory()
						.find(historyEntry -> historyEntry.getAction().getId().equals(targetAction.getId()))
						.map(StateHistoryEntry::getState);

					if(targetStateOption.isEmpty()) {
						return state;
					}

					final Seq<StateHistoryEntry> historyWithDeactivatedActions = state.getStateHistory()
						.map(historyEntry -> {
							final ClientAction historyEntryAction = historyEntry.getAction();

							if(historyEntryAction.getDispatchTime().isAfter(targetAction.getDispatchTime())) {
								return historyEntry.withAction(historyEntryAction.withActiveFlag(false));
							} else {
								if(historyEntryAction.isActive()) {
									return historyEntry;
								} else {
									return historyEntry.withAction(historyEntryAction.withActiveFlag(true));
								}
							}
						});

					return state
						.withSelectedAction(targetAction)
						.withStateHistory(historyWithDeactivatedActions);
				}
			),

			Case($(), state)
		);
	}
}
