package authoring.fsm;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class SavedStateRender extends TrackableObject {

    @Expose private List<String> arrowCode;
    @Expose private Double xRect;
    @Expose private Double yRect;

    SavedStateRender(List<Arrow> myLeavingTransitions, Rectangle myRender) {
        arrowCode = new ArrayList<>();
        for(Arrow arrow: myLeavingTransitions) {
            arrowCode.add(arrow.getMyCode());
        }
        xRect = myRender.getX() + 1e-6;
        yRect = myRender.getY() + 1e-6;
    }


    List<String> getArrowCode() { return arrowCode; }

    Double getXRect() { return xRect; }

    Double getYRect() { return yRect; }

    private SavedStateRender() {}

    @Override
    public void initialize() {
    }
}
