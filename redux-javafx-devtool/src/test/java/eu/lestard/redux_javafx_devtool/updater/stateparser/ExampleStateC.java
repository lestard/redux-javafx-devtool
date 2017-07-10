package eu.lestard.redux_javafx_devtool.updater.stateparser;

public class ExampleStateC {

	private final String stringA;

	private final String stringB;

	private ExampleStateC(String stringA, String stringB) {
		this.stringA = stringA;
		this.stringB = stringB;
	}

	public static ExampleStateC create() {
		return new ExampleStateC("", "");
	}

	public String getStringA() {
		return stringA;
	}

	public ExampleStateC withStringA(String value) {
		return new ExampleStateC(value, this.stringB);
	}

	public String getStringB() {
		return stringB;
	}

	public ExampleStateC withStringB(String value) {
		return new ExampleStateC(this.stringA, value);
	}

}
