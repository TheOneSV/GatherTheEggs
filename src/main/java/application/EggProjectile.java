package application;

import application.Egg.Color;
import Physics.Circle;
import framework.DeltaTime;
import framework.Vector2f;

public class EggProjectile {
	public Circle circleCollider;
	public Vector2f velocity = new Vector2f();
	public Color color;
	
	public EggProjectile(Vector2f position, Vector2f direction, float speed, Color color) {
		circleCollider = new Circle(position, 5);
		
		velocity = direction.getNormal();
		velocity.multByScalar(speed);
		this.color = color;
	}
	
	public void update(DeltaTime dt) {;
		circleCollider.offset(new Vector2f(velocity.x/dt.getFloatDelta(), velocity.y/dt.getFloatDelta()));
	}
}
