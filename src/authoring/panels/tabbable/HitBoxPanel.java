package authoring.panels.tabbable;

import authoring.Panel;
import authoring.buttons.CustomButton;
import engine.collisions.HitBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;

public class HitBoxPanel implements Panel {
    private static final double RADIUS = 5;
    private static final String TITLE = "Create or Add Hitboxes!";
    private static final int MIN_HEIGHT = 500;

    private Pane entityView = new Pane();
    private VBox region = new VBox();
    private TextField hitboxNameField = new TextField();
    private List<Double> points = new ArrayList<>();

    private List<HitBox> hitboxes;

    private Polygon currentHitBox;
    private ComboBox<String> hitboxSelection;

    public HitBoxPanel(List<HitBox> boxes) {

        hitboxes = boxes;

        createEntityView();
        createSaveButton();
        createComboBox();
        region.getChildren().add(hitboxNameField);

        // Create the new hitbox polygon
        currentHitBox = createPolygon();
        entityView.getChildren().add(currentHitBox);
    }

    private void createComboBox() {
        hitboxSelection = new ComboBox<>();
        hitboxSelection.getItems().add("View All");
        region.getChildren().add(hitboxSelection);
        //for(HitBox h : hitboxes)
            //hitboxSelection.getItems().add(h.getTag());
    }

    private void createSaveButton() {
        CustomButton saveBtn = new CustomButton(() -> {
            String boxName = hitboxNameField.getText();
            hitboxSelection.getItems().add(boxName);
            hitboxSelection.getSelectionModel().selectLast();
            System.out.println(hitboxSelection.getSelectionModel().getSelectedItem());
        }, "Save");
        region.getChildren().add(saveBtn.getButton());
    }

    private void createEntityView(){
        entityView.setMinHeight(MIN_HEIGHT);
        region.getChildren().add(entityView);
        entityView.setOnMouseClicked(e -> addPointClicked(e));
    }

    private Polygon createPolygon() {
        Polygon newPolygon = new Polygon();
        newPolygon.setFill(Color.LIGHTGRAY);
        return newPolygon;
    }

    private void addPointClicked(MouseEvent event) {
        addNewPoint(event.getX(), event.getY());
        if(points.size() > 4) {
            entityView.getChildren().remove(1, entityView.getChildren().size());
            currentHitBox.getPoints().addAll(points);
        }
    }

    private void addNewPoint(double x, double y) {
        Circle newPoint = new Circle();
        newPoint.setCenterX(x);
        newPoint.setCenterY(y);
        newPoint.setRadius(5);
        newPoint.setFill(Color.BLACK);
        points.add(x);
        points.add(y);
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
