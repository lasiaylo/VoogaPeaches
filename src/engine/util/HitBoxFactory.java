package engine.util;

import javafx.scene.shape.Shape;
import util.math.num.Vector;

import java.util.List;

/**
 * Creates HitBoxes and gives it to the Manager and a Script
 * @author lasia
 * @author Albert
 *
 */
public class HitBoxFactory {
    /**
     * Creates a new HitBoxFactory
     */
    public HitBoxFactory() {

    }

    /**
     * Creates a new HitBox based on parameters passed in.
     * @param shapes    List of shapes to be used in hitbox
     * @param center    Center of HitBox
     * @return          Correctly placed HitBox
     */
    public HitBox getHitBox(List<Shape> shapes, Vector center) {
//        Vector fxCoord = FXProcessing.getFXCoord(center, width, height);
//
//        for(Shape s : shapes) {
//            Vector pos = new Vector(s.getLayoutX(), s.getLayoutY());
//            Vector newPos = pos.subtract(center).add(fxCoord);
//        }

        return new HitBox(shapes, center);
    }
}
