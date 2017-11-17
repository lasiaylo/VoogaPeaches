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
	 
	public Render(String name) {
		//simple implementation for rendering, need the true renderer to render instead of name search
		try {
			myImageView = new ImageView(new Image(name));
		}
		// this is not permanent implementation, so simply print out the error
		catch(NullPointerException e) {
			System.out.println(e);
		}
	}
	 
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
	 * Sets the scale (width, height) of the imageview
	 * @param scale		Scale of the imageview.(1,1) is standard scale
	 */
	public void setScale(Vector scale) {
		myImageView.setFitWidth(scale.at(0));
		myImageView.setFitHeight(scale.at(1));
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
