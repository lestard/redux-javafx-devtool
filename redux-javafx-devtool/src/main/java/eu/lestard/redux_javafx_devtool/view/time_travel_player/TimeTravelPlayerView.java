package eu.lestard.redux_javafx_devtool.view.time_travel_player;

import com.netopyr.reduxfx.vscenegraph.builders.RegionBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import eu.lestard.redux_javafx_devtool.actions.Actions;
import eu.lestard.redux_javafx_devtool.state.AppState;
import eu.lestard.redux_javafx_devtool.state.selectors.TimeTravelPlayerSelectors;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;

import static com.netopyr.reduxfx.fontawesomefx.FontAwesomeFX.FontAwesomeIconView;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Button;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.HBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Slider;

public class TimeTravelPlayerView {
	private TimeTravelPlayerView() {
	}

	public static RegionBuilder view(AppState state) {
		final int max = Math.max(1, TimeTravelPlayerSelectors.getNumberOfActions(state) -1);
		final int value = TimeTravelPlayerSelectors.getNumberOfActions(state) < 2 ? 1 : TimeTravelPlayerSelectors.getIndexOfCurrentActiveAction(state);

		return HBox()
			.margin(10)
			.spacing(5)
			.alignment(Pos.CENTER_LEFT)
			.children(
				Button()
					.mnemonicParsing(false)
					.graphic(
						FontAwesomeIconView()
							.icon(state.isPlaybackRunning() ? FontAwesomeIcon.STOP : FontAwesomeIcon.PLAY)
					),
				Slider()
					.disable(true)
					.hgrow(Priority.ALWAYS)
					.max(max)
					.value(value),
				Button()
					.mnemonicParsing(false)
					.disable(!TimeTravelPlayerSelectors.isPreviousActionButtonEnabled(state))
					.graphic(
						FontAwesomeIconView()
							.icon(FontAwesomeIcon.CHEVRON_LEFT)
					)
					.onAction(event -> Actions.timeTravelToPreviousAction()),
				Button()
					.mnemonicParsing(false)
					.disable(!TimeTravelPlayerSelectors.isNextActionButtonEnabled(state))
					.graphic(
						FontAwesomeIconView()
							.icon(FontAwesomeIcon.CHEVRON_RIGHT)
					)
					.onAction(event -> Actions.timeTravelToNextAction())
			);
	}
}
