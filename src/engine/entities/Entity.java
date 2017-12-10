package engine.entities;

import com.google.gson.annotations.Expose;
import database.fileloaders.ScriptLoader;
import database.firebase.DatabaseConnector;
import database.firebase.TrackableObject;
import engine.collisions.HitBox;
import engine.events.*;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import util.ErrorDisplay;
import util.exceptions.ObjectIdNotFoundException;
import util.math.num.Vector;
import util.pubsub.PubSub;
import util.pubsub.messages.EntityPass;

import java.util.*;


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
    public Entity getParent() {
        return parent;
    }

    public Map<String, Object> getProperties(){
    	return properties;
    }

    public void add(Node node) {
        group.getChildren().add(node);
    }

    public void add(Entity entity) {
        children.add(entity);
        add(entity.getNodes());
        entity.addTo(this);
    }
    public void remove(Node node) {
        group.getChildren().remove(node);
    }

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
        children.remove(entity);
        remove(entity.getNodes());
    }

    public Entity getRoot() { return root; }

    public Group getNodes() {
        return group;
    }

    public List<Entity> getChildren() {
        return children;
    }

    public Object getProperty(String name) {
        return properties.getOrDefault(name, null);
    }

    public void setProperty(String name, Object property) {
        properties.put(name, property);
    }

    public List<HitBox> getHitBoxes() {
        return hitBoxes;
    }

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
        System.out.println(UID);
        Entity entity = new Entity(parent);
        entity.UID = this.UID;
        DatabaseConnector<Entity> db = new DatabaseConnector<>(Entity.class);
//        try {
//            db.removeFromDatabase(this);
//            db.addToDatabase(entity);
//        } catch (ObjectIdNotFoundException e) {
//            e.printStackTrace();
//            new ErrorDisplay("Oh no!", "We can't recreate this entity!").displayError();
//        }

        entity.properties = properties;
        entity.hitBoxes = hitBoxes;
        try {
            parent.getNodes().getChildren().remove(group);
        } catch(NullPointerException e){
            // do nothing
        }

        if(!children.isEmpty()) {
            for(Entity child : children)
                entity.add(child.substitute());
        }

        entity.initialize();
        fireInitialImageEvents(entity);

        return entity;
    }

    private void fireInitialImageEvents(Entity entity) {
        if(!((boolean) getProperties().getOrDefault("bg", false))) {
            new InitialImageEvent(new Vector((double) getProperty("width"), (double) getProperty("height")),
                    new Vector((double) getProperty("x"), (double) getProperty("y"))).fire(this);
            new KeyPressEvent(KeyCode.BACK_SPACE).fire(this);
            new ClickEvent(false).fire(this);
            new MouseDragEvent(false, false).fire(entity);
        }
    }


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
        DatabaseConnector<Entity> connector = new DatabaseConnector<>(Entity.class);
        try {
            connector.addToDatabase(this);
        } catch(Exception e) {

        }
    }
}