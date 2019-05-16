
public class SnakePoint {
	
	public enum PointType {
		EMPTY, SNAKE, BARRIER, FOOD
	}
	
	public enum Direction {
		UP, LEFT, RIGHT, DOWN
	}
	
	private PointType point_type;	//mutable
	private Direction direction;	//mutable
	
	public SnakePoint(PointType pt, Direction dir) {
		point_type = pt;
		direction = dir;
	}

	public Direction getDirection() {
		return direction;
	}
	
	public PointType getPointType() {
		return point_type;
	}
	
	public void setDirection(Direction d) {
		direction = d;
	}
	
	public void setPointType(PointType pt) {
		point_type = pt;
	}
	
}
