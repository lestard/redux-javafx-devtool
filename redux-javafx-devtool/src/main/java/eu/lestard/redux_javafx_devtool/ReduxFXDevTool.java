package eu.lestard.redux_javafx_devtool;

import com.netopyr.reduxfx.store.ReduxFXStore;
import com.netopyr.reduxfx.updater.Update;
import com.netopyr.reduxfx.vscenegraph.ReduxFXView;
import eu.lestard.redux_javafx_devtool.actions.Actions;
import eu.lestard.redux_javafx_devtool.state.AppState;
import eu.lestard.redux_javafx_devtool.state.selectors.Selectors;
import eu.lestard.redux_javafx_devtool.updater.Updater;
import eu.lestard.redux_javafx_devtool.view.DevToolWindow;
import io.reactivex.processors.PublishProcessor;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

public class ReduxFXDevTool<STATE> {

	private ReduxFXStore<AppState> devToolStore;

	private final PublishProcessor<Object> devToolActions = PublishProcessor.create();

	public static <STATE> ReduxFXDevTool<STATE> create() {
		return new ReduxFXDevTool<>();
	}

	private List<Connector<STATE>> connectors = new ArrayList<>();

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

			@SuppressWarnings("unchecked")
			@Override
			public void onNext(AppState appState) {
				Object lastState = Selectors.getClientStateObject(appState);

				connectors.forEach(connector ->
					connector.pushState((STATE) lastState));
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

	public Runnable connect(Connector<STATE> connector) {
		this.connectors.add(connector);
		connector.initActionPublisher(
			(action, state) -> devToolActions.offer(Actions.clientActionDispatched(action, state)));

		return () -> this.connectors.remove(connector);
	}
}
