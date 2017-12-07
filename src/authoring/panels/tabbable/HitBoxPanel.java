package authoring.panels.tabbable;

import authoring.Panel;
import authoring.buttons.CustomButton;
import engine.collisions.HitBox;
import engine.entities.Entity;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

public class HitBoxPanel implements Panel {
    private static final String TITLE = "Create or Add Hitboxes!";
    private static final int MIN_HEIGHT = 500;

    private Pane entityView = new Pane();
    private VBox region = new VBox();
    private TextField hitboxNameField = new TextField();
    private ComboBox<String> hitboxSelection;

    private List<HitBox> hitboxes;
    private List<Double> currentPoints;

    public HitBoxPanel() {
        createEntityView();

        region.getChildren().add(hitboxNameField);

        // Create the new hitbox polygon

    }

    public void setEntity(Entity entity) {
        hitboxes = entity.getHitBoxes();
        createAddButton();
        createComboBox();
        for(HitBox h : hitboxes) {
            h.getHitbox().setFill(Color.LIGHTGRAY);
            entityView.getChildren().add(h.getHitbox());
        }
    }

    private void createComboBox() {
        hitboxSelection = new ComboBox<>();
        hitboxSelection.getItems().add("View All");
        region.getChildren().add(hitboxSelection);
        hitboxSelection.getSelectionModel().selectLast();
        for(HitBox h : hitboxes)
            hitboxSelection.getItems().add(h.getTag());
        hitboxSelection.getSelectionModel().selectedIndexProperty().addListener((arg, oldVal, newVal) ->{
            entityView.getChildren().remove(0, entityView.getChildren().size());
            if(newVal.intValue() == 0){
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

    private void createAddButton() {
       region.getChildren().add(new CustomButton(() -> {
            String boxName = hitboxNameField.getText();
            hitboxSelection.getItems().add(boxName);
            hitboxes.add(new HitBox(new ArrayList<Double>(), 0.0, 0.0, boxName));
            hitboxSelection.getSelectionModel().selectLast();
        }, "Add Hitbox").getButton());
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
        newPoint.setRadius(5);
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
