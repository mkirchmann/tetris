package de.neuenberger.game.tetris.model;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.neuenberger.game.core.Vector2D;

public class TetrisModelTest {
	@Test
	public void testRemoveCompletedLines() {
		TetrisModel tetrisModel = new TetrisModel(2, 7);
		Vector2D brickAbove = new Vector2D(1, 2);
		Vector2D brickBelow = new Vector2D(0, 6);
		Vector2D brickMiddle = new Vector2D(0, 4);
		tetrisModel.addAllBricks(Arrays.asList(new Vector2D(0, 5), new Vector2D(1, 5), brickMiddle, new Vector2D(0, 3),
				brickAbove, new Vector2D(1, 3), brickBelow));
		tetrisModel.removeBricksAtLine(3, 5);
		List<Vector2D> allBricks = tetrisModel.getAllBricks();
		Assert.assertEquals(3, allBricks.size());
		Assert.assertTrue(allBricks.contains(brickBelow)); // unchanged
		Assert.assertTrue(allBricks.contains(new Vector2D(1, 4))); // brickAbove
																	// moved 2
																	// lines,
																	// because 2
																	// lines
																	// removed
		Assert.assertTrue(allBricks.contains(new Vector2D(0, 5))); // brickMiddle
																	// moved 1
																	// lines,
																	// because 1
																	// lines
																	// removed
	}
}
