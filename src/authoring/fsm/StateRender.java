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
import java.util.List;

public class StateRender {
    private static final double PADDING = 30;
    private static final Color DEFAULT = Color.DARKSLATEBLUE;
    private static final Color ERROR = Color.PALEVIOLETRED;
    private Label myTitle;
    private Pane myPane;
    private Rectangle myRender = new Rectangle();
    private State myState;

    private List<TransitionRender> myLeavingTransitions = new ArrayList<>();

    public StateRender(double X, double Y, String title, State state) {
        myState = state;
        myRender.setFill(ERROR); // hard coded
        myRender.setX(X);
        myRender.setY(Y);

        myTitle = new Label(title);
        myRender.heightProperty().bind(myTitle.heightProperty().add(PADDING));
        myRender.widthProperty().bind(myTitle.widthProperty().add(PADDING));
        myRender.setOnMouseClicked(e -> onClick());
    }

    private void onClick() {
        Scene scene = new Scene(new Group());
        FlowPane flow = createPopup();
        scene.setRoot(flow);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private FlowPane createPopup() {
        FlowPane flow = new FlowPane();
        flow.setMinSize(100, 200);
        Button delete = new Button("Delete State");
        Button save = new Button("Save");
        delete.setOnMouseClicked(e -> ((Group) myRender.getParent()).getChildren().remove(myRender));
        save.setOnMouseClicked(e -> System.out.println("Update map"));
        flow.getChildren().addAll(delete, save);
        return flow;
    }

    protected Shape getRender() {
        return myRender;
    }

    protected void setFill(Color color) {
        myRender.setFill(color);
    }

    protected void addLeavingTransition(TransitionRender tRender) {
        myState.getTransitions().add(tRender.getTransition());
        myLeavingTransitions.add(tRender);
    }

    protected void removeLeavingTransition(TransitionRender tRender) {
        myState.getTransitions().remove(tRender.getTransition());
        myLeavingTransitions.remove(tRender);
    }

    protected List<TransitionRender> getMyLeavingTransitions() {
        return myLeavingTransitions;
    }

    protected void addArrivingTransition(TransitionRender tRender) {

    }

    public void removeArrivingTransition(TransitionRender tRender) {

    }
}
