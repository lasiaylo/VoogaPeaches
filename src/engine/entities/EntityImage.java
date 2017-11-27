package engine.entities;

import engine.scripts.IScript;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

/**
 * wrapper class for imageview
 *
 * store entity pointer
 *
 * @author Estelle He
 */
public class EntityImage extends ImageView{
    private Image myImage;
    private Entity myEntity;

    public EntityImage(Entity entity, Image image) {
        myImage = image;
        myEntity = entity;
        this.setImage(myImage);
        this.setOnMouseClicked(e -> print());

    }

    private void print() {
        System.out.println(this.getX());
        System.out.println(this.getBoundsInLocal().getMinX());
        System.out.println(this.getBoundsInParent().getMinX());
    }

    /**
     * entity transform getter
     * @return transform
     */
    public Transform getTransform() {
        return myEntity.getTransform();
    }

    /**
     * entity render getter
     * @return render
     */
    public Render getRender() {
        return myEntity.getRender();
    }

    /**
     * entity script list getter
     * @return List<IScripts>
     */
    public List<IScript> getScripts() {
        return myEntity.getScripts();
    }
}
