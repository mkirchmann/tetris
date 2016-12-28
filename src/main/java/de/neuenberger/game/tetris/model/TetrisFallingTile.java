package de.neuenberger.game.tetris.model;

import java.util.List;

import de.neuenberger.game.core.Vector2D;

public class TetrisFallingTile {
	private final TetrisTiles tetrisTile;

	private List<Vector2D> currentCompositionPosition;

	TetrisFallingTile() {
		this(TetrisTiles.random());
	}

	TetrisFallingTile(TetrisTiles composition) {
		this.tetrisTile = composition;
		currentCompositionPosition = composition.getInitialPosition();
	}

	public void rotateForward() {
		currentCompositionPosition = tetrisTile.getNextPositionFor(currentCompositionPosition);
	}

	public void rotateBackward() {
		currentCompositionPosition = tetrisTile.getPreviousPositionFor(currentCompositionPosition);
	}

	public List<Vector2D> getCurrentTileRotationPosition() {
		return currentCompositionPosition;
	}

	public static TetrisFallingTile createRandom() {
		return new TetrisFallingTile();
	}
}
