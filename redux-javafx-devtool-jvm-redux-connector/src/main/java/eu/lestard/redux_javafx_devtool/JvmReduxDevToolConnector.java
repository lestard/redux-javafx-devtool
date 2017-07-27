package eu.lestard.redux_javafx_devtool;

import redux.api.Reducer;
import redux.api.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class JvmReduxDevToolConnector<STATE> implements Connector<STATE>, Store.Enhancer {

	private BiConsumer<Object, STATE> actionPublisher;

	private List<Store.Subscriber> subscribers = new ArrayList<>();
	private STATE lastState;

	@Override
	public void pushState(STATE newState) {
		lastState = newState;

		subscribers.forEach(Store.Subscriber::onStateChanged);
	}

	@Override
	public void initActionPublisher(BiConsumer<Object, STATE> actionPublisher) {
		this.actionPublisher = actionPublisher;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Store.Creator<STATE> enhance(Store.Creator next) {
		return (reducer, initialState) -> {
			final Store<STATE> store = next.create(reducer, initialState);

			lastState = initialState;

			return new Store<STATE>() {

				@SuppressWarnings("unchecked")
				@Override
				public Object dispatch(Object action) {
					final Object result = store.dispatch(action);
					final STATE newState = store.getState();

					actionPublisher.accept(action, newState);

					return result;
				}

				@SuppressWarnings("unchecked")
				@Override
				public STATE getState() {
					return (STATE) lastState;
				}

				@Override
				public Subscription subscribe(Subscriber subscriber) {
					subscribers.add(subscriber);

					final Subscription storeSubscription = store.subscribe(subscriber);

					return () -> {
						subscribers.remove(subscriber);

						storeSubscription.unsubscribe();
					};
				}

				@Override
				public void replaceReducer(Reducer<STATE> reducer) {
					store.replaceReducer(reducer);
				}
			};
		};
	}
}
