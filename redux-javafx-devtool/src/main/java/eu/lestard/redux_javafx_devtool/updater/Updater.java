package eu.lestard.redux_javafx_devtool.updater;

import eu.lestard.redux_javafx_devtool.actions.ClientActionDispatchedAction;
import eu.lestard.redux_javafx_devtool.actions.ClientActionSelectedAction;
import eu.lestard.redux_javafx_devtool.actions.TimeTravelToClientAction;
import eu.lestard.redux_javafx_devtool.actions.TimeTravelToNextActionAction;
import eu.lestard.redux_javafx_devtool.actions.TimeTravelToPreviousActionAction;
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

			Case($(instanceOf(TimeTravelToClientAction.class)),
				timeTravelToClientAction -> {
					// the action that we jump to
					final ClientAction targetAction = timeTravelToClientAction.getClientAction();

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
						.withStateHistory(historyWithDeactivatedActions);
				}
			),

			Case($(instanceOf(TimeTravelToNextActionAction.class)),
				timeTravelToNextActionAction -> {
					final Option<StateHistoryEntry> currentHistoryEntryOption = state.getStateHistory()
						.filter(entry -> entry.getAction().isActive())
						.lastOption();

					return currentHistoryEntryOption.map(currentHistoryEntry -> {
						final int indexOfCurrent = state.getStateHistory().indexOf(currentHistoryEntry);


						if((state.getStateHistory().size() -1) == indexOfCurrent) {
							// if the last entry is already active, there is no 'next' action.
							return state;
						}

						final StateHistoryEntry nextHistoryEntry = state.getStateHistory().get(indexOfCurrent + 1);

						final Seq<StateHistoryEntry> newHistory = state.getStateHistory()
							.replace(nextHistoryEntry,
								nextHistoryEntry.withAction(nextHistoryEntry.getAction().withActiveFlag(true)));
						return state.withStateHistory(newHistory);
					}).getOrElse(state);
				}
			),


			Case($(instanceOf(TimeTravelToPreviousActionAction.class)),
				timeTravelToPreviousActionAction -> {
					final Option<StateHistoryEntry> currentHistoryEntryOption = state.getStateHistory()
						.filter(entry -> entry.getAction().isActive())
						.lastOption();

					return currentHistoryEntryOption.map(currentHistoryEntry -> {
						final int indexOfCurrent = state.getStateHistory().indexOf(currentHistoryEntry);

						if(indexOfCurrent < 1) {
							// if the current entry is the first we can't move to previous
							return state;
						}

						final Seq<StateHistoryEntry> newHistory = state.getStateHistory()
							.replace(currentHistoryEntry,
								currentHistoryEntry.withAction(currentHistoryEntry.getAction().withActiveFlag(false)));
						return state.withStateHistory(newHistory);
					}).getOrElse(state);
				}
			),


			Case($(), state)
		);
	}
}
