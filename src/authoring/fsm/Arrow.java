package authoring.fsm;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import util.math.num.Vector;

public class Arrow {
    private static final double HEAD_OFFSET = 45;
    public static final double HEAD_FACTOR = 0.8;

    private Group myGroup = new Group();
    private Vector myOrigin;
    private Vector myHead;
    private Vector myLength;
    private Line myBody = new Line();
    private Line myNegativeHead = new Line();
    private Line myPositiveHead = new Line();
    private GraphDelegate myGraph;
    private boolean deleting;
    private StateRender original;
    private StateRender destination;

    public Arrow(Vector origin, Vector head, GraphDelegate graph) {
        myGraph = graph;
        myOrigin = origin;
        myHead = head;
        myLength = head.subtract(origin);
        setArrow();
        myBody.setOnMouseClicked(e -> onClick());
        myNegativeHead.setOnMouseClicked(e -> onClick());
        myPositiveHead.setOnMouseClicked(e -> onClick());
        myGroup.getChildren().addAll(myBody, myNegativeHead, myPositiveHead);
    }

    private void onClick() {
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
        delete.setOnMouseClicked(e -> onDelete(delete));
        save.setOnMouseClicked(e -> onSave(save));
        flow.getChildren().addAll(delete, save, name);
        return flow;
    }

    private void onDelete(Button delete) {
        ((Stage) delete.getScene().getWindow()).close();
        myGraph.removeMyself(this);
    }

    private void onSave(Button save) {
        ((Stage) save.getScene().getWindow()).close();
    }

    public Group getRender() {
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

    public void setHead(Vector headPosition) {
        myHead = headPosition;
        myLength = myHead.subtract(myOrigin);
        setArrow();
    }

    public StateRender getOriginal() {
        return original;
    }

    public void setOriginal(StateRender original) {
        this.original = original;
    }

    public StateRender getDestination() {
        return destination;
    }

    public void setDestination(StateRender destination) {
        this.destination = destination;
    }
}
