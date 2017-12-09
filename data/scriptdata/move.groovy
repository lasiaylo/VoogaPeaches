package scriptdata

import engine.entities.Entity
import engine.events.MoveEvent

entity = (Entity) entity

double vx = (Double) entity.getProperty("vx")
double vy = (Double) entity.getProperty("vy")

new MoveEvent(vx.doubleValue() * event.getDt(), vy.doubleValue() * event.getDt()).fire(entity as Entity)
