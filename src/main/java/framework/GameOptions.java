package framework;

public class GameOptions {
	private int width, height;
	private int halfWidth, halfHeight;
	private String title;
	private float spawnTime = 2f;

	public GameOptions() {
		this("Default", 0, 0);
	}

	public GameOptions(String title) {
		this(title, 0, 0);
	}

	public GameOptions(int width, int height) {
		this("Default", width, height);
	}

	public GameOptions(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		halfWidth = width / 2;
		halfHeight = height / 2;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getTitle() {
		return title;
	}

	public float getSpawnTime() {
		return spawnTime;
	}

	public void setSpawnTime(float spawnTime) {
		this.spawnTime = spawnTime;
	}

	public int getHalfHeight() {
		return halfHeight;
	}

	public int getHalfWidth() {
		return halfWidth;
	}
	
}
