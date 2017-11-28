package engine.entities;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import engine.util.FXProcessing;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.math.num.Vector;

/**Wrapper Class for Entity's image
 * @author lasia
 *
 */
public class Render extends ImageView {
	private Entity myEntity;

	/**
	 * Creates a new Render from database
	 */
	private Render() {
	}

	public Render(Entity entity) {
		myEntity = entity;
	}

	public void displayUpdate(Transform transform) {
		setPosition(transform.getPosition());
		setRotate(transform.getRotation());
		setSize(transform.getSize());
	}

	/**
	 * @param position	new position of the Imageview
	 */
	private void setPosition(Vector position) {
		this.setX(FXProcessing.getXImageCoord(position.at(0), this));
		this.setY(FXProcessing.getYImageCoord(position.at(1), this));
	}

	/**
	 * Sets the size (width, height) of the imageview
	 * @param size		Size of the imageview.(1,1) is standard scale
	 */
	private void setSize(Vector size) {
		this.setFitWidth(size.at(0));
		this.setFitHeight(size.at(1));
	}


	/**
	 * set imageview visibility
	 * @param vis
	 */
	public void setVis(boolean vis) {
		this.setVisible(vis);
	}

}
