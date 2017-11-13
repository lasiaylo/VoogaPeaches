package engine.util;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.shape.Shape;
import util.math.num.Vector;

/**
 * Represents a collision hitbox
 * 
 * @author lasia
 * @author Albert
 */
public class HitBox {
	private String tag;
	private List<String> visitorTags;
	private List<Shape> myShapes;
	private Vector myPosition;

	/**
	 * Creates a new Hitbox
	 * 
	 * @param shapes
	 *            list of JavaFX Shapes to be added to hitbox
	 */
	public HitBox(List<Shape> shapes, Vector pos) {
		myShapes = shapes;
		myPosition = pos;
	}

	/**
	 * Creates a new HitBox
	 * 
	 * @param shape
	 *             javafx shape to be hitbox
	 */
	public HitBox(Shape shape, Vector pos) {
		this(new ArrayList<Shape>() {
			{
				add(shape);
			}
		}, pos);
	}

	/**
	 * @param other
	 * @return Boolean on whether it intersects
	 */
	public boolean checkIntersect(HitBox other) {
		List<Shape> otherBox = other.getShapes();

		for (Shape myShape : myShapes) {
			for (Shape otherShape : otherBox) {
				if (myShape.getBoundsInParent().intersects(otherShape.getBoundsInParent())) {

					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Add a visitor's tag to the list of tags
	 * @param otherTag
	 */
	public void addVisitor(String otherTag) {
		visitorTags.add(otherTag);
	}

	/**
	 * @return	a list of the tags of entities that have collided with this hitbox
	 */
	public List<String> getVisitors() {
		return visitorTags;
	}

	/**
	 * @return	a list of shapes that make up this hitbox
	 */
	public List<Shape> getShapes() {
		return myShapes;
	}

	/**
	 * Set the position of this hitbox
	 * @param pos	new position of hitbox
	 */
	public void setPosition(Vector pos) {
		Vector translation = pos.subtract(myPosition);
		myPosition = pos;
		for(Shape s : myShapes) {
			s.setLayoutX(s.getLayoutX() + translation.at(0));
			s.setLayoutY(s.getLayoutY() + translation.at(1));
		}
	}

	/**
	 * @return	the current position of this vector
	 */
	public Vector getPosition() {
		return myPosition;
	}
}
