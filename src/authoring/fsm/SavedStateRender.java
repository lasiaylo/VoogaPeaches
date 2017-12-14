package authoring.fsm;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class SavedStateRender extends TrackableObject {

    @Expose List<String> arrowCode;
    @Expose Double xRect;
    @Expose Double yRect;

    public SavedStateRender(List<Arrow> myLeavingTransitions, Rectangle myRender) {
        arrowCode = new ArrayList<>();
        for(Arrow arrow: myLeavingTransitions) {
            arrowCode.add(arrow.getMyCode());
        }
        xRect = myRender.getX() + 1e-6;
        yRect = myRender.getY() + 1e-6;
    }


    public List<String> getArrowCode() { return arrowCode; }

    public Double getXRect() { return xRect; }

    public Double getYRect() { return yRect; }

    private SavedStateRender() {}

    @Override
    public void initialize() {
        System.out.println("init saved state");
    }
}
