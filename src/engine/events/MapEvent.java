package engine.events;

import engine.entities.Entity;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class MapEvent extends Event{
    private StackPane myStackPane;

    public MapEvent(String type) {
        super(type);
    }

    public void setStack(StackPane stack) {
        myStackPane = stack;
    }

    public void addLayer(Entity layer) {
        myStackPane.getChildren().add(layer.getNodes());
        myStackPane.setAlignment(layer.getNodes(), Pos.TOP_LEFT);
    }
}
