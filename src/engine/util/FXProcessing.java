package engine.util;

import engine.entities.Entity;
import javafx.scene.image.ImageView;
import util.math.num.Vector;

/**
 * A Class that translates Game Coordinates to JavaFX coordinates and back.
 * @author Albert
 *
 */
public class FXProcessing {
    public static double getXImageCenter(ImageView image) {
        return image.getX() + image.getFitWidth() / 2;
    }

    public static Vector getFXCoord(Vector center, double width, double height) {
        Vector translate = new Vector(width / 2, height / 2);
        return center.subtract(translate);
    }

    public static double getYImageCenter(ImageView image) {
        return image.getY() + image.getFitHeight()/ 2;
    }

    public static double getXImageCoord(double xCenter, Entity myEnt) {
        return xCenter - myEnt.getTransform().getSize().at(0)/2;
    }

    public static double getYImageCoord(double yCenter, Entity myEnt) {
        return yCenter - myEnt.getTransform().getSize().at(1)/2;
    }

    public static Vector getBGCenter(Vector click, double gridS) {
        double extraX = click.at(0) % gridS;
        double xCount = (click.at(0) - extraX) / gridS + 0.5;
        double extraY = click.at(1) % gridS;
        double yCount = (click.at(1) - extraY) / gridS + 0.5;
        return new Vector(xCount * gridS, yCount * gridS);
    }
}
