package engine.events;

import database.firebase.TrackableObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Consumer;

/**
 * An abstract class that represents something to act on an Event
 * @author ramilmsh
 * @author Albert
 */
public abstract class Evented extends TrackableObject {
    private Map<String, HashSet<Consumer<Event>>> callbacks;

    /**
     * Specify to execute @param callback on being sent an Event
     * of type @param type
     * @param type      type of event mapped to @param callback
     * @param callback  consumer to be executed when event type matches @param type
     */
    public void on(String type, Consumer<Event> callback) {
        if (callbacks == null)
            callbacks = new HashMap<>();

        if (!callbacks.containsKey(type))
            callbacks.put(type, new HashSet<>());

        callbacks.get(type).add(callback);
    }

    /**
     * Specify to execute @param callback on being sent an Event
     * of type specified in EventType
     * @param type      EventType mapped to @param callback
     * @param callback  consumer to be executed when event type matches @param type
     */
    public void on(EventType type, Consumer<Event> callback) {
        on(type.getType(), callback);
    }

    /**
     * Removes an @param consumer from the @param type mapping
     * @param type      type mapped to @param callback
     * @param callback  consumer to be removed
     */
    public void off(String type, Consumer<Event> callback) {
        if (callbacks != null && callbacks.containsKey(type))
            callbacks.get(type).remove(callback);
    }

    /**
     *  Execute the callbacks mapped to the event type of @param event
     * @param event     event sent to evented
     */
    public void dispatchEvent(Event event) {
        if (callbacks == null) {
            callbacks = new HashMap<>();
            return;
        } else if(!callbacks.containsKey(event.getType())) {
            callbacks.put(event.getType(), new HashSet<>());
            return;
        }
        for (Consumer<Event> callback : callbacks.get(event.getType())) {
            callback.accept(event);
        }
    }

    /**
     * clears the callback map
     */
    protected void clear() {
        if(callbacks != null) {
            callbacks.clear();
        }
    }
}
