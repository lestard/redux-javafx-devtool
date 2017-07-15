package eu.lestard.redux_javafx_devtool.state;

import io.vavr.Tuple2;
import io.vavr.collection.LinkedHashMap;
import io.vavr.collection.Seq;
import io.vavr.control.Option;

public class Selectors {

	public static Option<StateNode> getClientState(AppState appState) {
		final Option<ClientAction> selectedAction = appState.getSelectedAction();
		final LinkedHashMap<ClientAction, StateNode> history = appState.getStateHistory();

		return selectedAction
			.flatMap(history::get)
			.orElse(
				history.lastOption()
					.map(Tuple2::_2)
			);
	}

	public static Seq<ClientAction> getClientActions(AppState state) {
		return state.getStateHistory()
			.keySet().toList();
	}

}
