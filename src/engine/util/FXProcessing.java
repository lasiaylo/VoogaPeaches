package engine.util;

import javafx.scene.image.ImageView;
import util.math.num.Vector;

/**
 * A Class that translates Game Coordinates to JavaFX coordinates and back.
 * @author Albert
 * @author estellehe
 */
public class FXProcessing {
    /**
     *
     * @param click click location
     * @param gridS size of grid
     * @return      center of background image
     */
    public static Vector getBGCenter(Vector click, int gridS) {
        double extraX = click.at(0) % gridS;
        double xCount = (click.at(0) - extraX) / gridS;
        double extraY = click.at(1) % gridS;
        double yCount = (click.at(1) - extraY) / gridS;
        return new Vector(xCount * gridS, yCount * gridS);
    }
}
