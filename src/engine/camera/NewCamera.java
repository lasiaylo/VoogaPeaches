package engine.camera;

import engine.entities.Entity;
import engine.events.KeyPressEvent;
import javafx.scene.*;
import javafx.scene.Camera;
import util.math.num.Vector;

public class NewCamera {
    private SubScene scene;
    private Group group;
    private Camera camera;
    private Entity currentLevel;

    public NewCamera(Entity level){
        currentLevel = level;
        camera = new ParallelCamera();
        group = new Group();
        scene = new SubScene(group,10,10);

        changeLevel(level);
    }

//Recheck if you did this right
    public void changeLevel(Entity level) {
        if (levelIsEmpty()) {
            currentLevel.add(group);
        }
        add(level.getNodes().getChildren().get(0));
//        view.getContent().requestFocus();
        group.setOnKeyPressed(e->new KeyPressEvent(e).recursiveFire(level));
        currentLevel = level;
    }

    private void add(Node...node) {
        group.getChildren().addAll(node);
    }

    private boolean levelIsEmpty() {
        return currentLevel.getNodes().getChildren().size() == 0;
    }



    public Node getView(Vector position, Vector scale){
        System.out.println("Setting group to a subscene");
        scene.setWidth(scale.at(0));
        scene.setHeight(scale.at(1));
//        Possibly have to create a new camera each time
        scene.setCamera(camera);
        moveCamera(position);
        return scene;
    }

    public Node getView(Vector scale){
        return getView(new Vector(0,0), scale);
    }

    public Node getMinimap(Vector size){
        Node mini = getView(size);
//        Fix magic value later
        camera.setTranslateZ(-100);
        return mini;
    }

    private void moveCamera(Vector position) {
        camera.setLayoutX(position.at(0));
        camera.setLayoutY(position.at(1));
    }


}
