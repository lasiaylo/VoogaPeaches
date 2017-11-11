package engine.entities;

import java.util.List;

import engine.scripts.IScript;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import util.math.num.Vector;

public class Background extends Entity{
	private Vector myPosition;
	private Vector myVelocity;
	private Vector myAcceleration;
	private Circle myHitBox;
	private ImageView myImageView;
	private boolean isStatic;
	private List<IScript> myScripts;

	public Background(Vector pos, List<IScript> scripts, Image image) {
		super(pos, scripts, image);
		isStatic = true;
		
		
	}
	
	public Background(double x, double y, List<IScript> scripts, Image image) {
		this(new Vector(x, y), scripts, image);
	}

}
