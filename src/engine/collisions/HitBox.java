package engine.collisions;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import javafx.scene.shape.Polygon;

import java.util.List;

/**
 * This class defines the HitBox for the collision scripts, and it
 * provides methods for adjusting the polygon that will be used to
 * define an objects HitBox
 *
 * @author Walker
 * @author Albert
 */
public class HitBox extends TrackableObject {

    /* Variables for Trackable Object */
    @Expose private List<Double> polygonVertexTranslations;
    @Expose private double currentX;
    @Expose private double currentY;

    private HitBox(){}

    /* Regular Instance Variables not Stored */
    private Polygon hitboxShape;

    /**
     * Creates a new HitBox that possesses the given polygon
     * vertex offsets from the center of the specified entity
     * @param offsetPoints is a {@code List<Double>} that represents
     *                     the offset of each vertex of the hitbox
     *                     polygon from the center of its corresponding
     *                     entity object
     * @param entityXPosition is a {@code Double} that holds the
     *                       initial x coordinate of the entity object
     *                       that the HitBox is being created for
     * @param entityYPosition is a {@code Double} that holds the
     *                       initial y coordinate of the entity object
     *                       that the HitBox is being created for
     */
    public HitBox(List<Double> offsetPoints, Double entityXPosition, Double entityYPosition){
        currentX = entityXPosition;
        currentY = entityYPosition;
        // Add the offset points to the polygonVertexTranslation List
        polygonVertexTranslations = offsetPoints;
        // Create points for initial polygon
        double[] polygonPoints = createAdjustedPoints(entityXPosition, entityYPosition);
        // Create polygon from offsets
        hitboxShape = new Polygon(polygonPoints);
    }

    /**
     * Creates the array of polygon points for the hitbox that represent
     * the translations of the polygon added to the center provided
     * @param entityXPosition is a {@code Double} that holds the
     *                       initial x coordinate of the entity object
     *                       that the HitBox is being created for
     * @param entityYPosition is a {@code Double} that holds the
     *                       initial y coordinate of the entity object
     *                       that the HitBox is being created for
     * @return A {@code double[]} of the adjusted points for the polygon
     */
    private double[] createAdjustedPoints(Double entityXPosition, Double entityYPosition) {
        double[] adjustedPoints = new double[polygonVertexTranslations.size()];
        for(int i = 0; i < polygonVertexTranslations.size(); i += 2) {
            adjustedPoints[i] = polygonVertexTranslations.get(i) + entityXPosition;
            adjustedPoints[i + 1] = polygonVertexTranslations.get(i + 1) + entityYPosition;
        }
        return adjustedPoints;
    }

    /**
     * Moves the HitBox's polygon with the center of its entity
     * @param entityXPosition is a {@code Double} that holds the
     *                       initial x coordinate of the entity object
     *                       that the HitBox is being created for
     * @param entityYPosition is a {@code Double} that holds the
     *                       initial y coordinate of the entity object
     *                       that the HitBox is being created for
     */
    public void moveHitBox(Double entityXPosition, Double entityYPosition) {
        currentX = entityXPosition;
        currentY = entityYPosition;
        double[] polygonPoints = createAdjustedPoints(entityXPosition, entityYPosition);
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
        // Create points for initial polygon
        double[] polygonPoints = createAdjustedPoints(currentX, currentY);
        // Create polygon from offsets
        hitboxShape = new Polygon(polygonPoints);
    }
}
