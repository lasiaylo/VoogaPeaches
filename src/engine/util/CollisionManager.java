package engine.util;

import engine.Engine;
import engine.entities.Entity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A class that tells Entities what other Entities they have collided with
 * @author Albert
 */
public class CollisionManager {
    private Engine myEngine;

    /**
     * Creates a new CollisionManager
     * @param engine    Engine holding everything together (needed for object list)
     */
    public CollisionManager(Engine engine) {
        myEngine = engine;
    }

    /**
     * @param checkEntity   Entity to search for collisions over
     * @return              a List of Entities who have collided with checkEntity
     */
    public List<Entity> getCollided(Entity checkEntity) {
        List<Entity> sector = myEngine.getSector(checkEntity);
        List<Entity> collidedEntities = new LinkedList<>();

        if(!checkEntity.isMoving()) {
            return collidedEntities;
        }

        for(Entity e : sector) {
            if(intersects(checkEntity, e)) {
                collidedEntities.add(e);
            }
        }

        return collidedEntities;
    }

    /**
     * @param check Entity being looped over
     * @param other Other entity that checks collision
     * @return      whether or not the Entities have collided
     */
    private boolean intersects(Entity check, Entity other) {
        double centerDistance = check.getPosition().subtract(other.getPosition()).norm();
        double radDistance = check.getMyHitBox().getRadius() + other.getMyHitBox().getRadius();
        return centerDistance < radDistance;
    }
}
