package authoring.fsm;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import engine.fsm.State;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.*;


/**
 * Class to represent the StateRender visualization.
 *
 * @author Simran
 * @author Albert
 */
public class StateRender extends TrackableObject {

    private static final double PADDING = 30;
    private static final Color ERROR = Color.PALEVIOLETRED;

    @Expose private String myTitle;
    @Expose private Map<String, Object> myInfo;
    @Expose private SavedStateRender mySave;
    private List<Arrow> myLeavingTransitions = new ArrayList<>();
    private Rectangle myRender = new Rectangle();
    private GraphDelegate myGraph;
    private boolean deleting;
    private FlowPane flow;
    private Label myLabel;

    /**
     * @param X Initial X position of click
     * @param Y Initial Y position of click
     * @param title Name of the state
     * @param graph The Graph object that holds all the information about other states and arrows
     */
    StateRender(Double X, Double Y, String title, GraphDelegate graph) {
        myInfo = new HashMap<>();
        myTitle = title;
        myGraph = graph;
        initRender(X, Y);
    }

    private StateRender() {}

    /**
     * @return Name of the state
     */
    public String getName() {
        return myLabel.getText();
    }

    /**
     * Creates a new Data Storage object used to store information about the JavaFX objects and transitions to recreate
     * the visualization
     */
    public void save() {
        mySave = new SavedStateRender(myLeavingTransitions, myRender);
    }

    /**
     * Creates rectangle visual of state at given x and y
     *
     * @param X Initial X point
     * @param Y Initial Y point
     */
    private void initRender(Double X, Double Y) {
        myLabel = new Label(myTitle);
        myRender.setFill(ERROR);
        myRender.setX(X);
        myRender.setY(Y);
        myRender.setWidth(PADDING);
        myRender.setHeight(PADDING);
        myRender.setOnMouseClicked(e -> onClick());
    }

    /**
     * @param color Color that the visual is changed to
     */
    protected void setFill(Color color) {
        myRender.setFill(color);
    }

    /**
     * When recreating the FSMGraph from the JSON representation of the graph, the individual arrow
     * and state classes are created first and then the actual FSMGraph object is recreated. This
     * method is called by the FSMGraph to get all the information from its components and reset
     * all the links between arrows and states, as well as initialize the StateRender.
     *
     * @param graph The Graph that holds all information about other states and arrows.
     */
    void setGraphDelegate(GraphDelegate graph) {
        myGraph = graph;
        initRender(mySave.getXRect(), mySave.getYRect());
        for(String code: mySave.getArrowCode()) {
            myLeavingTransitions.add(myGraph.findArrowWith(code));
        }
    }

    /**
     * Handle creating a popup on the click of a state
     */
    void onClick() {
        if (deleting) { return; }
        deleting = true;
        Stage stage = new StateRenderPopup(myGraph, this, myLabel).getStage();
        stage.setOnHidden(e -> deleting = false);
        stage.show();
    }

    /**
     * @return Group that contains all visual elements for the StateRender
     */
    Shape getRender() {
        return myRender;
    }

    /**
     * @param arrow The arrow that starts at this state
     */
    void addLeavingTransition(Arrow arrow) {
        myLeavingTransitions.add(arrow);
    }

    /**
     * @param arrow The arrow that used to start at this state that is now being deleted
     */
    void removeLeavingTransition(Arrow arrow) {
        myLeavingTransitions.remove(arrow);
    }

    /**
     * @return The list of arrows that start at this render
     */
    List<Arrow> getMyLeavingTransitions() {
        return myLeavingTransitions;
    }

    /**
     * @param info The information from the popup
     */
    void setMyInfo(Map<String, Object> info) { myInfo = info; }

    /**
     * @return The new State object created by the relevant properties and transitions input by the user
     */
    State createNewState() {
        Map<String, Map<String, Object>> data = new LinkedHashMap<>();
        data.put("properties", myInfo);
        Map<String, Object> transitions = new LinkedHashMap<>();
        for(Arrow arrow: myLeavingTransitions) {
            transitions.put(arrow.getDestination().getName(), arrow.getMyCode());
        }
        data.put("transitions", transitions);
        return new State(getName(), data);
    }

    @Override
    public void initialize() { }

}
