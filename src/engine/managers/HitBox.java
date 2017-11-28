package engine.managers;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import database.firebase.TrackableObject;
import javafx.scene.shape.Shape;
import util.math.num.Vector;

/**
 * Represents a collision hitbox
 * 
 * @author lasia
 * @author Albert
 */
public class HitBox extends TrackableObject {
	@Expose private String myTag;
	@Expose private List<String> visitorTags;
	@Expose private List<Shape> myShapes;
	@Expose private Vector myPosition;

	/**
	 * Create a new HitBox from the database
	 */
	public HitBox() {}

	/**
	 * Creates a new Hitbox
	 * 
	 * @param shapes
	 *            list of JavaFX Shapes to be added to hitbox
	 */
	public HitBox(List<Shape> shapes, Vector pos, String tag) {
		myShapes = shapes;
		myPosition = pos;
		visitorTags = new ArrayList<>();
		myTag = tag;
	}

	/**
	 * Creates a new HitBox
	 * 
	 * @param shape
	 *             javafx shape to be hitbox
	 */
	public HitBox(Shape shape, Vector pos, String tag) {
		this(new ArrayList<Shape>() {
			{
				add(shape);
			}
		}, pos, tag);
	}

	public String getTag() {
		return myTag;
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
					this.addVisitor(other.getTag());
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
