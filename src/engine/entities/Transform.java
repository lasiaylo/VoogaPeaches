package engine.entities;

import database.firebase.TrackableObject;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import util.math.num.Vector;
import util.pubsub.PubSub;
import util.pubsub.messages.Message;
import util.pubsub.messages.TransformMessage;

import java.util.function.Consumer;

/**Wrapper Class for Entity's position/location/scale
 * @author lasia
 * @author Albert
 *
 */
public class Transform extends TrackableObject {
	private Vector myPosition = new Vector(0, 0);
	private Vector myVelocity = new Vector(0, 0);
	private Vector myAcceleration = new Vector(0, 0);
	private double myRotation = 0;
	private Vector mySize = new Vector(50, 50);
	private PubSub myPubSub;

	/**
	 * Creates a new Transform object from database
	 */
	private Transform() {}

	/**Creates a new Transform
	 * @param position
	 */
	public Transform(Vector position) {
		myPubSub = PubSub.getInstance(); // refactor?
		Consumer<Message> myCallBack = (message) -> {
			TransformMessage tMessage = (TransformMessage) message;
		};
		myPubSub.subscribe(PubSub.Channel.TRANSFORM_MESSAGE, myCallBack);
		myPosition = position;
	}

	public Transform(Vector position, Vector velocity, Vector acceleration) {
		this(position);
		myVelocity = velocity;
		myAcceleration = acceleration;
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
		myPubSub.publish(PubSub.Channel.TRANSFORM_MESSAGE, new TransformMessage(this));
	}

	/**
	 * @return	Vector representing the velocity of the transform
	 */
	public Vector getVelocity() {
		return myVelocity;
	}

	/**
	 * Sets the velocity of the transform to param
	 * @param newVel	Vector representing the new velocity
	 */
	public void setVelocity(Vector newVel) {
		myVelocity = newVel;
		myPubSub.publish(PubSub.Channel.TRANSFORM_MESSAGE, new TransformMessage(this));
	}

	/**
	 * @return	Vector representing the acceleration of the transform
	 */
	public Vector getAcceleration() {
		return myAcceleration;
	}

	/**
	 * Sets the acceleration of the transform to param
	 * @param newAccel	set the acceleration to param
	 */
	public void setAcceleration(Vector newAccel) {
		myAcceleration = newAccel;
		myPubSub.publish(PubSub.Channel.TRANSFORM_MESSAGE, new TransformMessage(this));
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
		myPubSub.publish(PubSub.Channel.TRANSFORM_MESSAGE, new TransformMessage(this));
	}

	/**
	 * get current size
	 * @return
	 */
	public Vector getSize() {
		return mySize;
	}
}
