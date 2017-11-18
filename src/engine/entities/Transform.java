package engine.entities;

import util.math.num.Vector;

/**Wrapper Class for Entity's position/location/scale
 * @author lasia
 *
 */
public class Transform {
	private Vector myPosition;
	private double myRotation;
	private Vector myScale;
	private Vector mySize; //need to implement this for map to determine whether inside viewport
	


	/**Creates a new Transform
	 * @param position
	 */
	public Transform(Vector position) {
		myPosition = position;
		myScale = new Vector(1,1);
	}
	
	/**Creates a new Tranform
	 * @param position
	 * @param rotation
	 */
	public Transform(Vector position, double rotation) {
		this(position);
		myRotation = rotation;
	}
	
    /**
     * @return Vector position of this entity
     */
	public Vector getPosition() {

	    return myPosition;
	}
	
    /**
     * @param newPos position for this entity
     */
	public void setPosition(Vector newPos) {

	    this.myPosition = newPos;
	}
	
	/**
	 * @return Rotation of this entity in degrees
	 */
	public double getRotation() {

	    return myRotation;
	}
	
	/**
	 * @param rotation of this entity in degrees
	 */
	public void setRotation(double rotation) {

		this.myRotation = this.myRotation + rotation;
	}
	
	public Vector getScale() {
		return myScale;
	}

	public void setScale(Vector scale) {

	    this.myScale = this.myScale.add(scale);
	}
}
