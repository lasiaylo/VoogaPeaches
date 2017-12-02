package engine.entities;

import com.google.gson.annotations.Expose;
import database.filehelpers.FileDataManager;
import database.firebase.TrackableObject;
import engine.scripts.defaults.ImageScript;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import util.math.num.Vector;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;


/**
 * Store entities in each layer of the gaming world
 * @author estellehe
 *
 */
public class Layer extends TrackableObject {
	@Expose private List<Entity> myEntityList;
	@Expose private Group myImageList;
    @Expose private int myID;


    /**
     * create layer, 0 is BG layer, others are different layers
     * @param ID
     */
	public Layer(int ID) {
		myEntityList = new ArrayList<Entity>();
		myImageList = new Group();
		myID = ID;
        ImageView holder = setPlaceHolder();
        myImageList.getChildren().add(holder);
    }

    private ImageView setPlaceHolder() {
        FileDataManager manager = new FileDataManager(FileDataManager.FileDataFolders.IMAGES);
        ImageView holder = new ImageView(new Image(manager.readFileData("holder.gif")));
        holder.setX(0);
        holder.setY(0);
        holder.setFitWidth(50);
        holder.setFitHeight(50);
        return holder;
    }


    /**
	 * select this layer: make all entity on this layer visible and mouse-clickable
	 */
	public void select() {
		for (Entity each: myEntityList) {
			each.getRender().setMouseTransparent(false);
			each.getRender().setVisible(true);
		}
	}

	/**
	 * deselect this layer: make all entity on this layer invisible and mouse-unclickable
	 */
	public void deselect() {
		for (Entity each: myEntityList) {
			each.getRender().setMouseTransparent(true);
			each.getRender().setVisible(false);
		}
	}

	/**
	 * view only mode for this layer
	 */
	public void onlyView() {
		for (Entity each: myEntityList) {
			each.getRender().setMouseTransparent(true);
			each.getRender().setVisible(true);
		}
	}

	/**
	 * get group of imageview representation of entities
	 * @return myimagelist
	 */
	public Group getImageList() {
		return myImageList;
	}

	/**
	 * update all entities in the layer
	 */
	public void updateAll() {
		for (Entity each: myEntityList) {
			each.update();
		}
	}

    /**
     * add entity to a layer
     * @param pos
     * @return
     */
	public Entity addEntity(Vector pos) {
        Entity newEnt = new Entity(pos);

        myEntityList.add(newEnt);
        myImageList.getChildren().add(newEnt.getRender());

        return newEnt;
    }

    /**
     * remove entity from the layer
     * @param ent
     */
    public void deleteEntity(Entity ent) {
	    myEntityList.remove(ent);
	    myImageList.getChildren().remove(ent.getRender());
    }

	/**
	 * update imageview of entities inside box
	 * @param center
	 * @param size
	 */
	public void displayUpdate(Vector center, Vector size) {
		for (Entity each: myEntityList) {
			Vector ePos = each.getTransform().getPosition();
			Vector eSize = each.getTransform().getSize();
			double xDis = abs(ePos.at(0) - center.at(0));
			double yDis = abs(ePos.at(1) - center.at(1));
			if (xDis <= (eSize.at(0) + size.at(0))/2 && yDis <= (eSize.at(1) + size.at(1))/2) {
				each.getRender().displayUpdate(each.getTransform());
			}
		}
	}

}
