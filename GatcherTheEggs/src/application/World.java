package application;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import Physics.Circle;
import Physics.Rectangle;
import framework.Animation;
import framework.Assets;
import framework.DeltaTime;
import framework.Drawer;
import framework.GameOptions;
import framework.PathManager;
import framework.Vector2f;

public class World {
	private DeltaTime deltaTime;
	private GameOptions gameOptions;
	private PathManager pathManager = new PathManager();
	private GameManager gameManager;
	private Main gameStarter;
	private final int MAXLOSTNUMBER = 10;
	private int lostEggs, score;

	private Rectangle eggRect, screenRect;

	private Bunny bunny;
	private EvilKid evilKid;
	private float evilKidSize = 150;

	public World(Main game) {
		gameStarter = game;
		gameOptions = game.getGameOptions();
		deltaTime = game.getDeltaTime();
		eggRect = new Rectangle();
		screenRect = new Rectangle(new Vector2f(0, 0), new Vector2f(
				gameOptions.getWidth(), gameOptions.getHeight()));
	}

	public void initGame() {
		lostEggs = score = 0;

		pathInitializing();
		initializeEvilKid(gameOptions.getWidth() - evilKidSize / 2,
				gameOptions.getHeight() - evilKidSize / 2, evilKidSize);

		gameManager = new GameManager(pathManager);
		gameManager.startSpawning(gameOptions.getSpawnTime());
		gameManager.setEvilKid(evilKid);

		initializeBunny(gameOptions.getWidth() / 2,
				gameOptions.getHeight() / 2, gameManager);
	}

	public void initializeBunny(float posX, float posY, GameManager gameManger) {
		float height = 150, width = 100;
		bunny = new Bunny(posX, posY, width / 2, height / 2, gameManger);

		Image character1 = new Image("data/BattleBunny_idle1.png", width,
				height, false, false);
		Image character2 = new Image("data/BattleBunny_idle2.png", width,
				height, false, false);
		Image character3 = new Image("data/BattleBunny_idle3.png", width,
				height, false, false);

		Animation anim = new Animation();
		anim.addFrame(character1, 1250);
		anim.addFrame(character2, 50);
		anim.addFrame(character3, 50);
		anim.addFrame(character2, 50);

		bunny.setAnimation(anim);

		Image shootLeft = new Image("data/BattleBunny_Left.png", width, height,
				false, false);
		Image shootRight = new Image("data/BattleBunny_Right.png", width,
				height, false, false);
		Image shootUp = new Image("data/BattleBunny_Top2.png", width, height,
				false, false);

		Image[] shootingImages = new Image[] { shootLeft, shootUp, shootRight };
		bunny.setShootingImages(shootingImages);
	}

	public void initializeEvilKid(float posX, float posY, float size) {
		evilKid = new EvilKid(posX, posY, size * 0.4f);

		Image character1 = new Image("data/Cookie1.png", size, size, false,
				false);
		Image character2 = new Image("data/Cookie2.png", size, size, false,
				false);
		Image character3 = new Image("data/Cookie3.png", size, size, false,
				false);

		Animation anim = new Animation();
		anim.addFrame(character1, 400);
		anim.addFrame(character2, 50);
		anim.addFrame(character3, 50);
		anim.addFrame(character2, 50);

		evilKid.setAnimation(anim);
	}

	public void pathInitializing() {
		Vector2f bottomLeft = new Vector2f(0 + Egg.size,
				gameOptions.getHeight() + Egg.size + 50);
		Vector2f topLeft = new Vector2f(bottomLeft.x, 0 + Egg.size);
		Vector2f topRight = new Vector2f(gameOptions.getWidth(), topLeft.y);
		Vector2f bottomRight = new Vector2f(topRight.x, bottomLeft.y);

		pathManager.addTarget(bottomLeft);
		pathManager.addTarget(topLeft);
		pathManager.addTarget(topRight);
		pathManager.addTarget(bottomRight);
	}

	public void update() {
		gameManager.update(deltaTime);
		evilKid.update(deltaTime);
		bunny.update(deltaTime);

	}

