package eu.lestard.redux_javafx_devtool.state.selectors;

import eu.lestard.redux_javafx_devtool.state.AppState;
import eu.lestard.redux_javafx_devtool.state.ClientAction;
import eu.lestard.redux_javafx_devtool.state.StateHistoryEntry;
import io.vavr.collection.Seq;
import io.vavr.control.Option;

public class TimeTravelPlayerSelectors {


	public static boolean isNextActionButtonEnabled(AppState state) {

		final Seq<StateHistoryEntry> stateHistory = state.getStateHistory();

		if(stateHistory.isEmpty()) {
			return false;
		}

		if(stateHistory.size() == 1) {
			return false;
		}

		final Option<ClientAction> currentActiveAction = getCurrentActiveAction(state);

		return currentActiveAction.map(action -> {
			final ClientAction lastAction = stateHistory.last().getAction();

			return !lastAction.equals(action);
		}).getOrElse(false);
	}

	public static boolean isPreviousActionButtonEnabled(AppState state) {
		final Seq<StateHistoryEntry> stateHistory = state.getStateHistory();

		if(stateHistory.isEmpty()) {
			return false;
		}

		if(stateHistory.size() == 1) {
			return false;
		}

		final Option<ClientAction> currentActiveAction = getCurrentActiveAction(state);

		return currentActiveAction.map(action -> {
			final ClientAction firstAction = stateHistory.get(0).getAction();

			return !firstAction.equals(action);
		}).getOrElse(false);
	}

	/**
	 * Returns the action that is currently active in time-traveler.
	 */
	public static Option<ClientAction> getCurrentActiveAction(AppState state) {
		return state.getStateHistory()
			.filter(entry -> entry.getAction().isActive())
			.lastOption()
			.map(StateHistoryEntry::getAction);
	}

	public static int getNumberOfActions(AppState state) {
		return state.getStateHistory().size();
	}

	public static int getIndexOfCurrentActiveAction(AppState state) {
		final Option<StateHistoryEntry> currentHistoryEntry = state.getStateHistory()
			.filter(entry -> entry.getAction().isActive())
			.lastOption();

		return currentHistoryEntry
			.map(entry -> state.getStateHistory().indexOf(entry))
			.getOrElse(0);
	}

}
