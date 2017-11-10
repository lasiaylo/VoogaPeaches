package engine.scripts;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.shape.Shape;


/**Represents a collision hitbox
 * 
 * @author lasia
 * @author Albert
 */
public class HitBox {
	private String tag;
	private String visitorTag;
	private List<Shape> myShapes;
	
	
	/** Creates a new Hitbox
	 * @param List of JavaFX Shapes
	 */
	public HitBox(List<Shape> shapes) {
		myShapes = shapes;
	}
	
	
	/** Creates a new HitBox
	 * @param JavaFX Shape
	 */
	public HitBox(Shape shape ) {
		this(new ArrayList<Shape>() {{
			add(shape);
		}});
	}
	
	
	/**
	 * @param HitBox
	 * @return 
	 */
	public boolean intersects(HitBox other) {
		List<Shape> otherBox = other.getShapes();
		
		for(Shape myShape : myShapes ) {
			for(Shape otherShape : otherBox) {
				if (myShape.getBoundsInParent().intersects(otherShape.getBoundsInParent())) {
					
					return true;
				}
			}
		}
		return false;
	}
	
	public void setVisitor() {
		
	}
	public List<Shape> getShapes() {
		return myShapes;
	}
}
