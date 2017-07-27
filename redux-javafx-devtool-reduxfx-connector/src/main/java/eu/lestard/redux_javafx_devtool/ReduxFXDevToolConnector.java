package eu.lestard.redux_javafx_devtool;

import com.netopyr.reduxfx.store.ReduxFXStore;

import java.util.function.BiConsumer;

public class ReduxFXDevToolConnector<STATE> implements Connector<STATE> {

	private ReduxFXStore<STATE> clientStore;

	public ReduxFXDevToolConnector(ReduxFXStore<STATE> clientStore) {
		this.clientStore = clientStore;
	}

	@Override
	public void pushState(STATE newState) {
		// TODO
	}

	@Override
	public void initActionPublisher(BiConsumer<Object, STATE> actionPublisher) {
		// TODO
	}
}
