package engine.collisions;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import javafx.scene.shape.Polygon;
import util.math.num.Vector;


import java.util.List;

/**
 * This class defines the HitBox for the collision scripts, and it
 * provides methods for adjusting the polygon that will be used to
 * define an objects HitBox
 *
 * @author Walker
 */
public class HitBox extends TrackableObject {

    /* Variables for Trackable Object */
    @Expose private List<Vector> polygonVertexTranslations;
    private HitBox(){}

    /* Regular Instance Variables not Stored */
    private Polygon hitboxShape;

    /**
     * Creates a new HitBox that possesses the given polygon
     * vertex offsets from the center of the specified entity
     * @param offsetPoints is a {@code List<Vector>} that represents
     *                     the offset of each vertex of the hitbox
     *                     polygon from the center of its corresponding
     *                     entity object
     * @param centerOfEntity is a {@code Vector} that holds the
     *                       initial coordinates of the entity object
     *                       that the HitBox is being created for
     */
    public HitBox(List<Vector> offsetPoints, Vector centerOfEntity){
        // Add the offset points to the polygonVertexTranslation List
        polygonVertexTranslations = offsetPoints;
        // Create points for initial polygon
        double[] polygonPoints = createAdjustedPoints(centerOfEntity);
        // Create polygon from offsets
        hitboxShape = new Polygon(polygonPoints);
    }

    /**
     * Creates the array of polygon points for the hitbox that represent
     * the translations of the polygon added to the center provided
     * @param centerOfEntity is a {@code Vector} that represents the center
     *                       of the {@code Entity} that the hitbox corresponds
     *                       to
     * @return A {@code double[]} of the adjusted points for the polygon
     */
    private double[] createAdjustedPoints(Vector centerOfEntity) {
        double[] adjustedPoints = new double[polygonVertexTranslations.size()];
        for(int i = 0; i < polygonVertexTranslations.size(); i += 2) {
            adjustedPoints[i] = polygonVertexTranslations.get(i).at(0) + centerOfEntity.at(0);
            adjustedPoints[i + 1] = polygonVertexTranslations.get(i).at(1) + centerOfEntity.at(1);
        }
        return adjustedPoints;
    }

    /**
     * Moves the HitBox's polygon with the center of its entity
     * @param currentEntityCenter is a {@code Vector} representing the
     *                            center of the entity that the polygon
     *                            is moving with
     */
    public void moveHitBox(Vector currentEntityCenter) {
        double[] polygonPoints = createAdjustedPoints(currentEntityCenter);
        for(int i = 0; i < polygonPoints.length; i += 2) {
            hitboxShape.getPoints().set(i, polygonPoints[i]);
            hitboxShape.getPoints().set(i + 1, polygonPoints[i + 1]);
        }
    }

    /**
     * @return {@code Polygon} corresponding to the HitBox
     */
    public Polygon getHitbox() {
        return hitboxShape;
    }

    @Override
    public void initialize() {

    }
}
