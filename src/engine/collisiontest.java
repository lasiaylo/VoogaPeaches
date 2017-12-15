package engine;

import engine.collisions.HitBox;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class collisiontest{
    public List<HitBox> hitbox;
    public Group group;

    public collisiontest(){
        hitbox = new ArrayList<HitBox>();
    }

    public void start(){
        group = new Group();
        for(HitBox h : hitbox){
            h.getIntersect();
            group.getChildren().add(h.getIntersect());
        }
        setupStage();
    }

    private void setupStage() {
        Scene scene = new Scene(group);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

}
