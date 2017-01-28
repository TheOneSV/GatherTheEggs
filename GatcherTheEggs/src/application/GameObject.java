package application;

import framework.Animation;

public class GameObject {
	protected Animation animation;
	
	
	public void setAnimation(Animation animation) {
		this.animation = animation;
	}
	
	public Animation getAnimaton() {
		return animation;
	}
	
	public void UpdateAnimation(long dt) {
		if(animation != null) {
			animation.update(dt);
		}
	}
}
