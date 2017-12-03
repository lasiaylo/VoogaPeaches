package engine.entities;

import com.google.gson.annotations.Expose;
import database.scripthelpers.ScriptLoader;
import engine.events.ClickEvent;
import engine.events.Evented;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.util.*;


/**
 * Basic game object
 *
 * @author ramilmsh
 * @author Albert
 */
public class Entity extends Evented {
    @Expose
    private Collection<Entity> children;

    private Entity parent;
    private Map<String, Object> properties;
    private Group group;

    /**
     * Create entity as root
     */
    public Entity() {
        group = new Group();
        children = new HashSet<>();
        properties = new HashMap<>();

        setEventListeners();
        executeScripts();
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

    public Group getNodes() {
        return group;
    }

    public Iterator<Entity> getChildren() {
        return children.iterator();
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
}