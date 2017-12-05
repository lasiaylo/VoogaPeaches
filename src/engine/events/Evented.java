package engine.events;

import database.firebase.TrackableObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Consumer;

public abstract class Evented extends TrackableObject {
    private Map<String, HashSet<Consumer<Event>>> callbacks;

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
        System.out.println(callbacks);
        // does not correctly do null check
        if (!callbacks.containsKey(event.getType()))
            return;

        for (Consumer<Event> callback : callbacks.get(event.getType()))
            callback.accept(event);
    }
}
