package authoring.fsm;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import util.math.num.Vector;

/**
 * A data storage class to store information about arrows since Vectors cannot be stored by the database. This class
 * is knowingly ugly, but the information needs to be stored and I chose to store the individual x and y values because
 * you need to expose doubles and strings. Setters and Getters are set for each of these values to recreate the arrow.
 *
 * @author Simran
 */
public class SavedArrow extends TrackableObject {

    @Expose private String myOriginal;
    @Expose private String myDestination;
    @Expose private Double originX;
    @Expose private Double originY;
    @Expose private Double headX;
    @Expose private Double headY;
    @Expose private Double lenX;
    @Expose private Double lenY;

    /**
     * @param original The staterender where the arrow being saved started
     * @param destination The staterender where the arrow being saved ends
     * @param myOrigin The vector representing the original point of the arrow
     * @param myHead The vector representing the head of the arrow
     * @param myLength The length of the vector
     */
    SavedArrow(StateRender original, StateRender destination, Vector myOrigin, Vector myHead, Vector myLength) {
        myOriginal = original.getName();
        myDestination = destination.getName();
        originX = myOrigin.at(0) + 1e-6;
        originY = myOrigin.at(1) + 1e-6;
        headX = myHead.at(0) + 1e-6;
        headY = myHead.at(1) + 1e-6;
        lenX = myLength.at(0) + 1e-6;
        lenY = myLength.at(1) + 1e-6;
    }

    private SavedArrow() {}

    String getMyDestination() {
        return myDestination;
    }

    String getMyOriginal() { return myOriginal; }

    Double getHeadX() { return headX; }

    Double getHeadY() { return headY; }

    Double getLenX() { return lenX; }

    Double getLenY() { return lenY; }

    Double getOriginX() { return originX; }

    Double getOriginY() { return originY; }

    @Override
    public void initialize() { }
}
