package engine.entities;

import com.google.gson.annotations.Expose;
import database.scripthelpers.ScriptLoader;
import engine.events.ClickEvent;
import engine.events.Evented;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import javafx.scene.Group;
import javafx.scene.Node;
import org.json.JSONArray;

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
//    private Entity() {}


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
    }

    /**
     * Create entity as a child
     *
     * @param parent: entities parent entity
     */
    public Entity(Entity parent) {
        this();
        this.parent = parent;
        parent.add(this);
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
        entity.addTo(this);
    }
    public void remove(Node node) {
        group.getChildren().remove(node);
    }

    public Entity addTo(Entity parent) {
        this.parent = parent;
        return this;
    }

    public void remove(Entity entity) {
        children.remove(entity);
        remove(entity.getNodes());
    }

    public Group getNodes() {
        return group;
    }

    public Iterator<Entity> getChildren() {
        return children.iterator();
    }

    public int getChildrenSize() {
        return children.size();
    }

    public Entity getChildren(int index) {
        return children.get(index);
    }

    public Object getProperty(String name) {
        return properties.get(name);
    }

    private void executeScripts() {
        for (Object script : (List) properties.get("scripts")) {
            String code = ScriptLoader.stringForFile((String) script);
            Binding binding = new Binding();
            binding.setVariable("entity", this);
            binding.setVariable("game", null);
            new GroovyShell(binding).evaluate(code);
        }
    }

    private void setEventListeners() {
        group.setOnMouseClicked(e -> new ClickEvent().fire(this));
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

        for (Entity entity : children)
            entity.parent = this;

        setEventListeners();
        executeScripts();
    }
}