package eu.lestard.redux_javafx_devtool.examples.todolist;

import com.netopyr.reduxfx.store.ReduxFXStore;
import com.netopyr.reduxfx.updater.Update;
import com.netopyr.reduxfx.vscenegraph.ReduxFXView;
import eu.lestard.redux_javafx_devtool.ReduxFXDevTool;
import eu.lestard.redux_javafx_devtool.examples.todolist.store.AppState;
import eu.lestard.redux_javafx_devtool.examples.todolist.updater.Reducer;
import eu.lestard.redux_javafx_devtool.examples.todolist.view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

public class ReduxFXApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		final AppState initialState = AppState.create();

		final ReduxFXDevTool<AppState> devTool = ReduxFXDevTool.create();

		final ReduxFXStore<AppState> store = new ReduxFXStore<>(initialState,
			(appState, action) -> Update.of(Reducer.reduce(appState, action)));

		devTool.instrumentReduxfxStore(store);

		final ReduxFXView<AppState> view = ReduxFXView.createStage(MainView::view, primaryStage);
		view.connect(store.getStatePublisher(), store.createActionSubscriber());

		devTool.openDevToolWindow(primaryStage);

	}
}
