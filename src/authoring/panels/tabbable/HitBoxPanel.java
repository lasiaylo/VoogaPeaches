package authoring.panels.tabbable;

import authoring.Panel;
import authoring.buttons.CustomButton;
import engine.collisions.HitBox;
import engine.entities.Entity;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import util.ErrorDisplay;
import util.pubsub.PubSub;
import util.pubsub.messages.EntityPass;

import java.util.ArrayList;
import java.util.List;

public class HitBoxPanel implements Panel {

    private static final String TITLE = "HitBoxes";
    private static final int MIN_HEIGHT = 500;
    private static final String PANEL = "panel";
    private static final String ENTITY_PASS = "ENTITY_PASS";
    private static final String VIEW_ALL = "View All";
    private static final String HITBOX_ERROR = "HitBox error";
    private static final String EMPTY_HITBOX_TAG = "Your HitBox's tag was empty!";
    private static final String EMPTY_STRING = "";
    private static final String SAVE = "Save";
    private static final String ADD_HITBOX = "Add Hitbox";
    private static final double OPTIONS_WIDTH_RATIO = 0.2;
    private static final double OPTION_WIDTH_RATIO = 0.8;
    private static final int RADIUS = 5;

    private Pane entityView = new Pane();
    private VBox region = new VBox();
    private TextField hitboxNameField = new TextField();
    private ComboBox<String> hitboxSelection = new ComboBox<>();
    private GridPane options = new GridPane();

    private List<HitBox> hitboxes = new ArrayList<>();
    private List<Double> currentPoints;
    private Button saveButton;
    private Button addButton;

    public HitBoxPanel() {
        createEntityView();

        region.getChildren().add(options);

        options.add(hitboxNameField, 0, 0);
        options.add(hitboxSelection, 0, 1);
//        GridPane.setHgrow(hitboxNameField, Priority.ALWAYS);


        getRegion().getStyleClass().add(PANEL);
        createAddButton();
        createComboBox();

        createSaveButton();
        PubSub.getInstance().subscribe(ENTITY_PASS, e -> {
            EntityPass entityPass = (EntityPass) e;
            setEntity(entityPass.getEntity());
        });
    }

    public void setEntity(Entity entity) {
        hitboxes.forEach(hitbox -> hitbox.getHitbox().setFill(Color.TRANSPARENT));
        entityView.getChildren().clear();
        hitboxes = entity.getHitBoxes();
        hitboxSelection.getItems().clear();
        createComboBox();

        options.getColumnConstraints().addAll(
                new ColumnConstraints(region.getWidth() * OPTION_WIDTH_RATIO), new ColumnConstraints(region.getWidth()* OPTIONS_WIDTH_RATIO));
        hitboxNameField.prefWidthProperty().bind(options.getColumnConstraints().get(0).prefWidthProperty());
        hitboxSelection.prefWidthProperty().bind(options.getColumnConstraints().get(0).prefWidthProperty());
        addButton.prefWidthProperty().bind(options.getColumnConstraints().get(1).prefWidthProperty());
        saveButton.prefWidthProperty().bind(options.getColumnConstraints().get(1).prefWidthProperty());
    }

    private void createComboBox() {
        hitboxSelection.getItems().add(VIEW_ALL);
        hitboxSelection.getSelectionModel().selectLast();
        for(HitBox h : hitboxes)
            hitboxSelection.getItems().add(h.getTag());
        hitboxSelection.getSelectionModel().selectedIndexProperty().addListener((arg, oldVal, newVal) ->{
            entityView.getChildren().remove(0, entityView.getChildren().size());
            if(newVal.intValue() == 0 | newVal.intValue() == -1){
                hitboxes.forEach(box -> {
                    box.getHitbox().setFill(Color.LIGHTGRAY);
                    entityView.getChildren().add(box.getHitbox());
                });
            } else {
                hitboxes.get(newVal.intValue() - 1).getHitbox().setFill(Color.LIGHTGRAY);
                entityView.getChildren().add(hitboxes.get(newVal.intValue() - 1).getHitbox());
                currentPoints = hitboxes.get(newVal.intValue() - 1).getPoints();
            }
        });
    }

    private void createSaveButton() {
        saveButton = new CustomButton(() -> {
            String boxName = hitboxNameField.getText();
            if(boxName.equals(EMPTY_STRING)) {
                new ErrorDisplay(HITBOX_ERROR, EMPTY_HITBOX_TAG).displayError();
            } else {
                hitboxes.get(hitboxSelection.getSelectionModel().getSelectedIndex() - 1).setTag(boxName);
                hitboxSelection.getItems().set(hitboxSelection.getSelectionModel().getSelectedIndex(), boxName);
            }
        }, SAVE).getButton();
        options.add(saveButton, 1, 0);
    }

    private void createAddButton() {
        addButton = new CustomButton(() -> {
            hitboxSelection.getItems().add(EMPTY_STRING);
            hitboxes.add(new HitBox(new ArrayList<Double>(), 0.0, 0.0, EMPTY_STRING));
            hitboxSelection.getSelectionModel().selectLast();
        }, ADD_HITBOX).getButton();
        options.add(addButton, 1, 1);
    }

    private void createEntityView(){
        entityView.setMinHeight(MIN_HEIGHT);
        region.getChildren().add(entityView);
        entityView.setOnMouseClicked(e -> addPointClicked(e));
    }

    private void addPointClicked(MouseEvent event) {
        if(hitboxSelection.getSelectionModel().getSelectedIndex() != 0) {
            addNewPoint(event.getX(), event.getY());
            if(currentPoints.size() == 6) {
                for(int i = 0; i < 6; i+=2)
                    hitboxes.get(hitboxSelection.getSelectionModel().getSelectedIndex() - 1).addPoints(currentPoints.get(i), currentPoints.get(i + 1));
            } else if (currentPoints.size() > 6) {
                entityView.getChildren().remove(1, entityView.getChildren().size());
                currentPoints.add(event.getX());
                currentPoints.add(event.getY());
                hitboxes.get(hitboxSelection.getSelectionModel().getSelectedIndex() - 1).addPoints(event.getX(),event.getY());
            }
        }
    }

    private void addNewPoint(double x, double y) {
        Circle newPoint = new Circle();
        newPoint.setCenterX(x);
        newPoint.setCenterY(y);
        newPoint.setRadius(RADIUS);
        newPoint.setFill(Color.BLACK);
        currentPoints.add(x);
        currentPoints.add(y);
        entityView.getChildren().add(newPoint);
    }

    @Override
    public Region getRegion() {
        return region;
    }

    @Override
    public String title() {
        return TITLE;
    }
}