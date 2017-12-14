package util.pubsub.messages;

import engine.EntityManager;

public class EntityManagerMessage extends Message {
    private EntityManager entityManager;
    public EntityManagerMessage(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
