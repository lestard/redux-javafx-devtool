package eu.lestard.redux_javafx_devtool.util;

import org.apache.commons.text.RandomStringGenerator;

import java.util.Random;

public class IdGenerator {
	private static final int ID_LENGTH = 15;

	private RandomStringGenerator generator;

	public static IdGenerator create(long seed) {
		return new IdGenerator(seed);
	}

	private IdGenerator(long seed) {
		Random random = new Random(seed);

		this.generator = new RandomStringGenerator.Builder()
			.withinRange('a', 'z')
			.usingRandom(random::nextInt)
			.build();
	}

	public String getId() {
		return generator.generate(ID_LENGTH);
	}
}
