package de.neuenberger.game.tetris.model;

import java.util.List;

import de.neuenberger.game.core.Vector2D;

public class TetrisFallingTile {
	private final TetrisTiles tetrisTile;

	private List<Vector2D> currentTileRotationPosition;

	TetrisFallingTile() {
		this(TetrisTiles.random());
	}

	TetrisFallingTile(TetrisTiles composition) {
		this.tetrisTile = composition;
		currentTileRotationPosition = composition.getInitialPosition();
	}

	public void rotateForward() {
		currentTileRotationPosition = tetrisTile.getNextPositionFor(currentTileRotationPosition);
	}

	public void rotateBackward() {
		currentTileRotationPosition = tetrisTile.getPreviousPositionFor(currentTileRotationPosition);
	}

	public List<Vector2D> getCurrentTileRotationPosition() {
		return currentTileRotationPosition;
	}

	public static TetrisFallingTile createRandom() {
		return new TetrisFallingTile();
	}
}
