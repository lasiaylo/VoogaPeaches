package authoring.panels.tabbable.fsm;

import java.util.ArrayList;
import java.util.List;

public class FSMGraph {
    private List<StateRender> myStateRenders;
    public FSMGraph() {
        this(new ArrayList<StateRender>());
    }

    public FSMGraph(List<StateRender> stateRenders) {
        myStateRenders = stateRenders;
    }
}
