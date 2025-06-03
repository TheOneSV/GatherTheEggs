package application;

import Physics.Circle;
import Physics.Collisions;
import framework.PathManager.Target;
import framework.Vector2f;

public class Egg {
	public static enum Color {
		BLUE, RED, GREEN, YELLOW
	};

	public final static float size = 60;
	public boolean isEnabled = false, isVisible = false;
	public Circle circleCollider;
	public Target prevTarget, nextTarget;
	public Color color;

	public Egg() {
		circleCollider = new Circle(new Vector2f(0, 0), size / 2);
	}

	public Egg(Vector2f position, Color color) {
		circleCollider.cpyPosition(position);
		this.color = color;
	}

	public void pushNext(Egg nextEgg) {
		if (isEnabled
				&& Collisions.circleVsCircle(circleCollider,
						nextEgg.circleCollider)) {

			float length = Collisions.circleVsCircleIntersectionLength(
					circleCollider, nextEgg.circleCollider);

			if (length >= 0) {
				nextEgg.moveEgg(length);
			}
		}
	}

	public void moveEgg(float displacement) {
		if (nextTarget != null) {

			if (displacement > 5) {
				deltaMoving(displacement);
			} else {
				offset(displacement);

				if (Collisions.pointVsCircle(circleCollider.getPosition2f(),
						nextTarget.getCircleColliderRef())) {

					float length = Collisions.circleVsCircleMinusAIntersection(
							circleCollider, nextTarget.getCircleColliderRef());

					prevTarget = nextTarget;
					nextTarget = nextTarget.getNextTargetRef();

					circleCollider.setPosition(prevTarget.getPosition().cpy());
					offset(length);
				}
			}
		}
	}

	public void deltaMoving(float displacement) {

		float deltaDisp = 5, accomulator = 0;
		while (accomulator < displacement) {
			
			if(nextTarget == null) {
				break;
			}
			
			accomulator += deltaDisp;
			
			if (accomulator > displacement) {
				accomulator = displacement;
			}
			
			offset(deltaDisp);
			

			if (Collisions.pointVsCircle(circleCollider.getPosition2f(),
					nextTarget.getCircleColliderRef())) {

				float length = Collisions.circleVsCircleMinusAIntersection(
						circleCollider, nextTarget.getCircleColliderRef());

				prevTarget = nextTarget;
				nextTarget = nextTarget.getNextTargetRef();

				circleCollider.setPosition(prevTarget.getPosition().cpy());
				offset(length);
			}
		}
	}

	private void offset(float displacement) {
		Vector2f normal = prevTarget.getNormalToNext();
		normal.multByScalar(displacement);
		circleCollider.offset(normal);
	}

	public Vector2f getPosition2f() {
		return circleCollider.getPosition2f();
	}

	public void setPosition2f(Vector2f position) {
		circleCollider.cpyPosition(position);
	}
}
