package authoring.panels.tabbable;

import authoring.Panel;
import authoring.PanelController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MiniMapPanel implements Panel{

    private Pane myPane;
    private TextField levelName;
    private TextField mapWidth;
    private TextField mapHeight;
    private Button addLevel;

    public MiniMapPanel() {
        myPane = new Pane();
        myPane.getStyleClass().add("panel");
        levelName = new TextField();
    }

    @Override
    public Region getRegion() {
        Pane holder = new StackPane();
        holder.getChildren().add(myPane);
        StackPane.setAlignment(myPane, Pos.CENTER);
        return holder;
    }

    @Override
    public void setController(PanelController controller) {

        myPane = controller.getMiniMap();
        myPane.setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null)));
        myPane.setCenterShape(true);
    }

    @Override
    public String title(){
        return "Mini Map";
    }

}
