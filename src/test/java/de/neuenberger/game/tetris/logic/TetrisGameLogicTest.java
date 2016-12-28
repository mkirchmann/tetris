package de.neuenberger.game.tetris.logic;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.neuenberger.game.core.Vector2D;
import de.neuenberger.game.tetris.model.TetrisModel;

public class TetrisGameLogicTest {
	@Test
	public void testCompletedLines() {
		TetrisModel tetrisModel = new TetrisModel(2, 7);
		tetrisModel.addAllBricks(Arrays.asList(new Vector2D(0, 5), new Vector2D(1, 5), new Vector2D(0, 4),
				new Vector2D(0, 3), new Vector2D(1, 3)));
		TetrisGameLogic tetrisGameLogic = new TetrisGameLogic(tetrisModel);
		List<Integer> allCompletedLines = tetrisGameLogic.getAllCompletedLines();
		Assert.assertEquals(2, allCompletedLines.size());
		Assert.assertTrue(allCompletedLines.contains(5));
		Assert.assertTrue(allCompletedLines.contains(3));
	}
}
