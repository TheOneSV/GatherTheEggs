package framework;

import javafx.scene.image.Image;

public class Assets {
	
	private Assets() {
	}
	
	public static final float smallEggSize = 20;
	public static final float mediumEggSize = 40;
	
	public static final Image tinyBlueEgg = new Image("data/smallEggs/eggBlue.png", smallEggSize, smallEggSize, false, false),
			tinyGreenEgg = new Image("data/smallEggs/eggGreen.png", smallEggSize, smallEggSize, false, false),
			tinyRedEgg = new Image("data/smallEggs/eggRed.png", smallEggSize, smallEggSize, false, false),
			tinyYellowEgg = new Image("data/smallEggs/eggYellow.png", smallEggSize, smallEggSize, false, false);
	
	public static final Image mediumBlueEgg = new Image("data/eggBlue.png", mediumEggSize, mediumEggSize, false, false),
			mediumGreenEgg = new Image("data/eggGreen.png", mediumEggSize, mediumEggSize, false, false),
			mediumRedEgg = new Image("data/eggRed.png", mediumEggSize, mediumEggSize, false, false),
			mediumYellowEgg = new Image("data/eggYellow.png", mediumEggSize, mediumEggSize, false, false);
}
