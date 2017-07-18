package eu.lestard.redux_javafx_devtool.view.stateview;

import com.netopyr.reduxfx.vscenegraph.builders.RegionBuilder;
import com.netopyr.reduxfx.vscenegraph.builders.TreeItemBuilder;
import eu.lestard.redux_javafx_devtool.state.AppState;
import eu.lestard.redux_javafx_devtool.state.Selectors;
import eu.lestard.redux_javafx_devtool.state.StateNode;
import io.vavr.collection.Array;
import io.vavr.control.Option;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Label;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.TreeItem;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.TreeView;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;

public class StateView {

	private StateView() {
	}

	public static RegionBuilder view(AppState state) {

		final Option<StateNode> clientStateOptional = Selectors.getClientState(state);

		return VBox()
			.spacing(2)
			.prefHeight(Region.USE_COMPUTED_SIZE)
			.prefWidth(Region.USE_COMPUTED_SIZE)
			.children(

				clientStateOptional.isEmpty()
					? Label().text("no State")
					: TreeView(StateNode.class)
					.vgrow(Priority.ALWAYS)
					.prefHeight(Region.USE_COMPUTED_SIZE)
					.prefWidth(Region.USE_COMPUTED_SIZE)
					.cellFactory(stateNode -> Label().text(stateNode.getValueStringRep()))
					.showRoot(true)
					.root(
						createTreeItem(clientStateOptional.get())
					)
			);
	}

	private static TreeItemBuilder<?, StateNode> createTreeItem(StateNode node) {
		return TreeItem(StateNode.class)
			.expanded(true)
			.value(node)
			.children(Array.ofAll(node.getChildren())
				.map(StateView::createTreeItem));
	}
}
