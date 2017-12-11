package authoring.fsm;

import com.google.gson.annotations.Expose;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class SavedStateRender {

    @Expose List<String> arrowCode;
    @Expose Double xRect;
    @Expose Double yRect;

    public SavedStateRender(List<Arrow> myLeavingTransitions, Rectangle myRender) {
        arrowCode = new ArrayList<>();
        for(Arrow arrow: myLeavingTransitions) {
            arrowCode.add(arrow.getMyCode());
        }
        xRect = myRender.getX();
        yRect = myRender.getY();
    }

}
