package authoring.fsm;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import util.math.num.Vector;

import java.util.ArrayList;
import java.util.List;

public class Arrow {
    private static final double bodyScale = 0.8;
    private static final double PADDING = 5;

    private Group myGroup = new Group();
    private Vector myOrigin;
    private Vector myHead;
    private List<Line> myArrow = new ArrayList<>();
    public Arrow(Vector origin, Vector head) {
        myOrigin = origin;
        myHead = head;
        createBody();
        myGroup.getChildren().addAll(myArrow);
    }

    private void createBody() {
        Line body = new Line();
        body.setStartX(myOrigin.at(0));
        body.setStartY(myOrigin.at(1));
        body.setEndX(myHead.at(0));
        body.setEndY(myHead.at(1));
        myArrow.add(body);

    }

    private Polygon createHead() {
        Polygon head = new Polygon();

    }
}
