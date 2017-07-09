package eu.lestard.redux_javafx_devtool.view.actionsview;

import com.netopyr.reduxfx.vscenegraph.builders.RegionBuilder;
import eu.lestard.redux_javafx_devtool.state.AppState;
import eu.lestard.redux_javafx_devtool.state.ClientAction;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javaslang.collection.Array;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.ListView;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;

public class ActionsHistoryView {

	private ActionsHistoryView() {
	}

	public static RegionBuilder view(AppState state) {
		return VBox()
			.prefHeight(Region.USE_COMPUTED_SIZE)
			.prefWidth(Region.USE_COMPUTED_SIZE)
			.children(
				ListView(ClientAction.class)
					.vgrow(Priority.ALWAYS)
					.prefHeight(Region.USE_COMPUTED_SIZE)
					.prefWidth(Region.USE_COMPUTED_SIZE)
					.items(Array.ofAll(state.getClientActions()))
					.cellFactory(ActionsHistoryItem::view)
			);
	}
}
