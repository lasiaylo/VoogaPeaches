package engine.entities;

import engine.events.ClickEvent;
import engine.events.Event;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {
    @Test
    void getParent() {
        Entity entity = new Entity();
        assertNotEquals(entity, null);
        entity.on("click", System.out::println);
        new ClickEvent().fire(entity);
    }

    @Test
    void add() {
    }

    @Test
    void add1() {
    }

    @Test
    void getNodes() {
    }

}