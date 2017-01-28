package application;

import javafx.scene.canvas.GraphicsContext;
import Physics.Circle;
import framework.DeltaTime;
import framework.Drawer;
import framework.Vector2f;

public class EvilKid extends GameObject {

	public Circle circleCollider;
	public boolean isEating = false;

	public EvilKid(float x, float y, float radius) {
		this(new Vector2f(x, y), radius);
	}

	public EvilKid(Vector2f position, float radius) {
		circleCollider = new Circle(position, radius);
	}

	public void update(DeltaTime dt) {
		UpdateAnimation(dt.getLongDelta());
	}

	public void draw(GraphicsContext gc) {
		Drawer.drawRotatedImage(gc, animation.getImage(), 0,
				circleCollider.getX() - animation.getImage().getWidth() / 2,
				circleCollider.getY() - animation.getImage().getHeight() / 2);
	}
}
