package engine.collisions;

import com.google.gson.annotations.Expose;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import util.math.num.Vector;

import java.util.Map;
import java.util.Set;

/**
 * @author Albert
 */
public class HitBox {
    @Expose private Vector myPosition;
    @Expose private String myName;
    private Map<Shape, Vector> myShapeOffsets;
    public HitBox(Vector position, Map<Shape, Vector> shapeOffsets, String name) {
        myPosition = position;
        myShapeOffsets = shapeOffsets;
//        myShapeOffsets.keySet().forEach(e -> e.setFill(Color.TRANSPARENT));
        myShapeOffsets.keySet().forEach(e -> calculateOffset(e, myShapeOffsets.get(e)));
        myName = name;
    }

    private void calculateOffset(Shape shape, Vector offset) {
        Vector shapePosition = myPosition.add(offset);
        shape.relocate(shapePosition.at(0), shapePosition.at(1));
    }

    public boolean intersects(HitBox other) {
        Set<Shape> otherShapes = other.getShapes();
        Set<Shape> myShapes = getShapes();
        for(Shape mineEach : myShapes) {
            for(Shape otherEach : otherShapes) {
                if(mineEach.intersects(otherEach.getBoundsInLocal())) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getName() {
        return myName;
    }

    public Set<Shape> getShapes() {
        return myShapeOffsets.keySet();
    }

    public void move(Vector position) {
        myPosition = position;
        myShapeOffsets.keySet().forEach(e -> calculateOffset(e, myShapeOffsets.get(e)));
    }
}
