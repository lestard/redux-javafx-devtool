package eu.lestard.redux_javafx_devtool.updater.stateparser;

public class ExampleStateA {

	private final String stringValue;
	private final double number;

	private final ExampleStateB subState1;
	private final ExampleStateB subState2;

	private ExampleStateA(String stringValue, double number, ExampleStateB subState1, ExampleStateB subState2) {
		this.stringValue = stringValue;
		this.number = number;
		this.subState1 = subState1;
		this.subState2 = subState2;
	}

	public static ExampleStateA create() {
		return new ExampleStateA("", 0, ExampleStateB.create(), null);
	}

	public String getStringValue() {
		return stringValue;
	}

	public ExampleStateA withStringValue(String value) {
		return new ExampleStateA(value, this.number, this.subState1, this.subState2);
	}

	public double getNumber() {
		return number;
	}

	public ExampleStateA withNumber(double value) {
		return new ExampleStateA(this.stringValue, value, this.subState1, this.subState2);
	}

	public ExampleStateB getSubState1() {
		return subState1;
	}

	public ExampleStateA withSubState1(ExampleStateB value) {
		return new ExampleStateA(this.stringValue, this.number, value, this.subState2);
	}

	public ExampleStateB getSubState2() {
		return subState2;
	}

	public ExampleStateA withSubState2(ExampleStateB value) {
		return new ExampleStateA(this.stringValue, this.number, this.subState1, value);
	}
}

