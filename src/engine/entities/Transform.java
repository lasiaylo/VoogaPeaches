package engine.entities;

import util.math.num.Vector;

/**Wrapper Class for Entity's position/location/scale
 * @author lasia
 *
 */
public class Transform {
	private Vector myPosition = new Vector(0, 0);
	private double myRotation = 0;
	private Vector mySize = new Vector(50, 50);
	


	/**Creates a new Transform
	 * @param position
	 */
	public Transform(Vector position) {
		myPosition = position;
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
	
	public double getXPosition() {
		return myPosition.at(0);
	}
	
	public void setXPosition(double newPos) {
		myPosition.at(0, newPos);
	}
	
	public double getYPosition() {
		return myPosition.at(1);
	}
	
	public void setYPosition(double newPos) {
		myPosition.at(1,newPos);
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
     * set scale factor
     * @param scale
     */
	public void setScale(Vector scale) {

	    this.mySize = new Vector(mySize.at(0)*scale.at(0), mySize.at(1)*scale.at(1));

	}


    /**
     * get current size
     * @return
     */
    public Vector getSize() {
        return mySize;
    }
}
