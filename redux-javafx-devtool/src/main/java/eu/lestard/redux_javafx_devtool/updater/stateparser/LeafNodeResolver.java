package eu.lestard.redux_javafx_devtool.updater.stateparser;

public interface LeafNodeResolver<T> {

	Class<T> getType();

	default boolean check(Object o) {
		return getType().isAssignableFrom(o.getClass());
	}

	default String toString(T value) {
		return value.toString();
	}
}
