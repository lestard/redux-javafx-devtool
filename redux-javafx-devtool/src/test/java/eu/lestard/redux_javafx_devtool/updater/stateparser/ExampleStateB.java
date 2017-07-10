package eu.lestard.redux_javafx_devtool.updater.stateparser;

import javaslang.collection.Array;
import javaslang.collection.Seq;

import java.util.Collections;
import java.util.List;

public class ExampleStateB {

	private final String string;

	private final Seq<String> immutableStringList;

	private final List<String> javaStringList;

	private final Seq<ExampleStateC> itemList;

	private ExampleStateB(String string, Seq<String> immutableStringList, List<String> javaStringList,
		Seq<ExampleStateC> itemList) {
		this.string = string;
		this.immutableStringList = immutableStringList;
		this.javaStringList = javaStringList;
		this.itemList = itemList;
	}

	public static ExampleStateB create() {
		return new ExampleStateB("", Array.empty(), Collections.emptyList(), Array.empty());
	}

	public String getString() {
		return string;
	}

	public ExampleStateB withString(String value) {
		return new ExampleStateB(value, this.immutableStringList, this.javaStringList, this.itemList);
	}

	public Seq<String> getImmutableStringList() {
		return immutableStringList;
	}

	public ExampleStateB withImmutableStringList(Seq<String> values) {
		return new ExampleStateB(this.string, values, this.javaStringList, this.itemList);
	}

	public List<String> getJavaStringList() {
		return Collections.unmodifiableList(javaStringList);
	}

	public ExampleStateB withJavaStringList(List<String> values) {
		return new ExampleStateB(this.string, this.immutableStringList, values, this.itemList);
	}

	public Seq<ExampleStateC> getItemList() {
		return itemList;
	}

	public ExampleStateB withItemList(Seq<ExampleStateC> items) {
		return new ExampleStateB(this.string, this.immutableStringList, this.javaStringList, items);
	}
}
