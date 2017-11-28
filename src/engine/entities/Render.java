package engine.entities;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import engine.util.FXProcessing;
import javafx.scene.image.ImageView;
import util.math.num.Vector;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**Wrapper Class for Entity's image
 * @author lasia
 *
 */
public class Render extends TrackableObject {
	@Expose private ImageView myImageView;
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
		myImageView.setX(position.at(0) - myEntity.getTransform().getSize().at(0)/2);
	    myImageView.setY(position.at(1) - myEntity.getTransform().getSize().at(1)/2);
	}
	
	/**Sets the value of the imageview
	 * @param rotation	Rotation in degrees
	 */
	private void setRotate(double rotation) {
		myImageView.setRotate(rotation);
	}
	
	/**
	 * Sets the size (width, height) of the imageview
	 * @param size		Size of the imageview.(1,1) is standard scale
	 */
	private void setSize(Vector size) {
		myImageView.setFitWidth(size.at(0));
		myImageView.setFitHeight(size.at(1));
	}
	
	
	/**
	 * set imageview visibility
	 * @param vis	
	 */
	public void setVisible(boolean vis) {
	    myImageView.setVisible(vis);
	}
	
	/**
	 * set imageview transparency to mouse click
	 * @param trans
	 */
	public void setMouseTrans(boolean trans) {
	    myImageView.setMouseTransparent(trans);
	}
	
	/**
	 * get imageview 
	 * @return imageview
	 */
	public ImageView getImage() {
		return myImageView;
	}
	
	/**
	 * set imageview
	 */
	public void setImage(ImageView newImage) {
	    myImageView = newImage;
	}

}
