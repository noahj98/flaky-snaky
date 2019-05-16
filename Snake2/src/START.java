
public class START {
	
	public static void main(String[] args) {
		
		
//		final int diameter = 12;
		final int WIDTH, HEIGHT, SNAKE_DIAMETER;
		WIDTH = 22;
		HEIGHT = 25;
		SNAKE_DIAMETER = 12;
		
		SnakeController controller = new SnakeController(WIDTH, HEIGHT, SNAKE_DIAMETER);
		controller.start();
	}
	
}
