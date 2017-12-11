package authoring.fsm;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import javafx.scene.Group;

import java.util.List;

public class GraphDelegate extends TrackableObject {

    @Expose private List<StateRender> myStateRenders;
    @Expose private List<Arrow> myArrows;
    private Group myGroup = new Group();

    public void removeMyself(StateRender state){
        myStateRenders.remove(state);
        myGroup.getChildren().remove(state.getRender());
        for(Arrow arrow:state.getMyLeavingTransitions()){
            removeMyself(arrow);
        }
    }

    public void removeMyself(Arrow arrow){
        myArrows.remove(arrow);
        myGroup.getChildren().remove(arrow.getRender());
        for(StateRender state:myStateRenders){
            if(state.getMyLeavingTransitions().contains(arrow)){
                state.removeLeavingTransition(arrow);
            }
        }
    }

    @Override
    public void initialize() {

    }
}
