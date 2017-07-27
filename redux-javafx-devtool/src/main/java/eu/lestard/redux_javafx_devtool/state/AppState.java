package eu.lestard.redux_javafx_devtool.state;

import eu.lestard.redux_javafx_devtool.updater.stateparser.StateParser;
import io.vavr.collection.Array;
import io.vavr.collection.Seq;
import io.vavr.control.Option;

public class AppState {

	/**
	 * A flag indicating whether the playback of the time-travel slider
	 * is running at the moment.
	 */
	private final boolean playbackRunning;

	/**
	 * A list of action-state pairs in the history of the clients application.
	 * The last entry in this list which has the {@link ClientAction#isActive()} flag
	 * set to <code>true</code> is considered as the "current" point in time in the time-traveler.
	 */
	private final Seq<StateHistoryEntry> stateHistory;

	/**
	 * Selected action represents the action that the user has selected in the action-list.
	 * This is used to view the action in a details view.
	 * Notice: This has nothing to do with time-traveling.
	 * It is <strong>not</strong> the current action in the time-traveler.
	 */
	private final Option<ClientAction> selectedAction;

	private final StateParser stateParser = StateParser.create();

	public static AppState create() {
		return new AppState(Array.empty(), Option.none(), false);
	}

	private AppState(Seq<StateHistoryEntry> stateHistory,
		Option<ClientAction> selectedAction, boolean playbackRunning) {
		this.stateHistory = stateHistory;
		this.selectedAction = selectedAction;
		this.playbackRunning = playbackRunning;
	}

	public AppState withNewAction(ClientAction clientAction, Object clientState) {
		final StateHistoryEntry newHistoryEntry = StateHistoryEntry.create(clientAction, stateParser.parse("root", clientState));
		return new AppState(this.stateHistory.append(newHistoryEntry),
			selectedAction, playbackRunning);
	}

	public AppState withSelectedAction(ClientAction action) {
		return new AppState(this.stateHistory, Option.of(action), playbackRunning);
	}

	public Seq<StateHistoryEntry> getStateHistory() {
		return this.stateHistory;
	}

	public AppState withStateHistory(Seq<StateHistoryEntry> history) {
		return new AppState(history, this.selectedAction, playbackRunning);
	}

	public Option<ClientAction> getSelectedAction() {
		return selectedAction;
	}

	public AppState withPlaybackRunning(boolean playback) {
		return new AppState(stateHistory, selectedAction, playback);
	}

	public boolean isPlaybackRunning() {
		return playbackRunning;
	}
}
