package engine.entities;

import engine.collisions.HitBox;
import engine.events.Event;
import engine.events.Evented;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;


/**
 * Basic game object
 *
 * @author ramilmsh
 * @author Albert
 */
public class Entity extends Evented {
    private Entity parent;

    private Collection<Entity> children;
    private Collection<HitBox> hitBoxes = new ArrayList<>();
    private Group group;

    /**
     * Create entity as root
     */
    public Entity() {
        group = new Group();
        children = new HashSet<>();
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

    public void add(Node node) {
        group.getChildren().add(node);
    }

    public void add(Entity entity) {
        children.add(entity);
        add(entity.getNodes());
    }

    public Node getNodes() {
        return group;
    }

    public Collection<Entity> getChildren() {
        return children;
    }

    public void addHitBox(HitBox hitBox) {
        hitBoxes.add(hitBox);
        group.getChildren().addAll(hitBox.getShapes());
    }
}
