package authoring.panels.tabbable;

import authoring.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MiniMapPanel implements Panel{

    private Pane myPane;

    public MiniMapPanel() {
        myPane = new Pane();
        myPane.getStyleClass().add("panel");
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
