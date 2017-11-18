package authoring.fsm;

import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class FSMGraph {
    private List<StateRender> myStateRenders;
    private List<TransitionRender> myTransitionRenders;
    public FSMGraph() {
        this(new ArrayList<StateRender>(), new ArrayList<TransitionRender>());
    }

    public FSMGraph(List<StateRender> stateRenders, List<TransitionRender> transitionRenders) {
        myStateRenders = stateRenders;
        myTransitionRenders = transitionRenders;
    }

    private void dragDetect(DragEvent event, Shape source) {
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);
    }
}
