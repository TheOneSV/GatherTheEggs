package Physics;

import framework.Vector2f;

public class Collisions {
	
	public static boolean rectVsRect(Rectangle a, Rectangle b ) {
	  // Exit with no intersection if found separated along an axis
	  if(a.max.x < b.min.x || a.min.x > b.max.x) {
		  return false;
	  }
	  
	  if(a.max.y < b.min.y || a.min.y > b.max.y) {
		  return false;
	  }
	 
	  return true;
	}
	
	
	public static boolean circleVsCircle(Circle a, Circle b) {
		float r = a.radius + b.radius;
		r *= r;
		return r > ( Math.pow(a.getX() - b.getX(), 2.0f) + Math.pow(a.getY() - b.getY(), 2.0f));
	}
	
	
	public static float circleVsCircleIntersectionLength(Circle a, Circle b) {
		
		float normalCentersDistance = a.radius + b.radius;
		float realCentersDistance =  (float) Math.sqrt((Math.pow(a.getX() - b.getX(), 2.0f) + Math.pow(a.getY() - b.getY(), 2.0f)));
		
		return normalCentersDistance - realCentersDistance;
	}
	
	public static float circleVsCircleMinusAIntersection(Circle a, Circle b) {
		float realCentersDistance =  (float) Math.sqrt((Math.pow(a.getX() - b.getX(), 2.0f) + Math.pow(a.getY() - b.getY(), 2.0f)));
		
		return  realCentersDistance - b.radius;
	}
	
	public static boolean pointVsCircle(Vector2f point, Circle c) {

		float realCentersDistance =  (float) Math.sqrt((Math.pow(point.x - c.getX(), 2.0f) + Math.pow(point.y - c.getY(), 2.0f)));
		
		return c.radius > realCentersDistance;
	}
}
