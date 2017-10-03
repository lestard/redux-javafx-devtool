package eu.lestard.redux_javafx_devtool.state;


import io.vavr.collection.Array;
import io.vavr.collection.Seq;

public class StateNode {

	private final String nodeName;
	private final Object value;
	private final Seq<StateNode> children;
	private final String valueStringRep;

	private StateNode(String nodeName, Object value, Seq<StateNode> children, String valueStringRep) {
		this.nodeName = nodeName;
		this.value = value;
		this.children = children;
		this.valueStringRep = valueStringRep;
	}

	public static StateNode create(String nodeName, Object value) {
		return new StateNode(nodeName, value, Array.empty(), null);
	}

	public String getNodeName() {
		return nodeName;
	}

	public String getValueStringRep() {
		return valueStringRep;
	}

	public StateNode withValueStringRepresentation(String stringRep) {
		return new StateNode(this.nodeName, this.value, this.children, stringRep);
	}

	public Object getValue() {
		return value;
	}

	public Seq<StateNode> getChildren() {
		return children;
	}

	public StateNode withChildren(Seq<StateNode> children) {
		return new StateNode(this.nodeName, this.value, children, this.valueStringRep);
	}
}
