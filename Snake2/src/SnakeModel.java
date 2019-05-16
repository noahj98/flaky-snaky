import java.util.Random;

public class SnakeModel {

	private SnakePoint[][] points;
	private boolean is_alive; //del this comment
	private boolean temp;
	private Point head, tail;
	private SnakePoint.Direction current_direction, next_direction, queued_next_direction;
	private int width, height;
	private int length_to_add;
	private int snake_length;
	private Random r;

	public SnakeModel(int width, int height) {
		setup(width, height);
		reset();
	}

	private void setup(int width, int height) {
		this.width = width;
		this.height = height;

		points = new SnakePoint[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				points[i][j] = new SnakePoint(SnakePoint.PointType.EMPTY, SnakePoint.Direction.RIGHT);
			}
		}
		head = new Point(-1, -1);
		tail = new Point(-1, -1);
		r = new Random();
	}

	private void reset() {
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points[0].length; j++) {
				points[i][j].setPointType(SnakePoint.PointType.EMPTY);
				points[i][j].setDirection(SnakePoint.Direction.RIGHT);
			}
		}

		int y = points[0].length / 2;
		int snake_start_x = points.length / 4;
		int food_start_x = points.length * 3 / 4;

		points[snake_start_x][y].setPointType(SnakePoint.PointType.SNAKE);
		points[food_start_x][y].setPointType(SnakePoint.PointType.FOOD);
		
		points[snake_start_x][y].setDirection(SnakePoint.Direction.RIGHT);

		head.setX(snake_start_x);
		head.setY(y);
		tail.setX(snake_start_x);
		tail.setY(y);

		is_alive = true;
		current_direction = SnakePoint.Direction.RIGHT;
		next_direction = null;
		queued_next_direction = null;
		snake_length = 1;
		length_to_add = 0;
	}
	
	public Point getHead() {
		return head;
	}
	
	public boolean isAlive() {
		return is_alive;
	}

	private void kill() {
		is_alive = false;
	}

	public void move() {
		updateDirection();
		moveTail();
		moveHead();
	}

	private void updateDirection() {
		
		if (next_direction == null) {
		} else if (queued_next_direction == null) {
			current_direction = next_direction;
			next_direction = null;
		} else {
			current_direction = next_direction;
			next_direction = queued_next_direction;
			queued_next_direction = null;
		}
		points[head.getX()][head.getY()].setDirection(current_direction);
	}

	private void moveTail() {
		if (length_to_add == 0) {
			points[tail.getX()][tail.getY()].setPointType(SnakePoint.PointType.EMPTY);
			
			int new_tail_x = tail.getX();
			int new_tail_y = tail.getY();
			
			switch (points[new_tail_x][new_tail_y].getDirection()) {
			case UP: {
				new_tail_y++;
				break;
			}
			case RIGHT: {
				new_tail_x++;
				break;
			}
			case DOWN: {
				new_tail_y--;
				break;
			}
			case LEFT: {
				new_tail_x--;
				break;
			}
			}
			
			tail.setX(new_tail_x);
			tail.setY(new_tail_y);
		} else {
			length_to_add--;
			snake_length++;
		}
	}
	
	private void moveHead() {
		int new_head_x = head.getX();
		int new_head_y = head.getY();
		
		switch (points[new_head_x][new_head_y].getDirection()) {
		case UP: {
			new_head_y++;
			if (new_head_y == height) {
				kill();
				return;
			}
			break;
		}
		case RIGHT: {
			new_head_x++;
			if (new_head_x == width) {
				kill();
				return;
			}
			break;
		}
		case DOWN: {
			if (new_head_y == 0) {
				kill();
				return;
			}
			new_head_y--;
			break;
		}
		case LEFT: {
			if (new_head_x == 0) {
				kill();
				return;
			}
			new_head_x--;
			break;
		}
		}

		SnakePoint new_head = points[new_head_x][new_head_y];
		
		if (new_head.getPointType() == SnakePoint.PointType.EMPTY) {
			new_head.setPointType(SnakePoint.PointType.SNAKE);
			new_head.setDirection(current_direction);
		} else if (new_head.getPointType() == SnakePoint.PointType.FOOD) {
			new_head.setPointType(SnakePoint.PointType.SNAKE);
			new_head.setDirection(current_direction);
			if (snake_length < 30) length_to_add += 3;
			else if (snake_length < 55) length_to_add += 2;
			else length_to_add++;
			addFood();
			addBarrier();
		} else {
			kill();
			return;
		}

		head.setX(new_head_x);
		head.setY(new_head_y);
	}
	
	private void addFood() { //add test for bad spot (trapped w/ barriers)
		int x, y;
		do {
			x = r.nextInt(width);
			y = r.nextInt(height);
		} while (points[x][y].getPointType() != SnakePoint.PointType.EMPTY);
		points[x][y].setPointType(SnakePoint.PointType.FOOD);
	}
	
	private void addBarrier() {
		int test = r.nextInt(150);
		if (test > snake_length) {
			return;
		}
		
		int x, y;
		do {
			x = r.nextInt(width);
			y = r.nextInt(height);
		} while (points[x][y].getPointType() != SnakePoint.PointType.EMPTY);
		points[x][y].setPointType(SnakePoint.PointType.BARRIER);
	}

	public void changeDirection(SnakePoint.Direction d) {
		SnakePoint.Direction dir = null;
		if (next_direction == null) {
			if (snake_length == 1) {
				next_direction = d;
				return;
			}
			dir = current_direction;
		} else if (queued_next_direction == null) {
			if (snake_length == 1) {
				queued_next_direction = d;
				return;
			}
			dir = next_direction;
		}
		
		switch (d) {
		case UP: {
			if (dir == SnakePoint.Direction.DOWN)
				return;
			break;
		}
		case LEFT: {
			if (dir == SnakePoint.Direction.RIGHT)
				return;
			break;
		}
		case RIGHT: {
			if (dir == SnakePoint.Direction.LEFT)
				return;
			break;
		}
		case DOWN: {
			if (dir == SnakePoint.Direction.UP)
				return;
			break;
		}
		default: return;
		}
		
		if (next_direction == null) next_direction = d;
		else queued_next_direction = d;
	}
	
	public SnakePoint[][] getPoints() {
		return points;
	}
	
}
