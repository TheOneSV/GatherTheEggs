package Physics;

import framework.Vector2f;


public class Circle {
	public float radius;
	private Vector2f position;
	
	public Circle(float x, float y, float radius) {
		position = new Vector2f(x, y);
		this.radius = radius;
	}
	
	public Circle(Vector2f position, float radius) {
		this.position = position;
		this.radius = radius;
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
	
	public void setX(float x) {
		position.x = x;
	}
	
	public void setY(float y) {
		position.y = y;
	}
	
	public void setPosition(Vector2f position) {
		this.position = position;
	}
	
	public Vector2f getPosition2f() {
		return position.cpy();
	}
	
	public void offset(Vector2f offset) {
		position.add(offset);
	}
	
	public void cpyPosition(Vector2f position) {
		this.position.cpy(position);
	}
	
	public Circle multByScalar(float scalar) {
		return new Circle(position.cpy(), radius * scalar);
	}
}
