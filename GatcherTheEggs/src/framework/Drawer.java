package framework;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

public final class Drawer {

	private static void rotate(GraphicsContext gc, double angle, double px,
			double py) {
		Rotate r = new Rotate(angle, px, py);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(),
				r.getTx(), r.getTy());
	}

	public static void drawRotatedImage(GraphicsContext gc, Image image,
			double angle, double tlpx, double tlpy) {
		gc.save();
		rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight()
				/ 2);
		gc.drawImage(image, tlpx, tlpy);
		gc.restore();
	}

	public static void drawRotatedImageWithOffset(GraphicsContext gc,
			Image image, double angle, double tlpx, double tlpy, float offsetX,
			float offsetY) {
		gc.save();
		rotate(gc, angle, tlpx + image.getWidth() * offsetX,
				tlpy + image.getHeight() * offsetY);
		gc.drawImage(image, tlpx, tlpy);
		gc.restore();
	}
}
