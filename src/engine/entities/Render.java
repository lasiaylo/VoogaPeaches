package engine.entities;

import engine.util.FXProcessing;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.math.num.Vector;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**Wrapper Class for Entity's image
 * @author lasia
 *
 */
public class Render {
    private String holder = "resources/graphics/holder.gif";
	private EntityImage myEntityImage;
	private Entity myEntity;
	 
	public Render(Entity entity) {
	    myEntity = entity;
        try {
            myEntityImage = new EntityImage(myEntity, new Image(new FileInputStream(holder)));//this should be a placeholder
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
		myEntityImage.setX(position.at(0) - myEntity.getTransform().getSize().at(0)/2);
	    myEntityImage.setY(position.at(1) - myEntity.getTransform().getSize().at(1)/2);
	}
	
	/**Sets the value of the imageview
	 * @param rotation	Rotation in degrees
	 */
	private void setRotate(double rotation) {
		myEntityImage.setRotate(rotation);
	}
	
	/**
	 * Sets the size (width, height) of the imageview
	 * @param size		Size of the imageview.(1,1) is standard scale
	 */
	private void setSize(Vector size) {
		myEntityImage.setFitWidth(size.at(0));
		myEntityImage.setFitHeight(size.at(1));
	}
	
	
	/**
	 * set imageview visibility
	 * @param vis	
	 */
	public void setVisible(boolean vis) {

	    myEntityImage.setVisible(vis);
	}
	
	/**
	 * set imageview transparency to mouse click
	 * @param trans
	 */
	public void setMouseTrans(boolean trans) {

	    myEntityImage.setMouseTransparent(trans);
	}
	
	/**
	 * get imageview 
	 * @return imageview
	 */
	public EntityImage getImage() {

		return myEntityImage;
	}
	
	/**
	 * set imageview
	 */
	public void setImage(Image newImage) {

	    myEntityImage.setImage(newImage);

	}

}
