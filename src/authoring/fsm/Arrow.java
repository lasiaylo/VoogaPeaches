package authoring.fsm;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import javafx.scene.Group;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import util.math.num.Vector;

/**
 * Class to represent the arrow in the FSM visualization. The arrow visualization requires a lot of code to make it
 * look as good as it does. The arrow is made out of three lines, one for the body, and one for each of the heads that
 * make up the arrow. The declared constants set the proportions and angles for the arrow, and the vectors helps in
 * creation of the lines.
 *
 * @author Simran
 * @author Albert
 */
public class Arrow extends TrackableObject {

    private static final double HEAD_OFFSET = 45;
    private static final double HEAD_FACTOR = 0.8;
    private static final double HEAD_WIDTH = 5;

    @Expose private String myCode = "{ entity, state -> ; INSERT_HERE }";
    @Expose private SavedArrow myState;
    private StateRender original;
    private StateRender destination;
    private Group myGroup = new Group();
    private Vector myOrigin;
    private Vector myHead;
    private Vector myLength;
    private Line myBody = new Line();
    private Line myNegativeHead = new Line();
    private Line myPositiveHead = new Line();
    private GraphDelegate myGraph;
    private boolean deleting;

    /**
     * @param origin Original point of the arrow
     * @param head Head of the arrow
     * @param graph GraphDelegate that holds all the information about FSMGraph including other arrows and states
     */
    Arrow(Vector origin, Vector head, GraphDelegate graph) {
        myGraph = graph;
        myOrigin = origin;
        myHead = head;
        myLength = head.subtract(origin);
        setArrow();
        initArrow();
    }

    private Arrow() {}

    /**
     * Used to store the information about all the Vectors to recreate the visualization
     */
    public void save() {
        myState = new SavedArrow(original, destination, myOrigin, myHead, myLength);
    }

    /**
     * Initialize the creation of the arrow with on click and adding everything to the overall group
     */
    private void initArrow() {
        myBody.setStrokeWidth(HEAD_WIDTH);
        myNegativeHead.setStrokeWidth(HEAD_WIDTH);
        myPositiveHead.setStrokeWidth(HEAD_WIDTH);
        myBody.setOnMouseClicked(e -> onClick());
        myNegativeHead.setOnMouseClicked(e -> onClick());
        myPositiveHead.setOnMouseClicked(e -> onClick());
        myGroup.getChildren().addAll(myBody, myNegativeHead, myPositiveHead);
    }

    /**
     * Sets the lines of the arrow from the vectors.
     */
    private void setArrow() {
        myBody.setStartX(myOrigin.at(0));
        myBody.setStartY(myOrigin.at(1));
        myBody.setEndX(myHead.at(0));
        myBody.setEndY(myHead.at(1));
        setHeadOffsets(myNegativeHead);
        setHeadOffsets(myPositiveHead);
        setHeadAngle(myNegativeHead, -HEAD_OFFSET);
        setHeadAngle(myPositiveHead, HEAD_OFFSET);
    }


    /**
     * Reacts to arrow on click by creating new popup
     */
    void onClick() {
        if (deleting) { return; }
        deleting = true;
        Stage stage = new ArrowPopup(myGraph, this).getStage();
        stage.setOnHidden(e -> deleting = false);
        stage.show();
    }

    /**
     * When recreating the FSMGraph from the JSON representation of the graph, the individual arrow
     * and state classes are created first and then the actual FSMGraph object is recreated. This
     * method is called by the FSMGraph to get all the information from its components and reset
     * all the links between arrows and states, as well as initialize the arrow.
     *
     * @param graph The graph that holds all the information about all arrows and states
     */
    void setGraphDelegate(GraphDelegate graph) {
        myGraph = graph;
        original = myGraph.findStateRenderWith(myState.getMyOriginal());
        destination = myGraph.findStateRenderWith(myState.getMyDestination());
        myOrigin = new Vector(myState.getOriginX(), myState.getOriginY());
        myHead = new Vector(myState.getHeadX(), myState.getHeadY());
        myLength = new Vector(myState.getLenX(), myState.getLenY());
        setArrow();
        initArrow();
    }

    /**
     * @return The group with the content to be visualized
     */
    Group getRender() {
        return myGroup;
    }

    /**
     * Used to refresh the angle of the head for visualization
     *
     * @param head
     * @param angle
     */
    private void setHeadAngle(Line head, double angle) {
        head.getTransforms().clear();
        head.getTransforms().add(new Rotate(angle, myHead.at(0), myHead.at(1)));
    }

    /**
     * Resets the head offsets for the visualization
     *
     * @param head
     */
    private void setHeadOffsets(Line head) {
        Vector myHeadStartPoint = myOrigin.add(myLength.multiply(HEAD_FACTOR));
        head.setStartX(myHeadStartPoint.at(0));
        head.setStartY(myHeadStartPoint.at(1));
        head.setEndX(myHead.at(0));
        head.setEndY(myHead.at(1));
    }

    /**
     * Resets the head position to make the visualization
     *
     * @param headPosition
     */
    void setHead(Vector headPosition) {
        myHead = headPosition;
        myLength = myHead.subtract(myOrigin);
        setArrow();
    }

    /**
     * @return The stateRender where the arrow originated
     */
    StateRender getOriginal() {
        return original;
    }

    /**
     * @param original Sets the original StateRender to the given value
     */
    void setOriginal(StateRender original) {
        this.original = original;
    }

    /**
     * @return Ending StateRender of the arrow
     */
    StateRender getDestination() {
        return destination;
    }

    /**
     * @param destination Set the ending destination of the arrow
     */
    void setDestination(StateRender destination) {
        this.destination = destination;
    }

    /**
     * @return Return code that was input to the arrow transition
     */
    String getMyCode() { return myCode; }

    /**
     * @param code Code that the code of the arrow will be set to
     */
    void setMyCode(String code) { myCode = code; }

    @Override
    public void initialize() { }
}
