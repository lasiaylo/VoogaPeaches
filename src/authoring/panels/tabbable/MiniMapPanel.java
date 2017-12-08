package authoring.panels.tabbable;

import authoring.Panel;
import authoring.PanelController;
import engine.EntityManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import util.ErrorDisplay;

public class MiniMapPanel implements Panel{

    private Pane myPane;
    private TextField levelName;
    private TextField mapWidth;
    private TextField mapHeight;
    private Button addLevel;
    private EntityManager manager;
    private HBox levelBar;

    public MiniMapPanel() {
        myPane = new Pane();
        myPane.getStyleClass().add("panel");
        levelName = new TextField("level name");
        mapWidth = new TextField("map width");
        mapHeight = new TextField("map height");
        addLevel = new Button("add level");
        levelBar = new HBox(levelName, mapWidth, mapHeight);
        levelBar.setSpacing(10);

        addLevel.setOnMouseClicked(e -> add());

    }

    private void add() {
        levelName.commitValue();
        mapWidth.commitValue();
        mapHeight.commitValue();
        try {
            int width = Integer.parseInt(mapWidth.getText());
            int height = Integer.parseInt(mapHeight.getText());
            manager.addLevel(levelName.getText(), width, height);
            levelName.setText("level name");
            mapWidth.setText("map width");
            mapHeight.setText("map height");
        }
        catch (NumberFormatException e){
            new ErrorDisplay("Not a integer");
        }
    }

    @Override
    public Region getRegion() {
        VBox box = new VBox(myPane, levelBar, addLevel);
        box.setSpacing(15);
        box.setPadding(new Insets(15));
        box.setAlignment(Pos.CENTER);
        Pane holder = new StackPane();
        holder.getChildren().add(box);
        StackPane.setAlignment(box, Pos.CENTER);
        return holder;
    }

    @Override
    public void setController(PanelController controller) {

        myPane = controller.getMiniMap();
        myPane.setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null)));
        myPane.setCenterShape(true);
        manager = controller.getManager();
    }

    @Override
    public String title(){
        return "Mini Map";
    }

}
