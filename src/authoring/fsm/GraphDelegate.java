package authoring.fsm;

/**
 * The point of this interface is to hide a lot of Graph functionality from the Arrow and StateRender visualizations.
 * The FSMGraph is the only class that currently implements this interface. Since Arrows and StateRenders handle their
 * own deletion, there needs to be a way for the overall graph to know about the deletion of an arrow, so the FSMGraph
 * can remove any necessary links to States. This graph delegate allows the Arrows and StateRenders to do that without
 * allowing them information to all the other states and transitions.
 *
 * @author Simran
 */
public interface GraphDelegate {

    /**
     * @param state The state that is being deleted
     */
    void removeMyself(StateRender state);

    /**
     * @param arrow The arrow that is being deleted
     */
    void removeMyself(Arrow arrow);

    /**
     * On recreation of the StateRender from JSON, the list of Arrows leaving the state needs to be recreated. Since
     * the SavedStateRender has stored the string representation of the code for all its arrows before saving, this
     * method is used to find the same arrows after recreating the objects.
     *
     * @param code The code that an arrow has
     * @return The arrow that has the code
     */
    Arrow findArrowWith(String code);

    /**
     * Similar to findArrowWith, the Arrow needs to know which StateRenders it originated and arrives on recreation.
     *
     * @param name Name of the state
     * @return The StateRender object with the specific name
     */
    StateRender findStateRenderWith(String name);
}
