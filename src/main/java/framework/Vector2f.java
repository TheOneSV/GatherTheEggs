package framework;

public class Vector2f {
	public float x, y;
	
	public Vector2f() {
		this(0.0f, 0.0f);
	}
	
	public Vector2f(Vector2f position) {
		x = position.x;
		y = position.y;
	}
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
        public float getDistance(Vector2f vecTo) {
                double xDist = vecTo.x - x;
                double yDist = vecTo.y - y;

                return (float) Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
        }
	
	public Vector2f cpy() {
		return new Vector2f(x, y);
	}
	
	public Vector2f getNormal() {
		float length = (float) Math.sqrt(x*x + y*y);
		
		return new Vector2f(x/length, y/length);
	}
	
	public void multByScalar(float scalar) {
		x *= scalar;
		y *= scalar;
	}
	
	public void addScalar(float scalar) {
		x += scalar;
		y += scalar;
	}
	
	public void add(Vector2f vecToAdd) {
		x += vecToAdd.x;
		y += vecToAdd.y;
	}
	
	public void cpy(Vector2f vec) {
		x = vec.x;
		y = vec.y;
	}
	
	public void substractVector(Vector2f vector) {
		x -= vector.x;
		y -= vector.y;
	}
	
	public void rotate(float degrees) {
		float ca = (float) Math.cos(Math.toRadians(degrees));
        float sa = (float) Math.sin(Math.toRadians(degrees));
        
        float newX = ca*x - sa*y;
        float newY = sa*x + ca*y;
        
        x = newX;
        y = newY;
	}
	
	@Override
	public String toString() {
		return "Vector2f [x=" + x + ", y=" + y + "]";
	}
}
