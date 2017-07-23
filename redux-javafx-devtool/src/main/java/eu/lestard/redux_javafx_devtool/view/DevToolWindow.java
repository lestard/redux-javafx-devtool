package eu.lestard.redux_javafx_devtool.view;

import com.netopyr.reduxfx.vscenegraph.VNode;
import eu.lestard.redux_javafx_devtool.state.AppState;
import eu.lestard.redux_javafx_devtool.view.actionsview.ActionsHistoryView;
import eu.lestard.redux_javafx_devtool.view.detailsview.DetailsView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.HBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Scene;
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
								HBox()
									.vgrow(Priority.ALWAYS)
									.prefHeight(Region.USE_COMPUTED_SIZE)
									.prefWidth(Region.USE_COMPUTED_SIZE)
									.children(
										ActionsHistoryView.view(state)
											.hgrow(Priority.ALWAYS),
										DetailsView.view(state)
											.hgrow(Priority.ALWAYS)
									)
							)
					)
			);
	}
}
