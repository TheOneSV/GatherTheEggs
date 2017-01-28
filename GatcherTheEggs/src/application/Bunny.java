package application;

import java.util.ArrayList;
import java.util.Random;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import application.Egg.Color;
import application.World.GameManager;
import framework.DeltaTime;
import framework.Drawer;
import framework.Vector2f;

public class Bunny extends GameObject {

	private enum ShootingDirection {
		LEFT, RIGHT, UP, NONE
	};

	public static final float shootingSpeed = 0.2f, shootAnimTime = 0.2f;

	private Image[] shootingImages;
	private int currShootImageIndex = 0;

	private final float shootingAngle = 30, rotationTime = 0.1f;
	private GameManager gameManager;
	private float bunnyHalfWidth;
	private float currShootTime = shootingSpeed, currAnimTime = 0;

	private Vector2f leftShootingPoint, rightShootingPoint, upShootingPoint,
			rotatedVector = new Vector2f();
	private Random randGen = new Random();
	private Color[] colorTypes;
	private ArrayList<Integer> colors = new ArrayList<Integer>(3);

	ShootingDirection shooting = ShootingDirection.NONE;

	public Rectangle2D body;
	public Vector2f position = new Vector2f();
	public float rotation = 0.0f;
	public float targetRotation = 0.0f;

	public Bunny(float posX, float posY, float halfWidth, float halfHeight,
			GameManager gameManager) {
		position.x = posX;
		position.y = posY;
		this.gameManager = gameManager;
		bunnyHalfWidth = halfWidth;
		leftShootingPoint = new Vector2f(-40, 0);
		rightShootingPoint = new Vector2f(40, 0);
		upShootingPoint = new Vector2f(0, -50);

		colorTypes = Egg.Color.values();
		colors.add(getNewColor());
		colors.add(getNewColor());
		colors.add(getNewColor());

		/*
		 * leftShootingPoint.substractVector(position);
		 * rightShootingPoint.substractVector(position);
		 * upShootingPoint.substractVector(position);
		 */
	}

	public void setShootingImages(Image[] images) {
		this.shootingImages = images;
	}

	public void update(DeltaTime dt) {
		
		 if (rotation != targetRotation) { 
			 rotation = lerp(rotation,
			 targetRotation, dt.getFloatDelta() / rotationTime); 
		 }
		 

		switch (shooting) {
		case RIGHT:
			shootOnDirection(rightShootingPoint);
			break;
		case LEFT:
			shootOnDirection(leftShootingPoint);
			break;
		case UP:
			shootOnDirection(upShootingPoint);
			break;
		default:
			break;
		}

		if (shooting != ShootingDirection.NONE) {
			shooting = ShootingDirection.NONE;
		}

		UpdateAnimation(dt.getLongDelta());

		if (!canShoot()) {
			if (currAnimTime < shootAnimTime) {
				currAnimTime += dt.getFloatDelta();
			}
			currShootTime -= dt.getFloatDelta();

			if (currShootTime <= 0) {
				currShootTime = 0;
			}
		}
	}

	public void draw(GraphicsContext gc) {

		if (currAnimTime < shootAnimTime && shootingImages != null && shootingImages.length == 3) {
			Drawer.drawRotatedImageWithOffset(gc,
					shootingImages[currShootImageIndex], rotation, position.x
							- animation.getImage().getWidth() / 2, position.y
							- animation.getImage().getHeight() / 2, 0.65f, 0.6f);
		} else {
			Drawer.drawRotatedImageWithOffset(gc, animation.getImage(),
					rotation, position.x - animation.getImage().getWidth() / 2,
					position.y - animation.getImage().getHeight() / 2, 0.65f,
					0.6f);
		}
	}

	private Color EnqueColor() {
		Color color = colorTypes[colors.get(0)];
		colors.remove(0);
		colors.add(getNewColor());

		return color;
	}

	public Color getColor(int index) {
		return colorTypes[colors.get(index)];
	}

	private int getNewColor() {
		int index = randGen.nextInt(colorTypes.length);
		while (colors.contains(index)) {
			index = (index + 1) % colorTypes.length;
		}
		return index;
	}

	private void shootOnDirection(Vector2f direction) {
		rotatedVector.cpy(direction.cpy());
		rotatedVector.rotate(targetRotation);
		gameManager.shootProjectile(new Vector2f(rotatedVector.x + position.x
				+ bunnyHalfWidth, rotatedVector.y + position.y), rotatedVector,
				0.5f, EnqueColor());
		currShootTime = shootingSpeed;
		currAnimTime = 0;
	}

	public void shootRight() {
		if (shooting == ShootingDirection.NONE && canShoot()) {
			 targetRotation = rotation + shootingAngle;
			//rotation += shootingAngle;
			shooting = ShootingDirection.RIGHT;
			currShootImageIndex = 2;
		}
	}

	public void shootLeft() {
		if (shooting == ShootingDirection.NONE && canShoot()) {
			 targetRotation = rotation - shootingAngle;
			// rotation -= shootingAngle;
			shooting = ShootingDirection.LEFT;
			currShootImageIndex = 0;
		}
	}

	public void shootUp() {
		if (shooting == ShootingDirection.NONE && canShoot()) {
			shooting = ShootingDirection.UP;
			currShootImageIndex = 1;
		}
	}

	private float lerp(float from, float to, float dt) {

		if (dt > 1.0f) {
			return to;
		}

		float newValue = to - from;
		if (Math.abs(newValue) < 0.05f) {
			return to;
		}

		return from + newValue * dt;
	}

	public boolean canShoot() {
		return (currShootTime == 0);
	}

	public float getCurrShootingTime() {
		return currShootTime;
	}
}
