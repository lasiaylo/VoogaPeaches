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
import javafx.scene.transform.Rotate;
import util.math.num.Vector;

import java.util.ArrayList;
import java.util.List;

public class Arrow {
    private static final double HEAD_OFFSET = 45;

    private Group myGroup = new Group();
    private Vector myOrigin;
    private Vector myHead;
    private Vector myLength;
    private Line myBody = new Line();
    private Line myNegativeHead = new Line();
    private Line myPositiveHead = new Line();

    public Arrow(Vector origin, Vector head) {
        myOrigin = origin;
        myHead = head;
        myLength = head.subtract(origin);
        setArrow();

        myGroup.getChildren().addAll(myBody, myNegativeHead, myPositiveHead);
    }

    public Group getGroup() {
        return myGroup;
    }

    private void setArrow() {
        myBody.setStartX(myOrigin.at(0));
        myBody.setStartY(myOrigin.at(1));
        myBody.setEndX(myHead.at(0));
        myBody.setEndY(myHead.at(1));

        setHead(myNegativeHead);
        setHead(myPositiveHead);

        setHeadAngle(myNegativeHead, -HEAD_OFFSET);
        setHeadAngle(myPositiveHead, HEAD_OFFSET);
    }

    private void setHeadAngle(Line head, double angle) {
        head.getTransforms().clear();
        head.getTransforms().add(new Rotate(angle, myHead.at(0), myHead.at(1)));
    }

    private void setHead(Line head) {
        Vector myHeadStartPoint = myOrigin.add(myLength.multiply(0.8));
        head.setStartX(myHeadStartPoint.at(0));
        head.setStartY(myHeadStartPoint.at(1));
        head.setEndX(myHead.at(0));
        head.setEndY(myHead.at(1));
    }

    public void setHead(Vector headPosition) {
        myHead = headPosition;
        myLength = myHead.subtract(myOrigin);
        setArrow();
    }
}
