package de.neuenberger.game.tetris;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.Timer;

import de.neuenberger.game.core.GameState;
import de.neuenberger.game.tetris.logic.TetrisGameLogic;
import de.neuenberger.game.tetris.model.TetrisFallingTile;
import de.neuenberger.game.tetris.model.TetrisModel;
import de.neuenberger.game.tetris.view.TetrisController;

public class TetrisGame extends JFrame {
	private static final String START = "Start";

	TetrisModel tetrisModel = new TetrisModel(10, 20);

	TetrisGameLogic gameLogic = new TetrisGameLogic(tetrisModel);

	GameState gameState = GameState.GAME_OVER;

	KeyListener listener = new KeyAdapter() {
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (gameState == GameState.RUNNING) {
				int keyCode = e.getKeyCode();
				if (keyCode == 37) { // left
					tetrisModel.moveTileLeft();
				} else if (keyCode == 39) { // right
					tetrisModel.moveTileRight();
				} else if (keyCode == 40) {
					gameLogic.nextTick();
				} else if (keyCode == 38) {
					tetrisModel.rotateForward();
				} else {
					System.out.println("unknown kc: " + keyCode);
				}
			}
		};
	};


	ActionListener timerListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			gameLogic.nextTick();
		}
	};
	Timer gameTickTimer = new Timer(500, timerListener);

	public TetrisGame() {
		this.setSize(250,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout borderLayout = new BorderLayout();
		this.setLayout(borderLayout);

		JToolBar toolBar = new JToolBar();
		setupToolBar(toolBar);
		this.add(toolBar, BorderLayout.NORTH);
		tetrisController = new TetrisController(tetrisModel);
		this.add(tetrisController.getPanel(), BorderLayout.CENTER);
	}

	private JButton startStopPauseButton;

	private TetrisController tetrisController;

	private void setupToolBar(final JToolBar toolbar) {
		addKeyListener(listener);
		startStopPauseButton = new JButton(START);
		startStopPauseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (gameState == GameState.GAME_OVER) {
					// no game running.
					resetGame(); // start game
					gameTickTimer.start();
				} else if (gameState == GameState.RUNNING) {
					gameState = GameState.PAUSED;
					gameTickTimer.stop();
					startStopPauseButton.setText("continue");
					// messageScreen.setPause();
					// showMessageScreen();
				} else {
					gameState = GameState.RUNNING;
					gameTickTimer.start();

					startStopPauseButton.setText("pause");
					// showGameScreen();
				}
				TetrisGame.this.requestFocus();
			}

		});
		toolbar.add(startStopPauseButton);

		final JButton cheatButton = new JButton("c");
		cheatButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				// model.setBites(Collections.<Vector2D> emptyList());
				tetrisModel.removeBricksAtLine(tetrisModel.getHeight() - 1);
			}
		});
		toolbar.add(cheatButton);
	}

	protected void resetGame() {
		gameState = GameState.RUNNING;
		tetrisModel.putNextFallingTile(TetrisFallingTile.createRandom());
		tetrisModel.putNextFallingTile(TetrisFallingTile.createRandom());
	}

	public static void main(String[] args) {
		TetrisGame tetrisGame = new TetrisGame();
		tetrisGame.setVisible(true);
	}
}
