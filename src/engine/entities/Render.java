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
	 
	/**
	 * apply the position of entity to its imageview
	 */
	public void displayUpdate(Vector position) {
		myImageView.setX(FXProcessing.getXImageCoord(position.at(0), myImageView));
	    myImageView.setY(FXProcessing.getYImageCoord(position.at(1), myImageView));
	}
	
	/**
	 * change the size (width, height) of the imageview
	 * @param size
	 */
	public void resize(Vector size) {
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
