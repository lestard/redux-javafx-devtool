package eu.lestard.redux_javafx_devtool.updater.stateparser;


import eu.lestard.redux_javafx_devtool.state.StateNode;
import io.vavr.Tuple;
import io.vavr.collection.Array;
import io.vavr.collection.Seq;
import io.vavr.control.Option;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StateParser {
	private static final Array<String> GETTER_PREFIXES = Array.of("get", "is");

	private final Seq<LeafNodeResolver> leafNodeResolvers;

	private final boolean ignoreNull;

	private StateParser(Seq<LeafNodeResolver> customTypeResolvers, boolean ignoreNull) {
		this.ignoreNull = ignoreNull;

		final Array<LeafNodeResolver> defaultResolvers = Array.<LeafNodeResolver>of(
			() -> Integer.class,
			() -> Double.class,
			() -> Float.class,
			() -> Long.class,
			new LeafNodeResolver() {
				@Override
				public Class getType() {
					return String.class;
				}
				@Override
				public String toString(Object value) {
					return "\"" + value + "\"";
				}
			},
			() -> Boolean.class,
			new LeafNodeResolver<LocalDate>() {
				@Override
				public Class<LocalDate> getType() {
					return LocalDate.class;
				}

				@Override
				public String toString(LocalDate value) {
					return value.format(DateTimeFormatter.ISO_DATE);
				}
			},
			() -> Enum.class
		);

		final Seq<Class> customTypes = customTypeResolvers.map(LeafNodeResolver::getType);

		this.leafNodeResolvers = defaultResolvers.filter(
			leafNodeResolver -> ! customTypes.contains(leafNodeResolver.getType()))
			.appendAll(customTypeResolvers);
	}

	public static StateParser create() {
		return new StateParser(Array.empty(), false);
	}

	public StateParser create(Seq<LeafNodeResolver> typeResolvers) {
		return new StateParser(typeResolvers, false);
	}

	public StateParser withIgnoreNull(boolean ignoreNull) {
		return new StateParser(this.leafNodeResolvers, ignoreNull);
	}


	public StateNode parse(String nodeName, Object clientState) {
		if(clientState == null) {
			return StateNode.create(nodeName, null).withValueStringRepresentation(nodeName + ": null");
		}

		final Option<LeafNodeResolver> leafNodeResolver = this.leafNodeResolvers
			.find(resolver -> resolver.check(clientState));

		if(leafNodeResolver.isDefined()) {
			return createNodeForLeaf(nodeName, clientState, leafNodeResolver);
		} else {
			if(clientState instanceof Iterable) {
				return createNodeForIterable(nodeName, (Iterable)clientState);
			} else {
				return createNodeForObject(nodeName, clientState);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private StateNode createNodeForLeaf(String nodeName, Object clientState, Option<LeafNodeResolver> leafNodeResolver) {
		return StateNode
			.create(nodeName, clientState)
			.withValueStringRepresentation(nodeName + ": " + leafNodeResolver.get().toString(clientState));
	}

	private StateNode createNodeForObject(String nodeName, Object clientState) {
		final Array<Method> temp = Array.of(clientState.getClass().getMethods())
			.filter(method -> !GETTER_PREFIXES.filter(prefix -> method.getName().startsWith(prefix)).isEmpty())
			.filter(method -> !method.getName().equals("getClass"));

		final Array<StateNode> childNodes = temp
			.map(method -> {
				final String fieldName = GETTER_PREFIXES
					.map(prefix -> {
						if (method.getName().startsWith(prefix)) {
							return getFieldNameFromGetter(method, prefix);
						} else {
							return "";
						}
					}).filter(name -> !name.isEmpty())
					.last();

				try {
					final Object fieldValue = method.invoke(clientState);
					return Tuple.of(fieldName, fieldValue);
				} catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
					return Tuple.of(fieldName, "Error: Cannot access field");
				}
			})
			.map(tuple -> parse(tuple._1(), tuple._2()));

		return StateNode.create(nodeName, clientState)
			.withValueStringRepresentation(nodeName)
			.withChildren(childNodes);
	}

	private StateNode createNodeForIterable(String nodeName, Iterable clientStateIterable) {
		List<StateNode> itemNodesList = new ArrayList<>();

		final Iterator iterator = clientStateIterable.iterator();

		int i = 0;
		while(iterator.hasNext()) {
			Object item = iterator.next();

			final StateNode stateNode = parse(Integer.toString(i), item);
			itemNodesList.add(stateNode);
			i++;
		}

		return StateNode.create(nodeName, clientStateIterable)
			.withValueStringRepresentation(nodeName + "(size=" + Array.ofAll(clientStateIterable).size() + ")")
			.withChildren(Array.ofAll(itemNodesList));
	}

	private static String getFieldNameFromGetter(Method method, String prefix) {
		final String methodName = method.getName();
		if(methodName.length() > 0) {
			final String getterNameWithoutPrefix = methodName.substring(prefix.length());
			final String firstLetter = getterNameWithoutPrefix.substring(0, 1).toLowerCase();
			return firstLetter + getterNameWithoutPrefix.substring(1);
		} else {
			return "";
		}
	}


}
