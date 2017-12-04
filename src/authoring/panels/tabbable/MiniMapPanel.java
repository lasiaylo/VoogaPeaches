package authoring.panels.tabbable;

import authoring.*;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class MiniMapPanel implements Panel{

    private Pane myPane;
    private IPanelController myController;

    public MiniMapPanel() {
        myPane = new Pane();
        myPane.getStyleClass().add("panel");
    }

    @Override
    public Region getRegion() {
        return myPane;
    }

    @Override
    public void setController(IPanelController controller) {
        myController = controller;
        myPane = myController.getMiniMap();
        myPane.setBackground(new Background(new BackgroundFill(Color.ROYALBLUE, null, null)));
        myPane.setPadding(new Insets(20));

    }

    @Override
    public String title(){
        return "Mini Map";
    }

}
