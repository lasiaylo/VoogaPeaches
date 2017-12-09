package authoring.panels.tabbable;

import authoring.Panel;
import authoring.PanelController;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MiniMapPanel implements Panel{

    private Pane myPane;
    Pane holder = new StackPane();

    public MiniMapPanel() {
    }

    @Override
    public Region getRegion() {
        holder.getChildren().add(myPane);
        holder.getStyleClass().add("panel");
        StackPane.setAlignment(myPane, Pos.CENTER);
        return holder;
    }

    @Override
    public void setController(PanelController controller) {
        myPane = controller.getMiniMap();
        myPane.getStyleClass().add("panel");
        myPane.setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null)));
        myPane.setCenterShape(true);
    }

    @Override
    public String title(){
        return "Mini Map";
    }

}
