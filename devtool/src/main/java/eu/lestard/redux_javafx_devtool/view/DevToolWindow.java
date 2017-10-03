package eu.lestard.redux_javafx_devtool.view;

import com.netopyr.reduxfx.vscenegraph.VNode;
import eu.lestard.redux_javafx_devtool.state.AppState;
import eu.lestard.redux_javafx_devtool.view.actionsview.ActionsHistoryView;
import eu.lestard.redux_javafx_devtool.view.detailsview.DetailsView;
import eu.lestard.redux_javafx_devtool.view.time_travel_player.TimeTravelPlayerView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Scene;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.SplitPane;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stage;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;

public class DevToolWindow {

	public static VNode view(AppState state) {

		return Stage()
			.title("ReduxFX Dev Tool")
			.showing(true)
			.scene(
				Scene()
					.root(
						VBox()
							.prefWidth(1000)
							.prefHeight(800)
							.children(
								SplitPane()
									.vgrow(Priority.ALWAYS)
									.prefHeight(Region.USE_COMPUTED_SIZE)
									.prefWidth(Region.USE_COMPUTED_SIZE)
									.dividerPositions(0.5)
									.items(
										ActionsHistoryView.view(state),
										DetailsView.view(state)
									),
								TimeTravelPlayerView.view(state)
							)
					)
			);
	}
}