	public void render(GraphicsContext gc) {
		gameManager.draw(gc);
		evilKid.draw(gc);
		bunny.draw(gc);

		Drawer.drawRotatedImage(gc,
				getMediumEggImageByColor(bunny.getColor(0)), 0,
				bunny.position.x - 50, bunny.position.y + 120);
		Drawer.drawRotatedImage(gc, getTinyEggImageByColor(bunny.getColor(1)),
				0, bunny.position.x, bunny.position.y + 130);
		Drawer.drawRotatedImage(gc, getTinyEggImageByColor(bunny.getColor(2)),
				0, bunny.position.x + 30, bunny.position.y + 130);

		if (!bunny.canShoot()) {
			drawText(
					gc,
					"Reloading: "
							+ String.format("%.2f", bunny.getCurrShootingTime()),
					gameOptions.getHalfWidth() - 100,
					gameOptions.getHeight() - 80, 30, Color.WHITE);
		}

		drawText(gc, "Score: " + score, gameOptions.getHalfWidth() - 200,
				gameOptions.getHeight() - 20, 30, Color.WHITE);
		drawText(gc, "Lost Eggs: " + lostEggs + "/" + MAXLOSTNUMBER,
				gameOptions.getHalfWidth() + 30, gameOptions.getHeight() - 20,
				30, Color.WHITE);
	}

	private Image getTinyEggImageByColor(Egg.Color color) {
		Image img = null;
		switch (color) {
		case BLUE:
			img = Assets.tinyBlueEgg;
			break;
		case GREEN:
			img = Assets.tinyGreenEgg;
			break;
		case RED:
			img = Assets.tinyRedEgg;
			break;
		case YELLOW:
			img = Assets.tinyYellowEgg;
			break;
		default:
			break;

		}

		return img;
	}

	private Image getMediumEggImageByColor(Egg.Color color) {
		Image img = null;
		switch (color) {
		case BLUE:
			img = Assets.mediumBlueEgg;
			break;
		case GREEN:
			img = Assets.mediumGreenEgg;
			break;
		case RED:
			img = Assets.mediumRedEgg;
			break;
		case YELLOW:
			img = Assets.mediumYellowEgg;
			break;
		default:
			break;

		}

		return img;
	}

	public Bunny getBunny() {
		return bunny;
	}

	public class GameManager {
		private ArrayList<Egg> eggs = new ArrayList<Egg>();
		private ArrayList<EggProjectile> projectiles = new ArrayList<EggProjectile>();

		private Egg.Color[] colors = Egg.Color.values();
		private Random randomGenerator = new Random();
		private boolean isSpawning = false;
		private float spawnTime = 0, timeElapsed = 0, eggSpeed = 0;

		private Image eggBlue = new Image("data/eggBlue.png", Egg.size,
				Egg.size, false, false);
		private Image eggGreen = new Image("data/eggGreen.png", Egg.size,
				Egg.size, false, false);
		private Image eggYellow = new Image("data/eggYellow.png", Egg.size,
				Egg.size, false, false);
		private Image eggRed = new Image("data/eggRed.png", Egg.size, Egg.size,
				false, false);

		private EvilKid evilKid;
		private PathManager pathManager;

		public void draw(GraphicsContext gc) {

			int size = eggs.size();
			Egg ptrEgg;

			Image eggImg = null;
			for (int i = 0; i < size - 1; ++i) {
				ptrEgg = eggs.get(i);
				if (ptrEgg.isEnabled && ptrEgg.isVisible) {
					eggImg = getImageByColor(ptrEgg.color);
					Drawer.drawRotatedImage(gc, eggImg, 0,
							ptrEgg.circleCollider.getX() - eggImg.getWidth(),
							ptrEgg.circleCollider.getY() - eggImg.getWidth());
				}
			}

			EggProjectile eggProjectile;
			for (int i = 0; i < projectiles.size(); ++i) {
				eggProjectile = projectiles.get(i);
				eggImg = getImageByColor(eggProjectile.color);
				Drawer.drawRotatedImage(
						gc,
						eggImg,
						0,
						projectiles.get(i).circleCollider.getX()
								- eggImg.getWidth(),
						projectiles.get(i).circleCollider.getY()
								- eggImg.getWidth());
			}
		}

