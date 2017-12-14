package authoring.fsm;

import authoring.panels.attributes.ParameterProperties;
import authoring.panels.attributes.Updatable;
import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import engine.fsm.State;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import util.exceptions.GroovyInstantiationException;

import java.util.*;


public class StateRender extends TrackableObject implements Updatable {

    private static final double PADDING = 30;
    private static final Color DEFAULT = Color.DARKSLATEBLUE;
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

    public StateRender(Double X, Double Y, String title, GraphDelegate graph) {
        myInfo = new HashMap<>();
        myTitle = title;
        myGraph = graph;
        initRender(X, Y);
    }

    private void initRender(Double X, Double Y) {
        myLabel = new Label(myTitle);
        myRender.setFill(ERROR);
        myRender.setX(X);
        myRender.setY(Y);
        myRender.heightProperty().bind(myLabel.heightProperty().add(PADDING));
        myRender.widthProperty().bind(myLabel.widthProperty().add(PADDING));
        myRender.setOnMouseClicked(e -> onClick());
    }

    private StateRender() {}

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
        Map<String, Map<String, Object>> data = new LinkedHashMap<>();
        data.put("properties", myInfo);
        Map<String, Object> transitions = new LinkedHashMap<>();
        for(Arrow arrow: myLeavingTransitions) {
            System.out.println("Transition");
            transitions.put(arrow.getDestination().getName(), arrow.getMyCode());
        }
        data.put("transitions", transitions);
        return new State(getName(), data);
    }

    public void save() {
        mySave = new SavedStateRender(myLeavingTransitions, myRender);
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
        flow.getChildren().addAll(delete, save, myLabel);
    }

    public String getName() {
        return myLabel.getText();
    }

    @Override
    public void initialize() { }

    public void setGraphDelegate(GraphDelegate graph) {
        myGraph = graph;
        initRender(mySave.getXRect(), mySave.getYRect());
        for(String code: mySave.getArrowCode()) {
            myLeavingTransitions.add(myGraph.findArrowWith(code));
        }
    }
}
