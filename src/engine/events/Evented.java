package engine.events;

import database.firebase.TrackableObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;

public abstract class Evented extends TrackableObject {
    private HashMap<String, HashSet<Consumer<Event>>> callbacks;

    public void on(String type, Consumer<Event> callback) {
        if (callbacks == null)
            callbacks = new HashMap<>();

        if (!callbacks.containsKey(type))
            callbacks.put(type, new HashSet<>());

        callbacks.get(type).add(callback);
    }

    public void off(String type, Consumer<Event> callback) {
        if (callbacks != null && callbacks.containsKey(type))
            callbacks.get(type).remove(callback);
    }

    public void dispatchEvent(Event event) {
        if (!callbacks.containsKey(event.getType()))
            return;

        for (Consumer<Event> callback : callbacks.get(event.getType()))
            callback.accept(event);
    }
}
