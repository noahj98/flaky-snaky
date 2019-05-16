import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class SnakeViewer extends JPanel {
	
	SnakeModel model;
	int snake_size;
	
	public SnakeViewer(SnakeModel sm, int width, int height, int diameter) {
		//ONLY for viewing purposes
		this.model = sm;
		this.snake_size = diameter;
		setup(width, height);
	}
	
	private void setup(int width, int height) {
		setPreferredSize(new Dimension(snake_size*width, snake_size*height));
		setOpaque(true);
		setFocusable(true);
		setBackground(Color.BLACK);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		SnakePoint[][] points = model.getPoints();
		
		g.setColor(Color.GREEN);
		
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points[i].length; j++) {
				SnakePoint.PointType curr = points[i][j].getPointType();
				
				switch (curr) {
				case EMPTY: {
					continue;
				}
				case SNAKE: {
					g.fillOval(i*snake_size, j*snake_size, snake_size, snake_size);
					g.setColor(Color.BLACK);
					g.drawOval(i*snake_size, j*snake_size, snake_size, snake_size);
					g.setColor(Color.GREEN);
					continue;
				}
				case BARRIER: {
					g.setColor(Color.WHITE);
					g.fillRect(i*snake_size, j*snake_size, snake_size, snake_size);
					g.setColor(Color.GREEN);
					continue;
				}
				case FOOD: {
					g.setColor(Color.RED);
					g.fillRect(i*snake_size, j*snake_size, snake_size, snake_size);
					g.setColor(Color.GREEN);
					continue;
				}
				}
			}
		}
		
		g.fillOval(model.getHead().getX()*snake_size, model.getHead().getY()*snake_size, snake_size, snake_size);
	}
	
}
