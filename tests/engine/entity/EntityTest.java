package engine.entity;

import engine.entities.Entity;
import engine.events.ClickEvent;
import org.junit.jupiter.api.Test;

class EntityTest {
    @Test
    void getParent() {
        Entity entity = new Entity();
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