package eu.lestard.redux_javafx_devtool.view.detailsview;

import com.netopyr.reduxfx.vscenegraph.VNode;
import com.netopyr.reduxfx.vscenegraph.builders.RegionBuilder;
import eu.lestard.redux_javafx_devtool.state.AppState;
import eu.lestard.redux_javafx_devtool.state.ClientAction;
import eu.lestard.redux_javafx_devtool.state.selectors.Selectors;
import io.vavr.control.Option;
import javafx.scene.layout.Region;

import java.time.format.DateTimeFormatter;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Label;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;

public class ActionDetailsView {

	private ActionDetailsView() {
	}

	public static RegionBuilder view(AppState state) {
		final Option<ClientAction> selectedAction = Selectors.getSelectedAction(state);


		return VBox()
			.prefHeight(Region.USE_COMPUTED_SIZE)
			.prefWidth(Region.USE_COMPUTED_SIZE)
			.children(
				selectedAction.<VNode>map(action ->
					VBox()
						.children(
							Label()
								.style("-fx-font-size: 1.5em")
								.text("Type:" + action.getAction().getClass().getSimpleName()),
							Label()
								.text("Time:" + action.getDispatchTime().format(DateTimeFormatter.ISO_TIME)),
							Label()
								.text("Action:\n" + action.getAction())
						)
				).getOrElse(Label().text(""))
			);
	}
}
