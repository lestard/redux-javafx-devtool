package eu.lestard.redux_javafx_devtool;

import com.glung.redux.Store;
import com.netopyr.reduxfx.store.ReduxFXStore;
import com.netopyr.reduxfx.updater.Update;
import com.netopyr.reduxfx.vscenegraph.ReduxFXView;
import eu.lestard.redux_javafx_devtool.actions.Actions;
import eu.lestard.redux_javafx_devtool.state.AppState;
import eu.lestard.redux_javafx_devtool.updater.Updater;
import eu.lestard.redux_javafx_devtool.view.DevToolWindow;
import io.reactivex.processors.PublishProcessor;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.reactivestreams.Subscriber;
import redux.api.Reducer;

public class ReduxFXDevTool<STATE> {

	private ReduxFXStore<AppState> devToolStore;

	private final PublishProcessor<Object> devToolActions = PublishProcessor.create();

	public static <STATE> ReduxFXDevTool<STATE> create() {
		return new ReduxFXDevTool<>();
	}

	private ReduxFXDevTool() {
		devToolStore = new ReduxFXStore<>(AppState.create(),
			((appState, action) -> Update.of(Updater.update(appState, action))));

		final Subscriber<Object> actionSubscriber = devToolStore.createActionSubscriber();
		devToolActions.subscribe(actionSubscriber);
	}

	public void openDevToolWindow(Stage primaryStage) {
		Stage devToolStage = new Stage();
		devToolStage.initOwner(primaryStage);
		devToolStage.initModality(Modality.NONE);

		final ReduxFXView<AppState> devToolView = ReduxFXView.createStage(DevToolWindow::view, devToolStage);

		devToolView.connect(devToolStore.getStatePublisher(), devToolStore.createActionSubscriber());
	}

	public void instrumentReduxfxStore(ReduxFXStore<STATE> clientStore) {

//		final Publisher<STATE> clientStatePublisher = clientStore.getStatePublisher();
//
//		Flowable.fromPublisher(clientStatePublisher)
//			.subscribe(state -> devToolActions.offer(Actions.clientStateUpdated(state)));
	}

	public Store.Enhancer instrumentJvmRedux() {
		return next -> new redux.api.Store.Creator() {
			@Override
			public <S> redux.api.Store<S> create(Reducer<S> reducer, S state) {
				final redux.api.Store<S> store = next.create(reducer, state);

				return new redux.api.Store<S>() {
					@Override
					public S getState() {
						return store.getState();
					}

					@Override
					public Subscription subscribe(Subscriber subscriber) {
						return store.subscribe(subscriber);
					}

					@Override
					public void replaceReducer(Reducer<S> reducer) {
						store.replaceReducer(reducer);
					}

					@Override
					public Object dispatch(Object action) {
						final Object result = store.dispatch(action);
						final S newState = store.getState();
						devToolActions.offer(Actions.clientActionDispatched(action, newState));
						return result;
					}
				};
			}
		};
	}
}
