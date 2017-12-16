package filedata.scripts

import engine.entities.Entity
import engine.events.Event

{ Entity entity, Map<String, Object> bindings, Event event = null ->
	double x = (double) entity.getProperty("x")
	double y = (double) entity.getProperty("y")
	if(x < 5 || x > 1595 || y < 5 || y > 795){
		try{
			entity.getParent().remove(entity)
		} catch(Exception e){}
	}
}
