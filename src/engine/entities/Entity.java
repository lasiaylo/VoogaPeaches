package engine.entities;

import engine.events.Evented;
import javafx.scene.Node;
import javafx.scene.media.Media;

import java.util.HashSet;


/**
 * Basic game object
 */
public class Entity extends Evented {
    private Entity parent;
    private HashSet<Entity> children;

    private Node node;
    private Media media;

    /**
     * Create entity as root
     */
    public Entity() {
    }

    /**
     * Create entity as a child
     *
     * @param parent: entities parent entity
     */
    public Entity(Entity parent) {
        this();
        this.parent = parent;
    }

    /**
     * Get entities parent
     *
     * @return (Entity.parent) or null, if root
     */
    public Entity getParent() {
        return parent;
    }

    /**
     * Create a visual
     *
     * @param node: JavaFX Node
     */
    public void setNode(Node node) {
        this.node = node;
    }

    /**
     * Get the visual
     *
     * @return node
     */
    public Node getNode() {
        return this.node;
    }

    /**
     * Set audio
     *
     * @param media: audio object
     */
    public void setMedia(Media media) {
        this.media = media;
    }

    /**
     * Get media
     *
     * @return media object
     */
    public Media getMedia() {
        return this.media;
    }
}
