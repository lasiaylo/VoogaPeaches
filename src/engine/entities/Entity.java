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

    public Map<String, Object> getProperties(){ return properties; }

    public void add(Node node) { group.getChildren().add(node); }

    public void add(Entity entity) {
        children.add(entity);
        add(entity.getNodes());
        entity.addTo(this);
    }
    public void remove(Node node) { group.getChildren().remove(node); }

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

    public void remove(Entity entity) {
        children.remove(entity);
        remove(entity.getNodes());
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
        if(parent != null) this.parent.remove(this);
        stopTrackingTrackableObject(this.UIDforObject());
        Entity entity = new Entity(parent);
        entity.properties = properties;
        entity.replaceUID(this.UIDforObject());

        try {
           // DatabaseConnector.removeFromDatabasePath(this.getDbPath());
            //DatabaseConnector.addToDatabasePath(entity, this.getDbPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
       // System.out.println(hitBoxes.get(0));
        //hitBoxes.forEach(e -> e.initialize());
        executeScripts();
    }
}
