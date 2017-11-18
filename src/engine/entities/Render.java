package engine.entities;

import engine.util.FXProcessing;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.math.num.Vector;

/**Wrapper Class for Entity's image
 * @author lasia
 *
 */
public class Render {
	private ImageView myImageView;
	 
	public Render() {
	}
	 
	public void displayUpdate(Transform transform) {
		setPosition(transform.getPosition());
		setRotate(transform.getRotation());
		setScale(transform.getScale());
	}
	
	/**
	 * @param position	new position of the Imageview
	 */
	private void setPosition(Vector position) {
		myImageView.setX(FXProcessing.getXImageCoord(position.at(0), myImageView));
	    myImageView.setY(FXProcessing.getYImageCoord(position.at(1), myImageView));
	}
	
	/**Sets the value of the imageview
	 * @param rotation	Rotation in degrees
	 */
	private void setRotate(double rotation) {
		myImageView.setRotate(rotation);
	}
	
	/**
	 * Sets the scale (width, height) of the imageview
	 * @param scale		Scale of the imageview.(1,1) is standard scale
	 */
	private void setScale(Vector scale) {
		myImageView.setFitWidth(myImageView.getFitWidth() * scale.at(0));
		myImageView.setFitHeight(myImageView.getFitHeight() * scale.at(1));
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
