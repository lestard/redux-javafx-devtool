package eu.lestard.redux_javafx_devtool.updater.stateparser;

import eu.lestard.redux_javafx_devtool.state.StateNode;
import javaslang.collection.Array;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class StateParserTest {

	@Test
	public void testPrimitives() {
		final StateParser stateParser = StateParser.create();

		StateNode stateNode = stateParser.parse("root", "Test");
		assertThat(stateNode.getValue()).isEqualTo("Test");
		assertThat(stateNode.getChildren()).isEmpty();
		assertThat(stateNode.getValueStringRep()).isEqualTo("root: \"Test\"");

		stateNode = stateParser.parse("root", 123);
		assertThat(stateNode.getValue()).isEqualTo(123);
		assertThat(stateNode.getChildren()).isEmpty();
		assertThat(stateNode.getValueStringRep()).isEqualTo("root: 123");

		stateNode = stateParser.parse("root", true);
		assertThat(stateNode.getValue()).isEqualTo(true);
		assertThat(stateNode.getChildren()).isEmpty();
		assertThat(stateNode.getValueStringRep()).isEqualTo("root: true");

	}

	@Test
	public void testInitialState() {
		final StateParser stateParser = StateParser.create();

		final ExampleStateA state = ExampleStateA.create();

		final StateNode stateNode = stateParser.parse("root", state);

		assertThat(stateNode.getNodeName()).isEqualTo("root");
		assertThat(stateNode.getValue()).isEqualTo(state);
		assertThat(stateNode.getChildren()).hasSize(4);
		assertThat(stateNode.getChildren().map(StateNode::getNodeName))
			.contains("stringValue", "number", "subState1", "subState2");

		final StateNode stringValueNode = getNode(stateNode, "stringValue");
		final StateNode numberNode = getNode(stateNode, "number");
		final StateNode subState1Node = getNode(stateNode, "subState1");
		final StateNode subState2Node = getNode(stateNode, "subState2");

		assertThat(stringValueNode.getValue()).isEqualTo("");
		assertThat(numberNode.getValue()).isEqualTo(0.0);
		assertThat(subState1Node.getValue()).isEqualTo(state.getSubState1());
		assertThat(subState2Node.getValue()).isEqualTo(null);


		assertThat(stringValueNode.getChildren()).isEmpty();
		assertThat(numberNode.getChildren()).isEmpty();
		assertThat(subState2Node.getChildren()).isEmpty();

		assertThat(subState1Node.getChildren()).hasSize(4);
		assertThat(subState1Node.getChildren().map(StateNode::getNodeName))
			.contains("string", "immutableStringList", "javaStringList", "itemList");

		final StateNode subStateStringNode = getNode(subState1Node, "string");
		final StateNode subStateImmutableStringListNode = getNode(subState1Node, "immutableStringList");
		final StateNode subStateJavaStringListNode = getNode(subState1Node, "javaStringList");
		final StateNode subStateItemListNode = getNode(subState1Node, "itemList");

		assertThat(subStateStringNode.getChildren()).isEmpty();
		assertThat(subStateImmutableStringListNode.getChildren()).isEmpty();
		assertThat(subStateJavaStringListNode.getChildren()).isEmpty();
		assertThat(subStateItemListNode.getChildren()).isEmpty();
	}

	@Test
	public void testFilledState() {
		final StateParser stateParser = StateParser.create();

		final ExampleStateC stateC_1 = ExampleStateC.create().withStringA("a1").withStringB("b1");
		final ExampleStateC stateC_2 = ExampleStateC.create().withStringA("a2").withStringB("b2");
		final ExampleStateC stateC_3 = ExampleStateC.create().withStringA("a3").withStringB("b3");
		final ExampleStateC stateC_4 = ExampleStateC.create().withStringA("a4").withStringB("b4");

		ExampleStateB stateB_1 = ExampleStateB.create()
			.withString("stateB 1")
			.withImmutableStringList(Array.of("b1_a1", "b1_a2"))
			.withJavaStringList(Arrays.asList("b1_b1", "b1_b2", "b1_b3", "b1_b4"))
			.withItemList(Array.of(stateC_1, stateC_2));

		ExampleStateB stateB_2 = ExampleStateB.create()
			.withString("stateB 2")
			.withImmutableStringList(Array.of("b2_a1", "b2_a2", "b2_a3"))
			.withJavaStringList(Arrays.asList("b2_b1", "b2_b2"))
			.withItemList(Array.of(stateC_3, stateC_4));

		final ExampleStateA state = ExampleStateA.create()
			.withNumber(23.4)
			.withStringValue("state A")
			.withSubState1(stateB_1)
			.withSubState2(stateB_2);

		final StateNode stateNode = stateParser.parse("root", state);

		assertThat(stateNode.getNodeName()).isEqualTo("root");
		assertThat(stateNode.getValue()).isEqualTo(state);
		assertThat(stateNode.getChildren()).hasSize(4);
		assertThat(stateNode.getChildren().map(StateNode::getNodeName))
			.contains("stringValue", "number", "subState1", "subState2");


		final StateNode stringValueNode = getNode(stateNode, "stringValue");
		final StateNode numberNode = getNode(stateNode, "number");
		final StateNode subStateB1Node = getNode(stateNode, "subState1");
		final StateNode subState2Node = getNode(stateNode, "subState2");

		assertThat(stringValueNode.getValue()).isEqualTo("state A");
		assertThat(numberNode.getValue()).isEqualTo(23.4);
		assertThat(subStateB1Node.getValue()).isEqualTo(stateB_1);
		assertThat(subState2Node.getValue()).isEqualTo(stateB_2);


		assertThat(stringValueNode.getChildren()).isEmpty();
		assertThat(numberNode.getChildren()).isEmpty();

		assertThat(subStateB1Node.getChildren()).hasSize(4);
		assertThat(subStateB1Node.getChildren().map(StateNode::getNodeName))
			.contains("string", "immutableStringList", "javaStringList", "itemList");

		final StateNode subStateB1StringNode = getNode(subStateB1Node, "string");
		final StateNode subStateB1ImmutableStringListNode = getNode(subStateB1Node, "immutableStringList");
		final StateNode subStateB1JavaStringListNode = getNode(subStateB1Node, "javaStringList");
		final StateNode subStateB1ItemListNode = getNode(subStateB1Node, "itemList");


		assertThat(subStateB1StringNode.getChildren()).isEmpty();
		assertThat(subStateB1ImmutableStringListNode.getChildren()).hasSize(2);
		assertThat(subStateB1JavaStringListNode.getChildren()).hasSize(4);
		assertThat(subStateB1ItemListNode.getChildren()).hasSize(2);

		assertThat(subStateB1ImmutableStringListNode.getChildren().map(StateNode::getNodeName))
			.containsExactly("0", "1");
		final StateNode immutableListItem0 = getNode(subStateB1ImmutableStringListNode, "0");
		final StateNode immutableListItem1 = getNode(subStateB1ImmutableStringListNode, "1");

		assertThat(immutableListItem0.getValue()).isEqualTo("b1_a1");
		assertThat(immutableListItem0.getChildren()).isEmpty();
		assertThat(immutableListItem1.getValue()).isEqualTo("b1_a2");
		assertThat(immutableListItem1.getChildren()).isEmpty();

		assertThat(subStateB1JavaStringListNode.getChildren()).hasSize(4);
		assertThat(subStateB1JavaStringListNode.getChildren().map(StateNode::getNodeName))
			.containsExactly("0", "1", "2", "3");

		final StateNode javaListItem0 = getNode(subStateB1JavaStringListNode, "0");
		final StateNode javaListItem1 = getNode(subStateB1JavaStringListNode, "1");
		final StateNode javaListItem2 = getNode(subStateB1JavaStringListNode, "2");
		final StateNode javaListItem3 = getNode(subStateB1JavaStringListNode, "3");

		assertThat(javaListItem0.getValue()).isEqualTo("b1_b1");
		assertThat(javaListItem0.getChildren()).isEmpty();
		assertThat(javaListItem1.getValue()).isEqualTo("b1_b2");
		assertThat(javaListItem1.getChildren()).isEmpty();
		assertThat(javaListItem2.getValue()).isEqualTo("b1_b3");
		assertThat(javaListItem2.getChildren()).isEmpty();
		assertThat(javaListItem3.getValue()).isEqualTo("b1_b4");
		assertThat(javaListItem3.getChildren()).isEmpty();

		assertThat(subStateB1ItemListNode.getChildren()).hasSize(2);
		assertThat(subStateB1ItemListNode.getChildren().map(StateNode::getNodeName))
			.containsExactly("0", "1");

		final StateNode itemListItem0 = getNode(subStateB1ItemListNode, "0");
		final StateNode itemListItem1 = getNode(subStateB1ItemListNode, "1");

		assertThat(itemListItem0.getValue()).isEqualTo(stateC_1);
		assertThat(itemListItem0.getChildren()).hasSize(2);

		assertThat(itemListItem0.getChildren().map(StateNode::getNodeName))
			.contains("stringA", "stringB");
		final StateNode itemListItem0StringA = getNode(itemListItem0, "stringA");
		final StateNode itemListItem0StringB = getNode(itemListItem0, "stringB");

		assertThat(itemListItem0StringA.getValue()).isEqualTo("a1");
		assertThat(itemListItem0StringA.getChildren()).isEmpty();
		assertThat(itemListItem0StringB.getValue()).isEqualTo("b1");
		assertThat(itemListItem0StringB.getChildren()).isEmpty();

		assertThat(itemListItem1.getValue()).isEqualTo(stateC_2);
		assertThat(itemListItem1.getChildren()).hasSize(2);

		assertThat(itemListItem1.getChildren().map(StateNode::getNodeName))
			.contains("stringA", "stringB");
		final StateNode itemListItem1StringA = getNode(itemListItem1, "stringA");
		final StateNode itemListItem1StringB = getNode(itemListItem1, "stringB");

		assertThat(itemListItem1StringA.getValue()).isEqualTo("a2");
		assertThat(itemListItem1StringA.getChildren()).isEmpty();
		assertThat(itemListItem1StringB.getValue()).isEqualTo("b2");
		assertThat(itemListItem1StringB.getChildren()).isEmpty();
	}

	private static StateNode getNode(StateNode root, String nodeName) {
		return root.getChildren()
			.filter(node -> node.getNodeName().equals(nodeName))
			.getOrElse(() -> null);
	}
}
