package engine.entities;

import engine.util.FXProcessing;
import javafx.scene.image.ImageView;
import util.math.num.Vector;

/**Wrapper Class for Entity's image
 * @author lasia
 *
 */
public class Render {
	 private ImageView myImageView;
	 
	public void displayUpdate(Transform transform) {
		setPosition(transform.getPosition());
		setRotate(transform.getRotation());
		setScale(transform.getScale());
	}
	
	/**
	 * @param position	new position of the Imageview
	 */
	public void setPosition(Vector position) {
		myImageView.setX(FXProcessing.getXImageCoord(position.at(0), myImageView));
	    myImageView.setY(FXProcessing.getYImageCoord(position.at(1), myImageView));
	}
	
	/**Sets the value of the imageview
	 * @param rotation	Rotation in degrees
	 */
	public void setRotate(double rotation) {
		myImageView.setRotate(rotation);
	}
	
	/**
	 * change the size (width, height) of the imageview
	 * @param size
	 */
	public void setScale(Vector size) {
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
	
}
