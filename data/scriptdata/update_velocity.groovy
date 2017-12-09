package scriptdata

import engine.entities.Entity

entity = (Entity) entity

entity.setProperty("vx", ((Double) entity.getProperty("vx")).doubleValue() + event.dvx())
entity.setProperty("vy", ((Double) entity.getProperty("vy")).doubleValue() + event.dvy())
