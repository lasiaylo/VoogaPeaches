import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;



public class lmaocollisiontesting extends Application {
    StackPane stack;
    Group group;
    @Override
    public void start(Stage primaryStage) throws Exception {
        group = new Group();
        stack = new StackPane();
        Polygon poly1 = new Polygon(485,327,754,334,799,477,496,523);
        Polygon poly2 = new Polygon(566,373,859,379,877,504,540,516);
        System.out.println(poly1);
        System.out.println(poly2);
//        group.getChildren().addAll(poly1, poly2);

        Shape intersect = Shape.intersect(poly1,poly2);
        System.out.println(intersect);
        intersect.setFill(Color.RED);
        group.getChildren().add(intersect);

        setupStage();


    }

    private void setupStage() {
        Scene scene = new Scene(group);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}