		public void update(DeltaTime time) {

			if (isSpawning) {
				timeElapsed += time.getFloatDelta();
				if (timeElapsed >= spawnTime) {
					timeElapsed = 0;
					spawn();
				}
			}

			// Update projectiles - this is very bad O^2 but i don`t have more
			// time

			EggProjectile tmpProjectile;
			for (int i = 0; i < projectiles.size(); ++i) {
				tmpProjectile = projectiles.get(i);
				tmpProjectile.update(time);
				
				eggRect.min.x = tmpProjectile.circleCollider.getPosition2f().x - Egg.size;
				eggRect.max.x = tmpProjectile.circleCollider.getPosition2f().x + Egg.size;
				eggRect.min.y = tmpProjectile.circleCollider.getPosition2f().y - Egg.size;
				eggRect.max.y = tmpProjectile.circleCollider.getPosition2f().y + Egg.size;

				// Occlusion culling :D
				if (!Physics.Collisions.rectVsRect(eggRect, screenRect)) {
					projectiles.remove(i);
				} else {
					for (int innerIndex = 0; innerIndex < eggs.size(); ++innerIndex) {
						if (Physics.Collisions.circleVsCircle(
								eggs.get(innerIndex).circleCollider,
								tmpProjectile.circleCollider)) {
							Circle bigCircle = new Circle(
									tmpProjectile.circleCollider.getPosition2f(),
									Egg.size);
	
							if (innerIndex > 0) {
								if (Physics.Collisions.circleVsCircle(
										eggs.get(innerIndex - 1).circleCollider,
										bigCircle)) {
									addEggAtPosition(tmpProjectile.color,
											innerIndex);
									forcePhysicsUpdate(innerIndex);
								}
							} else if (innerIndex + 1 < eggs.size()) {
								addEggAtPosition(tmpProjectile.color,
										innerIndex + 1);
								forcePhysicsUpdate(innerIndex);
							} else {
								addEggAtPosition(tmpProjectile.color, innerIndex);
								forcePhysicsUpdate(innerIndex);
							}
	
							projectiles.remove(i);
							break;
						}
					}
				}
			}

			if (eggs.size() > 0 && eggs.get(eggs.size() - 1).isEnabled) {

				// Moving first Egg
				Egg ptrEgg = eggs.get(eggs.size() - 1);

				Vector2f normal = ptrEgg.prevTarget.getNormalToNext();
				normal.multByScalar(eggSpeed * time.getFloatDelta());
				ptrEgg.circleCollider.offset(normal);

				// Check for collision between EvilKid and the last projectile
				if (Physics.Collisions.circleVsCircle(
						eggs.get(0).circleCollider, evilKid.circleCollider)) {
					eggs.remove(0);
					lostEggs += 1;

					if (lostEggs > MAXLOSTNUMBER) {
						gameStarter.changeGameState(Main.GameState.GAMEOVER);
					}
				}

				// update all of them
				int sizeOfEggs = eggs.size() - 1;
				for (int i = sizeOfEggs; i > 0; --i) {

					ptrEgg = eggs.get(i);
					if (ptrEgg.isEnabled) {

						if (i - 1 > 0) {
							Egg firstEggForCheck = eggs.get(i - 1);
							if (ptrEgg.color == firstEggForCheck.color) {

								if (i - 2 > 0) {
									Egg secondEggForCheck = eggs.get(i - 2);
									if (ptrEgg.color == secondEggForCheck.color) {

										if (Physics.Collisions.circleVsCircle(
												ptrEgg.circleCollider
														.multByScalar(1.5f),
												firstEggForCheck.circleCollider
														.multByScalar(1.5f))
												&& Physics.Collisions
														.circleVsCircle(
																firstEggForCheck.circleCollider
																		.multByScalar(1.5f),
																secondEggForCheck.circleCollider
																		.multByScalar(1.5f))) {

											eggs.remove(i);
											eggs.remove(i - 1);
											eggs.remove(i - 2);

											score += 10;
										}
									}
								}
							}
						}

						eggRect.min.x = ptrEgg.getPosition2f().x - Egg.size;
						eggRect.max.x = ptrEgg.getPosition2f().x + Egg.size;
						eggRect.min.y = ptrEgg.getPosition2f().y - Egg.size;
						eggRect.max.y = ptrEgg.getPosition2f().y + Egg.size;

						// Occlusion culling :D
						if (!Physics.Collisions.rectVsRect(eggRect, screenRect)) {
							ptrEgg.isVisible = false;
						} else {
							ptrEgg.isVisible = true;
						}
						ptrEgg.pushNext(eggs.get(i - 1));
					}
				}
			}

		}

