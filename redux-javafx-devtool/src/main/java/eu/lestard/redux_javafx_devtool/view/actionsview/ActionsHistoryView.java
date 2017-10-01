package eu.lestard.redux_javafx_devtool.view.actionsview;

import com.netopyr.reduxfx.vscenegraph.builders.RegionBuilder;
import eu.lestard.redux_javafx_devtool.actions.Actions;
import eu.lestard.redux_javafx_devtool.state.AppState;
import eu.lestard.redux_javafx_devtool.state.ClientAction;
import eu.lestard.redux_javafx_devtool.state.selectors.Selectors;
import io.vavr.collection.Seq;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.ListView;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;

public class ActionsHistoryView {

	private ActionsHistoryView() {
	}

	public static RegionBuilder view(AppState state) {

		final Seq<ClientAction> clientActions = Selectors.getClientActions(state);

		return VBox()
			.prefHeight(Region.USE_COMPUTED_SIZE)
			.prefWidth(Region.USE_COMPUTED_SIZE)
			.children(
				ListView(ClientAction.class)
					.vgrow(Priority.ALWAYS)
					.prefHeight(Region.USE_COMPUTED_SIZE)
					.prefWidth(Region.USE_COMPUTED_SIZE)
					.items(clientActions)
					.selectedItem(state.getSelectedAction().isDefined() ? state.getSelectedAction().get() : null,
						(oldSelection, newSelection) -> Actions.selectClientAction(newSelection))
					.cellFactory(ActionsHistoryItem::view),
				ActionsControlsView.view(state)
			);
	}
}
