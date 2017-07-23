package eu.lestard.redux_javafx_devtool.view.detailsview;

import com.netopyr.reduxfx.vscenegraph.builders.RegionBuilder;
import eu.lestard.redux_javafx_devtool.state.AppState;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Tab;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.TabPane;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;

public class DetailsView {

	private DetailsView() {
	}

	public static RegionBuilder view(AppState state) {
		return VBox()
			.prefHeight(Region.USE_COMPUTED_SIZE)
			.prefWidth(Region.USE_COMPUTED_SIZE)
			.children(
				TabPane()
					.vgrow(Priority.ALWAYS)
					.selectedIndex(1)
					.tabs(
						Tab()
							.text("Action")
							.content(ActionDetailsView.view(state)),
						Tab()
							.text("State")
							.content(StateView.view(state))
					)
			);
	}

}
