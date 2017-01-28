package framework;

import java.util.ArrayList;

import Physics.Circle;

public class PathManager {

	public static final float targetRadius = 6;
	private ArrayList<Target> targets = new ArrayList<Target>();

	public class Target {
		private Vector2f normalToNext = new Vector2f();
		private Circle circleCollider;
		private Target nextTarget = null;

		Target(Vector2f position) {
			circleCollider = new Circle(position, targetRadius);
		}

		public Vector2f getPosition() {
			return circleCollider.getPosition2f();
		}

		public Vector2f getNormalToNext() {
			return normalToNext.cpy();
		}

		public Circle getCircleColliderRef() {
			return circleCollider;
		}

		public Target getNextTargetRef() {
			return nextTarget;
		}
	}

	public Vector2f getNormalToNextTarget(int index) {
		return targets.get(index).normalToNext;
	}

	public void addTarget(Vector2f targetPosition) {

		Target newTarget = new Target(targetPosition);

		if (targets.size() > 0) {
			Target prevTarget = targets.get(targets.size() - 1);

			Vector2f targetPos = newTarget.circleCollider.getPosition2f();
			Vector2f currPos = prevTarget.circleCollider.getPosition2f();

			Vector2f normalVec = new Vector2f(targetPos.x - currPos.x,
					targetPos.y - currPos.y);
			normalVec = normalVec.getNormal();

			prevTarget.normalToNext = normalVec;
			prevTarget.nextTarget = newTarget;
			System.out.println(prevTarget.circleCollider.getPosition2f()
					+ " --- " + prevTarget.normalToNext);
		}

		targets.add(newTarget);
	}

	public Target getTarget(int index) {
		return targets.get(index);
	}

	public static Vector2f calcNormalFromToTarget(Target target1, Target target2) {

		Vector2f targetPos = target2.circleCollider.getPosition2f();
		Vector2f currPos = target1.circleCollider.getPosition2f();

		Vector2f normalVec = new Vector2f(targetPos.x - currPos.x, targetPos.y
				- currPos.y);
		normalVec = normalVec.getNormal();

		return normalVec;
	}
}