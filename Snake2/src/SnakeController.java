import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

public class SnakeController implements KeyListener, ActionListener {
	
	//Update model and view
	private SnakeModel snake_model;
	private SnakeViewer snake_viewer;
	private Timer timer;
	private boolean is_paused;
	
	public SnakeController(int width, int height, int diameter) {
		modelSetup(width, height);
		viewerSetup(width, height, diameter);
		timerSetup();
		is_paused = true;
	}
	
	private void resume() {
		if (!isPaused()) return;
		if (!snake_model.isAlive()) return;
		timer.start();
		is_paused = false;
	}
	
	private void pause() {
		timer.stop();
		is_paused = true;
	}
	
	private boolean isPaused() {
		return is_paused;
	}
	
	public void start() {
		resume();
	}
	
	private void timerSetup() {
		this.timer = new Timer(120, this);
	}
	
	private void viewerSetup(int width, int height, int diameter) {
		this.snake_viewer = new SnakeViewer(snake_model, width, height, diameter);
		SnakeFrame frame = new SnakeFrame(this.snake_viewer);
		this.snake_viewer.addKeyListener(this);
	}
	
	private void modelSetup(int width, int height) {
		this.snake_model = new SnakeModel(width, height);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		SnakePoint.Direction d;
		
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP: {}
		case KeyEvent.VK_W: {
			d = SnakePoint.Direction.DOWN;
			break;
		}
		case KeyEvent.VK_RIGHT: {}
		case KeyEvent.VK_D: {
			d = SnakePoint.Direction.RIGHT;			
			break;
		}
		case KeyEvent.VK_DOWN: {}
		case KeyEvent.VK_S: {
			d = SnakePoint.Direction.UP;
			break;
		}
		case KeyEvent.VK_LEFT: {}
		case KeyEvent.VK_A: {
			d = SnakePoint.Direction.LEFT;
			break;
		}
		default: { //Invalid key pressed, pause game
			pause();
			return;
		}
		}
		
		snake_model.changeDirection(d);
		
		resume();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!snake_viewer.hasFocus()) {
			pause();
			return;
		}
		snake_model.move();
		if (snake_model.isAlive()) {
			snake_viewer.repaint();
		} else {
			pause();
//			gameOver();
		}
	}
	
}
