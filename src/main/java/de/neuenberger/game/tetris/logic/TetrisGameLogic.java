package de.neuenberger.game.tetris.logic;

import java.util.ArrayList;
import java.util.List;

import de.neuenberger.game.core.Vector2D;
import de.neuenberger.game.tetris.model.TetrisFallingTile;
import de.neuenberger.game.tetris.model.TetrisModel;

public class TetrisGameLogic {

	private TetrisModel tetrisModel;

	public TetrisGameLogic(TetrisModel tetrisModel) {
		this.tetrisModel = tetrisModel;
	}

	public void nextTick() {
		List<Vector2D> tileTransposed = tetrisModel.getCurrentFallingTilePositionTransposed();
		
		int limit = tetrisModel.getHeight() - 1;
		boolean match = tileTransposed.stream().anyMatch(v -> v.getY() >= limit);
		if (match || isOneOnTopOf(tetrisModel.getAllBricks(), tileTransposed)) {
			tetrisModel.addAllBricks(tileTransposed);
			tetrisModel.putNextFallingTile(TetrisFallingTile.createRandom());
			List<Integer> linesToRemove = getAllCompletedLines();
			tetrisModel.removeBricksAtLine(linesToRemove.toArray(new Integer[] {}));
			// TODO check game over
		} else {
			tetrisModel.moveTileDown();
		}
	}

	List<Integer> getAllCompletedLines() {
		List<Integer> linesToRemove = new ArrayList<>();
		for (int y = 0; y < tetrisModel.getHeight(); y++) {
			boolean lineComplete = true;
			for (int i = 0; i < tetrisModel.getWidth(); i++) {
				boolean hasBrickAt = tetrisModel.hasBrickAt(i, y);
				if (!hasBrickAt) {
					lineComplete = false;
					break;
				}
			}
			if (lineComplete) {
				linesToRemove.add(y);
			}
		}
		return linesToRemove;
	}

	/**
	 * Check if any of the given tiles is on top of any of the given obstacles.
	 * 
	 * @param obstacles
	 *            given {@link List} of obstacles.
	 * @param tiles
	 *            given {@link List} of tiles.
	 * @return true if on top of obstacle
	 */
	private boolean isOneOnTopOf(List<Vector2D> obstacles, List<Vector2D> tiles) {
		for (Vector2D vector2d : tiles) {
			for (Vector2D obstacle2d : obstacles) {
				if (vector2d.hasSameX(obstacle2d)) {
					if (vector2d.getY() == obstacle2d.getY() - 1) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
