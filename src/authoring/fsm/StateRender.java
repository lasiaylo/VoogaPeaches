package authoring.fsm;

import engine.managers.State;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class StateRender {
    private static final double PADDING = 30;
    private Label myTitle;
    private Pane myPane;
    private Rectangle myRender = new Rectangle();
    private State myState;

    private List<TransitionRender> myLeavingTransitions = new ArrayList<>();

    public StateRender(double X, double Y, String title) {
//        myState = state;
        myRender.setLayoutX(X);
        myRender.setLayoutY(Y);
        myRender.setFill(Color.DARKSLATEBLUE); // hard coded

        myTitle = new Label(title);
        myRender.heightProperty().bind(myTitle.heightProperty().add(PADDING));
        myRender.widthProperty().bind(myTitle.widthProperty().add(PADDING));
        myPane = new StackPane();
        myPane.getChildren().addAll(myRender, myTitle);
        // color red
    }

    protected Pane getRender() {
        return myPane;
    }

    protected void setFill(Color color) {
        myRender.setFill(color);
    }

    protected void addLeavingTransition(TransitionRender tRender) {
//        myState.getTransitions().add(tRender.getTransition());
        myLeavingTransitions.add(tRender);
    }

    protected void removeLeavingTransition(TransitionRender tRender) {
//        myState.getTransitions().remove(tRender.getTransition());
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
