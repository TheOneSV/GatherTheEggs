package framework;

public class DeltaTime {
	private long prevTime, currTime, lDelta;
	private float fDelta;

	public void initiliazeTime() {
		prevTime = System.currentTimeMillis();
	}

	public void updateTime() {
		currTime = System.currentTimeMillis();
		lDelta = currTime - prevTime;
		prevTime = currTime;
		fDelta = (float) lDelta / 1000;
	}

	public long getLongDelta() {
		return lDelta;
	}

	public float getFloatDelta() {
		return fDelta;
	}
}
