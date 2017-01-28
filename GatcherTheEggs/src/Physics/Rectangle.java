package Physics;

import framework.Vector2f;

public class Rectangle {
	public Vector2f min;
	public Vector2f max;
	
	public Rectangle() {
		this(new Vector2f(), new Vector2f());
	}
	
	public Rectangle(Vector2f min, Vector2f max){
		this.min = min;
		this.max = max;
	}
	
}
