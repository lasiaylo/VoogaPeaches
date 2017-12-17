package engine.util;

import engine.collisions.HitBox;
import engine.entities.Entity;
import engine.events.CollisionEvent;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.util.Map;

/**
 * A class that checks collisions and fires the appropriate events
 * @author Albert
 */
public class CollisionCheck {
    /**
     * Checks collisions and fires collision events on mapped entities if their hitboxes collide
     * @param hitBoxes  map of hitboxes to entity
     */
    public static void checkCollisions(Map<HitBox, Entity> hitBoxes) {
        for(HitBox hitBox : hitBoxes.keySet()) {
            Polygon poly1 = new Polygon();
            poly1.getPoints().addAll(hitBox.getHitbox().getPoints());
            for(HitBox other : hitBoxes.keySet()) {
                if(hitBox != other) {
                    Polygon poly2 = new Polygon();
                    poly2.getPoints().addAll(other.getHitbox().getPoints());
                    Shape intersect = Shape.intersect(poly1,poly2);

                    if (intersect.getBoundsInLocal().getWidth() != -1){
                        new CollisionEvent(hitBox, hitBoxes.get(hitBox)).fire(hitBoxes.get(other));
                        new CollisionEvent(other, hitBoxes.get(other)).fire(hitBoxes.get(hitBox));
                    }
                }
            }
        }
    }
}
