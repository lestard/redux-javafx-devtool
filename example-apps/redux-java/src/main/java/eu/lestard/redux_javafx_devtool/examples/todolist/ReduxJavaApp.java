package eu.lestard.redux_javafx_devtool.examples.todolist;

import com.netopyr.reduxfx.vscenegraph.ReduxFXView;
import eu.lestard.redux_javafx_devtool.examples.todolist.actions.Actions;
import eu.lestard.redux_javafx_devtool.examples.todolist.store.AppState;
import eu.lestard.redux_javafx_devtool.examples.todolist.updater.Reducer;
import eu.lestard.redux_javafx_devtool.examples.todolist.view.MainView;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;
import javafx.application.Application;
import javafx.stage.Stage;
import redux.api.Store;

public class ReduxJavaApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		final AppState initialState = AppState.create();

		final Store<AppState> store = com.glung.redux.Store.createStore(Reducer::reduce, initialState, null);

		final Flowable<AppState> statePublisher = Flowable.create(
			emitter -> store.subscribe(() -> emitter.onNext(store.getState())),
			BackpressureStrategy.BUFFER
		);

		final PublishProcessor<Object> actionSubscriber = PublishProcessor.create();
		actionSubscriber.subscribe(store::dispatch);

		final ReduxFXView<AppState> view = ReduxFXView.createStage(MainView::view, primaryStage);

		view.connect(statePublisher, actionSubscriber);

		store.dispatch(Actions.initAction());


	}
}
