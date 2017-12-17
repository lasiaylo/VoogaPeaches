package authoring.fsm;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import util.math.num.Vector;

/**
 * Class to represent the arrow in the FSM visualization.
 *
 * @author Simran
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


    void onClick() {
        if (deleting) { return; }
        deleting = true;
        Scene scene = new Scene(new Group());
        FlowPane flow = createPopup();
        scene.setRoot(flow);
        Stage stage = new Stage();
        stage.setOnHidden(e -> deleting = false);
        stage.setScene(scene);
        stage.show();
    }

    private FlowPane createPopup() {
        FlowPane flow = new FlowPane();
        flow.setMinSize(100, 200);
        Button delete = new Button("Delete Transition");
        Button save = new Button("Save");
        TextField name = new TextField();
        name.setPromptText("Enter your closure lol");
        name.setText(myCode);
        delete.setOnMouseClicked(e -> onDelete(delete));
        save.setOnMouseClicked(e -> onSave(save, name.getText()));
        flow.getChildren().addAll(delete, save, name);
        return flow;
    }

    private void onDelete(Button delete) {
        ((Stage) delete.getScene().getWindow()).close();
        myGraph.removeMyself(this);
    }

    private void onSave(Button save, String name) {
        myCode = name;
        ((Stage) save.getScene().getWindow()).close();
    }

    Group getRender() {
        return myGroup;
    }

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

    private void setHeadAngle(Line head, double angle) {
        head.getTransforms().clear();
        head.getTransforms().add(new Rotate(angle, myHead.at(0), myHead.at(1)));
    }

    private void setHeadOffsets(Line head) {
        Vector myHeadStartPoint = myOrigin.add(myLength.multiply(HEAD_FACTOR));
        head.setStartX(myHeadStartPoint.at(0));
        head.setStartY(myHeadStartPoint.at(1));
        head.setEndX(myHead.at(0));
        head.setEndY(myHead.at(1));
    }

    void setHead(Vector headPosition) {
        myHead = headPosition;
        myLength = myHead.subtract(myOrigin);
        setArrow();
    }

    StateRender getOriginal() {
        return original;
    }

    void setOriginal(StateRender original) {
        this.original = original;
    }

    StateRender getDestination() {
        return destination;
    }

    void setDestination(StateRender destination) {
        this.destination = destination;
    }

    String getMyCode() { return myCode; }



    public void save() {
        myState = new SavedArrow(original, destination, myOrigin, myHead, myLength);
    }

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

    @Override
    public void initialize() { }
}
