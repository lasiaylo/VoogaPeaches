package authoring.fsm;


/**
 * A class that lets an individual Graph Object delete itself from the entire FSMGraph
 * @author Simran
 */
public interface GraphDelegate {

    void removeMyself(StateRender state);
    void removeMyself(TransitionRender transition);

}
