package eu.lestard.redux_javafx_devtool.examples.todolist.view;

import com.netopyr.reduxfx.vscenegraph.VNode;
import eu.lestard.redux_javafx_devtool.examples.todolist.actions.Actions;
import eu.lestard.redux_javafx_devtool.examples.todolist.store.AppState;
import eu.lestard.redux_javafx_devtool.examples.todolist.store.Item;
import eu.lestard.redux_javafx_devtool.examples.todolist.store.Selectors;

import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Button;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.HBox;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Label;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.ListView;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Scene;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.Stage;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.TextField;
import static com.netopyr.reduxfx.vscenegraph.VScenegraphFactory.VBox;

public class MainView {

	public static VNode view(AppState appState) {
		return Stage()
			.title("Redux-Java Example")
			.showing(true)
			.scene(
				Scene()
					.root(
						VBox()
							.padding(10)
							.spacing(10)
							.children(
								Label().text("List App")
									.style("-fx-font-size:30"),
								HBox()
									.spacing(5)
									.children(
										TextField()
											.text(appState.getTextFieldValue(),
												(oldValue, newValue) -> Actions.updateTextField(newValue)),
										Button()
											.text("Add")
											.disable(!Selectors.isAddButtonEnabled(appState))
											.onAction(event -> Actions.addItem()),
										Button()
											.text("Remove")
											.disable(!Selectors.isRemoveButtonEnabled(appState))
											.onAction(event -> Actions.removeSelectedItem())
									),
								ListView(Item.class)
									.cellFactory(item ->
										Label().text(item.getText())
									)
									.items(appState.getItems())
							)
					)
			);

	}
}
