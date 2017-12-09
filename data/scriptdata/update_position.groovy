import engine.collisions.HitBox
import engine.entities.Entity

entity = (Entity) entity

entity.setProperty("x", ((Double) entity.getProperty("x")).doubleValue() + event.dx())
entity.setProperty("y", ((Double) entity.getProperty("y")).doubleValue() + event.dy())

entity.getNodes().relocate(((Double) entity.getProperty("x")).doubleValue(),
        ((Double) entity.getProperty("y")).doubleValue())

for (HitBox hitBox : entity.getHitBoxes())
    hitBox.moveHitBox((double) entity.getProperty("x"), (double) entity.getProperty("y"))
