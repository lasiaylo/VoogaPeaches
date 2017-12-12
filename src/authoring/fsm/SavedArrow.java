package authoring.fsm;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import util.math.num.Vector;

public class SavedArrow extends TrackableObject {

    @Expose private String myOriginal;
    @Expose private String myDestination;
    @Expose private Double originX;
    @Expose private Double originY;
    @Expose private Double headX;
    @Expose private Double headY;
    @Expose private Double lenX;
    @Expose private Double lenY;

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
    public void initialize() {
    }
}
