package engine.events;

import engine.entities.Entity;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * An abstract class that represents all communication to the entity, whether because of an action such as a collision
 * or mouse press, the need to set up a map in authoring, etc.
 *
 * Inspired by pubsub
 * @author ramilmsh
 * @author Albert
 */
public abstract class Event {
    private String type;
    private Entity target;

    /**
     * Creates a new Entity with identifier tag param
     * @param type  identifier of Event
     */
    public Event(String type) {
        this.type = type;
    }

    /**
     * @return the type of this event
     */
    public String getType() {
        return type;
    }

    /**
     * Send this event to the target entity
     * @param target    Entity to communicate with
     * @return          this
     */
    public Event fire(Entity target) {
        target.dispatchEvent(this);
        return this;
    }

    /**
     * Recursively send this event down the tree starting with param
     * @param target    root Entity to send event down
     * @return          this
     */
    public Event recursiveFire(Entity target) {
        if(target == null) {
            return this;
        }

        fire(target);
        if(target.getChildren().isEmpty() || target.getChildren() == null) {
            return this;
        }

        Iterator<Entity> iter = target.getChildren().iterator();
        try{
            iter.forEachRemaining(e -> recursiveFire(e));
        } catch(ConcurrentModificationException c){
            //do nothing
        }
        return this;
    }
}
