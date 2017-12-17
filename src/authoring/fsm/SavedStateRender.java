package authoring.fsm;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * A Data storage class that is used to store information into the database about the FSMGraph. Since JavaFX objects
 * cannot be stored in the database in their raw form, this class was used to store information about JavaFX rectangles
 * that will allow me to recreate the object.
 *
 * @author Simran
 */
public class SavedStateRender extends TrackableObject {

    @Expose private List<String> arrowCode;
    @Expose private Double xRect;
    @Expose private Double yRect;

    /**
     * @param myLeavingTransitions The list of arrows that start at the state (rectangle)
     * @param myRender The rectangle that visually represents the state
     */
    SavedStateRender(List<Arrow> myLeavingTransitions, Rectangle myRender) {
        arrowCode = new ArrayList<>();
        for(Arrow arrow: myLeavingTransitions) {
            arrowCode.add(arrow.getMyCode());
        }
        xRect = myRender.getX() + 1e-6;
        yRect = myRender.getY() + 1e-6;
    }

    private SavedStateRender() { }

    /**
     * @return List of Strings that represents the code of all the arrows leaving the state
     */
    List<String> getArrowCode() { return arrowCode; }

    /**
     * @return x of the rectangle
     */
    Double getXRect() { return xRect; }

    /**
     * @return y of the rectangle
     */
    Double getYRect() { return yRect; }

    @Override
    public void initialize() { }
}
