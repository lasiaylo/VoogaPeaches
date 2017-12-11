package engine.entities;

import com.google.gson.annotations.Expose;
import engine.collisions.HitBox;
import engine.events.*;
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

    public Map<String, Object> getProperties(){ return properties; }

    public void add(Node node) { group.getChildren().add(node); }

    public void add(Entity entity) {
        children.add(entity);
        add(entity.getNodes());
        entity.addTo(this);
    }
    public void remove(Node node) { group.getChildren().remove(node); }

    public Entity addTo(Entity parent) {
        this.parent = parent;
        parent.getNodes()
                .getChildren()
                .add(group);
        parent.getChildren().add(this);
        return this;
    }

    /**
     * clear layer but leave the placeholder inside the group
     */
    public void clearLayer() {
        children.clear();
        group.getChildren().subList(1, group.getChildren().size()).clear();
    }

    public void remove(Entity entity) {
        System.out.println(this.getNodes().getChildren().size());
        children.remove(entity);
        remove(entity.getNodes());
        System.out.println(this.getNodes().getChildren().size());
    }

    public Entity getRoot() { return root; }

    public Group getNodes() { return group; }

    public List<Entity> getChildren() { return children; }

    public Object getProperty(String name) { return properties.getOrDefault(name, null); }

    public void setProperty(String name, Object property) { properties.put(name, property);    }

    public List<HitBox> getHitBoxes() { return hitBoxes; }

    public void addHitBox(HitBox hitbox) {
        hitBoxes.add(hitbox);
        group.getChildren().add(hitbox.getHitbox());
    }

    public void executeScripts() {
        clear();
        EntityScriptFactory.executeScripts(this);
    }

    public Entity substitute() {
        clear();
        Entity entity = new Entity(parent);
        entity.properties = properties;
        return EntitySubstituter.substitute(this, entity);
    }

    String getDbPath() { return dbPath; }

    void setDbPath(String dbPath) { this.dbPath = dbPath; }

    @Override
    public void initialize() {
        if (root == null)
            if (parent != null)
                for (Entity entity : children)
                    entity.root = this;
            else
                for (Entity entity : children)
                    entity.root = root;

        executeScripts();
    }
}
