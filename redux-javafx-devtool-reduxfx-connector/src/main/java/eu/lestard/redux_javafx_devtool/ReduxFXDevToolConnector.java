package eu.lestard.redux_javafx_devtool;

import com.netopyr.reduxfx.middleware.Middleware;
import com.netopyr.reduxfx.updater.Update;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class ReduxFXDevToolConnector<STATE> implements Connector<STATE>, Middleware<STATE> {

	private final Publisher<STATE> statePublisher;
	private final List<Consumer<STATE>> callbacks = new CopyOnWriteArrayList<>();
    private BiConsumer<Object, STATE> actionPublisher;


    public ReduxFXDevToolConnector() {
		this.statePublisher = Flowable.create(emitter -> callbacks.add(emitter::onNext), BackpressureStrategy.MISSING);
	}

	@Override
	public void pushState(STATE newState) {
        callbacks.forEach(callback -> callback.accept(newState));
	}

	@Override
	public void initActionPublisher(BiConsumer<Object, STATE> actionPublisher) {
        this.actionPublisher = actionPublisher;
    }

    @Override
    public BiFunction<STATE, Object, Update<STATE>> apply(BiFunction<STATE, Object, Update<STATE>> next) {
        return (oldState, action) -> {
            final Update<STATE> newState = next.apply(oldState, action);
            actionPublisher.accept(action, newState.getState());
            return newState;
        };
    }

	public Publisher<STATE> getStatePublisher() {
		return statePublisher;
	}

}
