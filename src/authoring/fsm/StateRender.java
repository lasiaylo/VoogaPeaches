package authoring.fsm;

import engine.fsm.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateRender {
    private static final double PADDING = 30;
    private static final Color DEFAULT = Color.DARKSLATEBLUE;
    private static final Color ERROR = Color.PALEVIOLETRED;
    private Label myTitle;
    private Pane myPane;
    private Rectangle myRender = new Rectangle();
    private State myState;
    private GraphDelegate myGraph;
    private Map<String, Object> myInfo;
    private boolean deleting;
    private List<Arrow> myLeavingTransitions = new ArrayList<>();

    public StateRender(double X, double Y, String title, State state, GraphDelegate graph) {
        myState = state;
        myRender.setFill(ERROR); // hard coded
        myRender.setX(X);
        myRender.setY(Y);
        myGraph = graph;
        myInfo = new HashMap<>();

        myTitle = new Label(title);
        myRender.heightProperty().bind(myTitle.heightProperty().add(PADDING));
        myRender.widthProperty().bind(myTitle.widthProperty().add(PADDING));
        myRender.setOnMouseClicked(e -> onClick());
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
//        flow.getChildren().add(new ParameterProperties(myInfo).getNode());
        flow.setMinSize(100, 200);
        Button delete = new Button("Delete State");
        Button save = new Button("Save");
        delete.setOnMouseClicked(e -> onDelete(delete));
        save.setOnMouseClicked(e -> onSave(save));
        flow.getChildren().addAll(delete, save);
        return flow;
    }

    private void onSave(Button save) {
        ((Stage) save.getScene().getWindow()).close();
        System.out.println("Update map!");
    }

    private void onDelete(Button delete) {
        ((Stage) delete.getScene().getWindow()).close();
        myGraph.removeMyself(this);
    }

    protected Shape getRender() {
        return myRender;
    }

    protected void setFill(Color color) {
        myRender.setFill(color);
    }

    protected void addLeavingTransition(Arrow tRender) {
//        #TODO ASK RAMIL ABOUT ADDING TRANSITIONS
        myLeavingTransitions.add(tRender);
    }

    protected void removeLeavingTransition(Arrow tRender) {
//        myState.getTransitions().remove(tRender.getTransition());
        myLeavingTransitions.remove(tRender);
    }

    protected List<Arrow> getMyLeavingTransitions() {
        return myLeavingTransitions;
    }

}
