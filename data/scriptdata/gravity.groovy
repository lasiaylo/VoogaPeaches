package scriptdata

import engine.entities.Entity
import engine.events.AccelerateEvent

entity = (Entity) entity

double gx = (Double) entity.getProperty("gx")
double gy = (Double) entity.getProperty("gy")
double mass = (Double) entity.getProperty("mass")

new AccelerateEvent(gx.doubleValue() / mass.doubleValue() * tickEvent.getDt(), gy.doubleValue() / mass.doubleValue() * tickEvent.getDt()).fire(entity as Entity)
