package de.neuenberger.game.tetris.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.neuenberger.game.core.Vector2D;

public enum TetrisTiles {
	COMPOSITION_L1("***\n" + "00*", "0*\n" + "0*\n" + "**", "*00\n" + "***", "**\n" + "*0\n" + "*0"), COMPOSITION_L2(
			"00*\n" + "***", "*0\n" + "*0\n" + "**", "***\n" + "*00",
			"**\n" + "0*\n" + "0*"), COMPOSITION_I("*\n*\n*\n*", "0000\n****"), COMPOSITION_Y("***\n0*0", "0*\n**\n0*",
					"0*0\n***", "*0\n**\n*0"), COMPOSITION_O("**\n**"), COMPOSITION_Z1("*0\n**\n0*",
							"0**\n**0"), COMPOSITION_Z2("0*\n**\n*0", "**0\n0**");
	final Map<List<Vector2D>, Integer> stringToInteger;
	private final List<List<Vector2D>> rotationVector;

	TetrisTiles(String... rotationLookString) {
		List<String> rotationLook = Collections.unmodifiableList(new ArrayList<>(Arrays.asList(rotationLookString)));
		Map<List<Vector2D>, Integer> tempVectorToIntMap = new HashMap<>();
		final List<List<Vector2D>> rotationVector = new ArrayList<>(rotationLookString.length);
		for (String string : rotationLookString) {
			List<Vector2D> list = Collections.unmodifiableList(Vector2D.fromString(string));
			tempVectorToIntMap.put(list, rotationLook.indexOf(string));
			rotationVector.add(list);
		}
		this.rotationVector = Collections.unmodifiableList(rotationVector);
		stringToInteger = Collections.unmodifiableMap(tempVectorToIntMap);
	}

	public static TetrisTiles random() {
		Random random = new Random();
		TetrisTiles[] values = values();
		int nextInt = random.nextInt(values.length);
		return values[nextInt];
	}

	public List<Vector2D> getInitialPosition() {
		return rotationVector.get(0);
	}

	public List<Vector2D> getNextPositionFor(List<Vector2D> str) {
		Integer integer = stringToInteger.get(str);
		if (integer != null) {
			integer++;
			integer %= rotationVector.size();
			return rotationVector.get(integer);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public List<Vector2D> getPreviousPositionFor(List<Vector2D> str) {
		Integer integer = stringToInteger.get(str);
		if (integer != null) {
			integer--;
			if (integer < 0) {
				integer += rotationVector.size();
			}
			return rotationVector.get(integer);
		} else {
			throw new IllegalArgumentException();
		}
	}
}
