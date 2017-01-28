package application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import framework.DeltaTime;
import framework.GameOptions;

public class Main extends Application {
	private Timeline timeLine;
	private GameOptions gameOptions;
	private World gameWorld;
	private DeltaTime deltaTime = new DeltaTime();
	private Bunny player;
	private StackPane root;

	public static enum GameState {
		PAUSE, INIT, READY, PLAYING, GAMEOVER, AWAKE
	};

	private GameState currGameState = GameState.AWAKE;
	private GameState prevGameState = GameState.AWAKE;

	@Override
	public void start(Stage stage) {

		gameOptions = new GameOptions("GatherTheEggs", 800, 600);
		root = new StackPane();

		gameWorld = new World(this);

		// Background Canvas
		final Canvas background = new Canvas(gameOptions.getWidth(),
				gameOptions.getHeight());
		Image backgroundImage = new Image("data/adam.jpg",
				gameOptions.getWidth(), gameOptions.getHeight(), false, false);
		GraphicsContext context = background.getGraphicsContext2D();
		context.drawImage(backgroundImage, 0, 0);
		root.getChildren().add(background);

		// Update Canvas
		final Canvas canvas = new Canvas(gameOptions.getWidth(),
				gameOptions.getHeight());
		final GraphicsContext gc = canvas.getGraphicsContext2D();

		deltaTime.initiliazeTime();
		timeLine = new Timeline();
		timeLine.setCycleCount(Animation.INDEFINITE);
		timeLine.getKeyFrames().add(
				new KeyFrame(Duration.millis(30),
						new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent t) {

								switch (currGameState) {
								case AWAKE:
									changeGameState(GameState.READY);
									break;
								case INIT:
									gameWorld.initGame();
									player = gameWorld.getBunny();
									gc.clearRect(0, 0, canvas.getWidth(),
											canvas.getHeight());
									changeGameState(GameState.PLAYING);
									break;
								case READY:
									gc.clearRect(0, 0, canvas.getWidth(),
											canvas.getHeight());
									
									gameWorld.drawText(gc,
											"Choose a difficulty:\nPress 1 for Easy\nPress 2 for Normal\nPress3 for Hard",
											240,
											gameOptions.getHalfHeight() - 100, 40,
											Color.WHITE);
									changeGameState(GameState.PAUSE);
									break;
								case PAUSE:
									break;
								case PLAYING:
									gc.clearRect(0, 0, canvas.getWidth(),
											canvas.getHeight());
									deltaTime.updateTime();
									gameWorld.update();
									gameWorld.render(gc);
									break;
								case GAMEOVER:
									gc.clearRect(0, 0, canvas.getWidth(),
											canvas.getHeight());
									gameWorld
											.drawText(
													gc,
													"\tGame Over\n press space to exit",
													gameOptions.getHalfWidth() - 180,
													gameOptions.getHalfHeight(),
													40, Color.WHITE);
									changeGameState(GameState.PAUSE);
									break;
								default:
									break;

								}

							}
						}, new KeyValue[0]));

		timeLine.playFromStart();
		root.getChildren().add(canvas);

		Scene scene = new Scene(root, gameOptions.getWidth(),
				gameOptions.getHeight());
		handleSceneKeys(scene);
		stage.setTitle(gameOptions.getTitle());
		stage.setScene(scene);
		stage.show();

	}

	public void changeGameState(GameState newState) {
		prevGameState = currGameState;
		currGameState = newState;
	}

	public GameState getCurrGameState() {
		return currGameState;
	}

	public GameState getPrevGameState() {
		return prevGameState;
	}

	public StackPane getRootStackPane() {
		return root;
	}

	private void handleSceneKeys(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {

				switch (currGameState) {
				case PAUSE:
					if (getPrevGameState() == GameState.GAMEOVER && event.getCode() == KeyCode.SPACE) {
						changeGameState(GameState.READY);
					} else {
						switch(event.getCode()) {
						case DIGIT1:
							gameOptions.setSpawnTime(2);
							changeGameState(GameState.INIT);
							break;
						case DIGIT2:
							gameOptions.setSpawnTime(1.2f);
							changeGameState(GameState.INIT);
							break;
						case DIGIT3:
							gameOptions.setSpawnTime(0.6f);
							changeGameState(GameState.INIT);
							break;
						default:
							break;
						}
					}
					break;
					
				case PLAYING: {

					switch (event.getCode()) {
					case RIGHT:
						player.shootRight();
						break;
					case LEFT:
						player.shootLeft();
						break;
					case UP:
						player.shootUp();
						break;
					default:
						break;
					}

				}
					break;
				default:
					break;
				}

			}
		});
	}

	public DeltaTime getDeltaTime() {
		return this.deltaTime;
	}

	public GameOptions getGameOptions() {
		return this.gameOptions;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
