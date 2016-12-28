package de.neuenberger.game.tetris.view;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;

import de.neuenberger.game.core.GameImageProducer;
import de.neuenberger.game.core.ImagePanel;
import de.neuenberger.game.tetris.model.TetrisModel;

public class TetrisController {
	TetrisModel model;
	private GameImageProducer gameImageProducer;

	public static final Image imageBrick = getImage("wall.png");

	public static final int BLOCK_SIZE = 10;

	public TetrisController(TetrisModel model) {
		this(model,new GameImageProducer(BLOCK_SIZE, new ImagePanel(), model.getWidth(),model.getHeight()));
	}

	public TetrisController(TetrisModel model, GameImageProducer gameImageProducer) {
		this.model = model;
		this.gameImageProducer = gameImageProducer;
	}

	private static Image getImage(String string) {
		ImageIcon imageIcon = new ImageIcon(TetrisController.class.getResource("res/" + string));
		return imageIcon.getImage();
	}

	public void update() {
		gameImageProducer.clear(Color.BLACK);
		gameImageProducer.drawListWithImage(model.getAllBricks(), 0, imageBrick);

		gameImageProducer.drawListWithImage(model.getCurrentFallingTilePositionTransposed(), 0, imageBrick);

		gameImageProducer.repaint();
	}

	/**
	 * @return the panel
	 */
	public ImagePanel getPanel() {
		return gameImageProducer.getPanel();
	}
}