		public void spawn() {

			int colorIndex = randomGenerator.nextInt(colors.length);

			if (eggs.size() > 1) {
				if (eggs.get(eggs.size() - 1).color == colors[colorIndex]
						&& eggs.get(eggs.size() - 2).color == colors[colorIndex]) {
					colorIndex = (colorIndex + 1) % (colors.length - 1);
				}
			}

			addEgg(colors[colorIndex]);
		}

		private void forcePhysicsUpdate(int index) {
			if (Physics.Collisions.circleVsCircle(eggs.get(0).circleCollider,
					evilKid.circleCollider)) {
				eggs.remove(0);
			}
			Egg ptrEgg = null;
			for (; index > 0; --index) {

				ptrEgg = eggs.get(index);
				if (ptrEgg.isEnabled) {

					eggRect.min.x = ptrEgg.getPosition2f().x - Egg.size;
					eggRect.max.x = ptrEgg.getPosition2f().x + Egg.size;
					eggRect.min.y = ptrEgg.getPosition2f().y - Egg.size;
					eggRect.max.y = ptrEgg.getPosition2f().y + Egg.size;

					// Occlusion culling :D
					if (!Physics.Collisions.rectVsRect(eggRect, screenRect)) {
						ptrEgg.isVisible = false;
					} else {
						ptrEgg.isVisible = true;
					}
					ptrEgg.pushNext(eggs.get(index - 1));
				}
			}
		}

		private void addEgg(Egg.Color color) {
			Egg newEgg = new Egg();
			newEgg.setPosition2f(new Vector2f(pathManager.getTarget(0)
					.getPosition().cpy()));
			newEgg.color = color;
			newEgg.prevTarget = pathManager.getTarget(0);
			newEgg.nextTarget = pathManager.getTarget(1);
			newEgg.isEnabled = true;
			eggs.add(newEgg);
		}

		private void addEggAtPosition(Egg.Color color, int index) {
			Egg newEgg = new Egg();
			Egg prevEgg = eggs.get(index);

			newEgg.setPosition2f(prevEgg.getPosition2f().cpy());
			newEgg.color = color;
			newEgg.prevTarget = prevEgg.prevTarget;
			newEgg.nextTarget = prevEgg.nextTarget;
			newEgg.isEnabled = true;
			prevEgg.moveEgg(Egg.size / 8);
			eggs.add(index, newEgg);
		}

		public void setEvilKid(EvilKid evilKid) {
			this.evilKid = evilKid;
		}

		public boolean isSpawning() {
			return isSpawning;
		}

		public void startSpawning(float spawnTime) {
			this.spawnTime = spawnTime;
			eggSpeed = Egg.size / spawnTime;
			isSpawning = true;
		}

		public void stopSpawning() {
			isSpawning = false;
		}

		public void setSpawnSpeed(float newSpawnSpeed) {
			spawnTime = newSpawnSpeed;
		}

		public float getSpawnSpeed() {
			return spawnTime;
		}

		public GameManager(PathManager path) {
			pathManager = path;
		}

		void shootProjectile(Vector2f position, Vector2f direction,
				float speed, Egg.Color color) {
			EggProjectile newProjectile = new EggProjectile(position,
					direction, speed, color);
			projectiles.add(newProjectile);
		}

		private Image getImageByColor(Egg.Color color) {
			Image img = null;
			switch (color) {
			case BLUE:
				img = eggBlue;
				break;
			case GREEN:
				img = eggGreen;
				break;
			case RED:
				img = eggRed;
				break;
			case YELLOW:
				img = eggYellow;
				break;
			default:
				break;
			}
			return img;
		}
	}

	public void drawText(GraphicsContext gc, String text, double x, double y,
			double size, Color color) {
		gc.setFill(color);
		gc.setFont(Font.font(size));
		gc.fillText(text, x, y);
	}
}
