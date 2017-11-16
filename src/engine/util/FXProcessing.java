package engine.util;

import javafx.scene.image.ImageView;
import util.math.num.Vector;

/**
 * A Class that translates Game Coordinates to JavaFX coordinates and back.
 * @author Albert
 *
 */
public class FXProcessing {
    public static double getXImageCenter(ImageView image) {
        return image.getX() + image.getBoundsInLocal().getWidth() / 2;
    }

    public static Vector getFXCoord(Vector center, double width, double height) {
        Vector translate = new Vector(width / 2, height / 2);
        return center.subtract(translate);
    }

    public static double getYImageCenter(ImageView image) {
        return image.getY() + image.getBoundsInLocal().getHeight() / 2;
    }

    public static double getXImageCoord(double xCenter, ImageView image) {
        return xCenter - image.getBoundsInLocal().getWidth() / 2;
    }

    public static double getYImageCoord(double yCenter, ImageView image) {
        return yCenter - image.getBoundsInLocal().getHeight() / 2;
    }
}
