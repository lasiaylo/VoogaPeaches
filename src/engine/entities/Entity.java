package engine.entities;

import com.google.gson.annotations.Expose;
import engine.collisions.HitBox;
import engine.events.Evented;
import engine.events.SubstituteEvent;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Basic game object
 *
 * @author ramilmsh
 * @author Albert
 */
public class Entity extends Evented {
    @Expose private List<Entity> children;
    @Expose private Map<String, Object> properties;
    @Expose private List<HitBox> hitBoxes;

    private String dbPath;
    private Group group;
    private Entity parent;
    private Entity root;

    /**
     * Create entity as root
     */
    public Entity() {
        group = new Group();
        children = new ArrayList<>();
        properties = new HashMap<>();
        HashMap<String,HashMap<String,String>> defaultScripts = new HashMap<>();
        HashMap<String,String> loader = new HashMap<>();
        loader.put("empty","empty");
        defaultScripts.put("loader",loader);
        properties.put("scripts", defaultScripts);
        hitBoxes = new ArrayList<>();
    }

    /**
     * Create entity as a child
     *
     * @param parent: entities parent entity
     */
    public Entity(Entity parent) {
        this();
        if(parent == null) return;
        addTo(parent);
    }

    /**
     * Get entities parent
     *
     * @return (Entity.parent) or null, if root
     */
    public Entity getParent() { return parent; }

    /**
     * @return a map of properties this entity contains
     */
    public Map<String, Object> getProperties(){ return properties; }

    /**
     * Adds a new javafx node to the entity's group
     * @param node  javafx node to be added
     */
    public void add(Node node) { group.getChildren().add(node); }

    /**
     * Adds a new entity to this entity's children
     * @param entity    entity to be added
     */
    public void add(Entity entity) {
        children.add(entity);
        add(entity.getNodes());
        entity.addTo(this);
    }

    /**
     * Removes param from entity javafx group
     * @param node  Node to be removed
     */
    public void remove(Node node) { group.getChildren().remove(node); }

    /**
     * Adds this Entity to param parent
     * @param parent    parent to add this entity to
     * @return this entity
     */
    public Entity addTo(Entity parent) {
        if(parent != null) {
            this.parent = parent;
            if(!parent.getNodes().getChildren().contains(this.group)) {
                parent.getNodes().getChildren().add(group);
            }
            if(!parent.getChildren().contains(this)) parent.getChildren().add(this);
        }
        return this;
    }

    /**
     * clear layer but leave the placeholder inside the group
     */
    public void clearLayer() {
        children.clear();
        group.getChildren().subList(1, group.getChildren().size()).clear();
    }

    /**
     * Removes param from this entity's
     * @param entity
     */
    public void remove(Entity entity) {
        children.remove(entity);
        remove(entity.getNodes());
    }

    /**
     * @return the root of this entity
     */
    public Entity getRoot() { return root; }

    /**
     * @return the javafx group contained within this entity
     */
    public Group getNodes() { return group; }

    /**
     * Returns a list of children
     * @return  a list of children to this entity
     */
    public List<Entity> getChildren() { return children; }

    /**
     * @param name  name of property
     * @return      an Object mapped to by param name in properties
     */
    public Object getProperty(String name) { return properties.getOrDefault(name, null); }

    /**
     * Sets a property in the properties map
     * @param name      name of property
     * @param property  property
     */
    public void setProperty(String name, Object property) { properties.put(name, property);    }

    /**
     * @return a list of hitboxes contained within this entity
     */
    public List<HitBox> getHitBoxes() { return hitBoxes; }

    /**
     * Adds a hitbox to the list of hitboxes & javafx group
     * @param hitbox    hitbox to be added
     */
    public void addHitBox(HitBox hitbox) {
        hitBoxes.add(hitbox);
        group.getChildren().add(hitbox.getHitbox());
    }

    /**
     * clear all consumer callbacks and reexecute all scripts
     */
    private void executeScripts() {
        clear();
        EntityScriptFactory.executeScripts(this);
    }

    /**
     * Performs a substitution on this entity. Copies all properties of this
     * entity to a new instance and reinitializes the new entity; retains same
     * UID in database
     * @return  new Entity that was substituted for old entity
     */
    public Entity substitute() {
        clear();
        if(parent != null) this.parent.remove(this);
        stopTrackingTrackableObject(this.UIDforObject());
        Entity entity = new Entity(parent);
        entity.properties = properties;
        entity.replaceUID(this.UIDforObject());

        entity.properties = properties;
        entity.hitBoxes = hitBoxes;
        try {
            parent.getNodes().getChildren().remove(group);
        } catch(NullPointerException e){
            // do nothing
        }

         if(!children.isEmpty())
            for(Entity child : children)
                entity.add(child.substitute());

        entity.initialize();
        new SubstituteEvent(entity).fire(entity);
        return entity;
    }

    @Override
    public void initialize() {
        for(Entity child : children) {
             child.parent = this;
        }
        clear();
        executeScripts();
    }
}
