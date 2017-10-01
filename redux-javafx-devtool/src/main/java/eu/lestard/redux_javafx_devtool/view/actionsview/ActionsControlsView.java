package eu.lestard.redux_javafx_devtool.view.actionsview;

import com.netopyr.reduxfx.vscenegraph.builders.RegionBuilder;
import eu.lestard.redux_javafx_devtool.actions.Actions;
import eu.lestard.redux_javafx_devtool.state.AppState;
import javafx.scene.layout.Region;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.CheckBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.HBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;

public class ActionsControlsView {

	private ActionsControlsView() {
	}

	public static RegionBuilder view(AppState state) {

		return VBox()
			.prefHeight(Region.USE_COMPUTED_SIZE)
			.prefWidth(Region.USE_COMPUTED_SIZE)
			.padding(10)
			.children(
				HBox()
				.children(
					CheckBox()
						.text("Ignore new Actions")
					.selected(state.isIgnoreNewActions(), (oldValue, newValue) -> Actions.switchIgnoreNewActions())
				)
			);
	}

}
