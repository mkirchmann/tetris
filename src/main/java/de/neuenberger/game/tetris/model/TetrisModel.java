package de.neuenberger.game.tetris.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import de.neuenberger.game.core.Vector2D;

public class TetrisModel {
	private TetrisFallingTile currentFallingTile;
	private TetrisFallingTile nextFallingTile;

	private Vector2D currentFallingTilePosition;
	private List<Vector2D> currentFallingTilePositionTransposed;

	private List<Vector2D> bricks = new LinkedList<>();
	private List<Vector2D> unmodifiableBricks = Collections.unmodifiableList(bricks);
	private int width;
	private int height;
	private Vector2D moveDownVector = new Vector2D(0, 1);

	private PropertyChangeSupport support = new PropertyChangeSupport(this);

	public TetrisModel(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void moveTileLeft() {
		tryMoveX(-1);
	}

	private void tryMoveX(int moveX) {
		Vector2D newMovedPosition = currentFallingTilePosition.moveX(moveX);
		List<Vector2D> currentTileRotationPosition = Vector2D.move(currentFallingTile.getCurrentTileRotationPosition(),
				newMovedPosition);

		boolean invalid = currentTileRotationPosition.stream()
				.anyMatch(v -> (v.getX() < 0 || v.getX() >= getWidth()));
		boolean occupied = currentTileRotationPosition.stream().anyMatch(v -> hasBrickAt(v));
		if (!invalid && !occupied) {
			setCurrentFallingTilePosition(newMovedPosition);
			clearCurrentFallingTilePositionTransposed();
		}
	}

	private void setCurrentFallingTilePosition(Vector2D newMovedPosition) {
		PropertyChangeEvent event = new PropertyChangeEvent(this, "currentFallingTilePosition",
				this.currentFallingTilePosition, newMovedPosition);
		currentFallingTilePosition = newMovedPosition;
		support.firePropertyChange(event);
	}

	private boolean hasBrickAt(Vector2D v) {
		return hasBrickAt(v.getX(), v.getY());
	}

	public void moveTileRight() {
		tryMoveX(1);
	}

	public void rotateForward() {
		currentFallingTile.rotateForward();
		clearCurrentFallingTilePositionTransposed();
	}

	public void rotateBackward() {
		currentFallingTile.rotateBackward();
		clearCurrentFallingTilePositionTransposed();
	}

	public void moveTileDown() {
		currentFallingTilePosition = currentFallingTilePosition.moveY(1);
		clearCurrentFallingTilePositionTransposed();
	}

	private void clearCurrentFallingTilePositionTransposed() {
		PropertyChangeEvent event = new PropertyChangeEvent(this, "currentFallingTilePositionTransposed",
				currentFallingTilePositionTransposed, null);
		currentFallingTilePositionTransposed = null;
		support.firePropertyChange(event);
	}

	public List<Vector2D> getCurrentFallingTilePositionTransposed() {
		if (currentFallingTilePositionTransposed == null) {
			currentFallingTilePositionTransposed = Vector2D.move(currentFallingTile.getCurrentTileRotationPosition(),
					currentFallingTilePosition);
		}
		return currentFallingTilePositionTransposed;
	}

	public TetrisFallingTile getCurrentFallingTile() {
		return currentFallingTile;
	}

	public TetrisFallingTile getNextFallingTile() {
		return nextFallingTile;
	}

	public List<Vector2D> getAllBricks() {
		return unmodifiableBricks;
	}

	public void addAllBricks(List<Vector2D> tileTransposed) {
		bricks.addAll(tileTransposed);
		fireBricksChanged();
	}

	public void putNextFallingTile(TetrisFallingTile nextFallingTile) {
		currentFallingTile = this.nextFallingTile;
		this.nextFallingTile = nextFallingTile;
		this.currentFallingTilePosition = new Vector2D(width / 2, -1);
		clearCurrentFallingTilePositionTransposed();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean hasBrickAt(int x, int y) {
		List<Vector2D> allBricks = getAllBricks();
		return allBricks.stream().anyMatch(v -> v.getX() == x && v.getY() == y);
	}

	public void removeBricksAtLine(Integer... allY) {
		for (int y : allY) {
			List<Vector2D> vectorsToRemove = getAllBricks().stream().filter(v -> v.getY() == y)
					.collect(Collectors.toList());
			bricks.removeAll(vectorsToRemove);
		}
		List<Integer> asList = Arrays.asList(allY);
		Collections.sort(asList);
		Collections.reverse(asList);
		int removedLines = 0;
		for (Integer integer : asList) {
			int limit = integer + removedLines++;
			List<Vector2D> list = getAllBricks().stream().filter(v -> v.getY() < limit).collect(Collectors.toList());
			bricks.removeAll(list);
			List<Vector2D> movedBricks = Vector2D.move(list, moveDownVector);
			bricks.addAll(movedBricks);
		}
		fireBricksChanged();
	}

	private void fireBricksChanged() {
		PropertyChangeEvent event = new PropertyChangeEvent(this, "bricks", null, bricks);
		support.firePropertyChange(event);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		support.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		support.removePropertyChangeListener(propertyName, listener);
	}
}
