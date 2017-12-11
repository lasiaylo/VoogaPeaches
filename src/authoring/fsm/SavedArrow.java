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

    public SavedArrow(StateRender original, StateRender destination, Vector myOrigin, Vector myHead, Vector myLength) {
        myOriginal = original.getName();
        myDestination = destination.getName();
        originX = myOrigin.x + 1e-6;
        originY = myOrigin.y + 1e-6;
        headX = myHead.x + 1e-6;
        headY = myHead.y + 1e-6;
        lenX = myLength.x + 1e-6;
        lenY = myLength.y + 1e-6;
    }

    private SavedArrow() {}

    public String getMyDestination() {
        return myDestination;
    }

    public String getMyOriginal() { return myOriginal; }

    public Double getHeadX() { return headX; }

    public Double getHeadY() { return headY; }

    public Double getLenX() { return lenX; }

    public Double getLenY() { return lenY; }

    public Double getOriginX() { return originX; }

    public Double getOriginY() { return originY; }

    @Override
    public void initialize() {
        System.out.println("init SavedArrow");
    }
}
