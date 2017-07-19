package eu.lestard.redux_javafx_devtool;

import com.glung.redux.Store;
import com.netopyr.reduxfx.store.ReduxFXStore;
import com.netopyr.reduxfx.updater.Update;
import com.netopyr.reduxfx.vscenegraph.ReduxFXView;
import eu.lestard.redux_javafx_devtool.actions.Actions;
import eu.lestard.redux_javafx_devtool.state.AppState;
import eu.lestard.redux_javafx_devtool.state.Selectors;
import eu.lestard.redux_javafx_devtool.updater.Updater;
import eu.lestard.redux_javafx_devtool.view.DevToolWindow;
import io.reactivex.processors.PublishProcessor;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import redux.api.Reducer;

import java.util.ArrayList;
import java.util.List;

public class ReduxFXDevTool<STATE> {

	private ReduxFXStore<AppState> devToolStore;

	private final PublishProcessor<Object> devToolActions = PublishProcessor.create();

	public static <STATE> ReduxFXDevTool<STATE> create() {
		return new ReduxFXDevTool<>();
	}

	private List<redux.api.Store.Subscriber> subscribers = new ArrayList<>();

	private Object lastState;

	private ReduxFXDevTool() {
		devToolStore = new ReduxFXStore<>(AppState.create(),
			((appState, action) -> Update.of(Updater.update(appState, action))));

		final Subscriber<Object> actionSubscriber = devToolStore.createActionSubscriber();
		devToolActions.subscribe(actionSubscriber);

		devToolStore.getStatePublisher().subscribe(new Subscriber<AppState>() {
			@Override
			public void onSubscribe(Subscription s) {
				s.request(Long.MAX_VALUE);
			}

			@Override
			public void onNext(AppState appState) {
				lastState = Selectors.getClientStateObject(appState);
				subscribers.forEach(redux.api.Store.Subscriber::onStateChanged);
			}

			@Override
			public void onError(Throwable t) {
			}

			@Override
			public void onComplete() {
			}
		});
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

				lastState = state;

				return new redux.api.Store<S>() {
					@Override
					public S getState() {
						return (S)lastState;
					}

					@Override
					public Subscription subscribe(Subscriber subscriber) {
						subscribers.add(subscriber);
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
