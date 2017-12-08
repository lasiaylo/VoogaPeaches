package engine.entities;

import com.google.gson.annotations.Expose;
import database.fileloaders.ScriptLoader;
import engine.collisions.HitBox;
import engine.events.ClickEvent;
import engine.events.Evented;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import javafx.scene.Group;
import javafx.scene.Node;
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
        parent.getNodes().getChildren().add(group);
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

    public Group getNodes() {
        return group;
    }

    public List<Entity> getChildren() {
        return children;
    }

    public Object getProperty(String name) {
        return properties.get(name);
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

    private void executeScripts() {
        Map<String, List<String>> listenActionPair = (Map<String, List<String>>) properties.getOrDefault("scripts", new HashMap<String, List<String>>());
        for (String script : listenActionPair.keySet() ) {
            String code = ScriptLoader.stringForFile(script);
            Binding binding = new Binding();
            binding.setVariable("entity", this);
            binding.setVariable("game", root);
            binding.setVariable("actions", listenActionPair.get(script));
            new GroovyShell(binding).evaluate(code);
        }
    }

    private void setEventListeners() {
//
//        group.setOnMouseClicked(e -> {
//            new ClickEvent().fire(this);
//            System.out.println("set event listeners");
//            PubSub.getInstance().publish("ENTITY_PASS", new EntityPass(this));
//        });

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

      //  for (Entity entity : children)
        //    entity.addTo(this);

        setEventListeners();
        executeScripts();
    }
}