package authoring.fsm;

import authoring.panels.attributes.ParameterProperties;
import authoring.panels.attributes.Updatable;
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
import util.exceptions.GroovyInstantiationException;

import java.util.*;

public class StateRender implements Updatable {
    private static final double PADDING = 30;
    private static final Color DEFAULT = Color.DARKSLATEBLUE;
    private static final Color ERROR = Color.PALEVIOLETRED;
    private Label myTitle;
    private Map<String, Object> myInfo;
    private List<Arrow> myLeavingTransitions = new ArrayList<>();

    private Rectangle myRender = new Rectangle();
    private Pane myPane;
    private GraphDelegate myGraph;
    private boolean deleting;
    private FlowPane flow;

    public StateRender(double X, double Y, String title, GraphDelegate graph) {
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

    public void onClick() {
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
        flow = new FlowPane();
        update();
        return flow;
    }

    private void onDone(Button done) {
        ((Stage) done.getScene().getWindow()).close();
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

    protected void addLeavingTransition(Arrow arrow) {
        myLeavingTransitions.add(arrow);
    }

    protected void removeLeavingTransition(Arrow arrow) {
        myLeavingTransitions.remove(arrow);
    }

    protected List<Arrow> getMyLeavingTransitions() {
        return myLeavingTransitions;
    }

    protected State createNewState() {
        LinkedHashMap<String, LinkedHashMap<String, Object>> data = new LinkedHashMap<>();
        LinkedHashMap<String, Object> properties = new LinkedHashMap<>(myInfo);
        data.put("properties", properties);
        LinkedHashMap<String, Object> transitions = new LinkedHashMap<>();
        for(Arrow arrow: myLeavingTransitions) {
            transitions.put(arrow.getDestination().getName(), arrow.getMyCode());
        }
        data.put("transitions", transitions);
        return new State(getName(), data);
    }

    @Override
    public void update() {
        flow.getChildren().clear();
        try {
            flow.getChildren().add(new ParameterProperties(myInfo, this).getNode());
        } catch (GroovyInstantiationException e) {
            e.printStackTrace();
        }
        flow.setMinSize(100, 200);
        Button delete = new Button("Delete State");
        Button save = new Button("Done");
        delete.setOnMouseClicked(e -> onDelete(delete));
        save.setOnMouseClicked(e -> onDone(save));
        flow.getChildren().addAll(delete, save);
    }

    public String getName() {
        return myTitle.getText();
    }
}
