
{ Entity entity, Map<String, Object> bindings, Event event ->
	
	int i = Math.random()
	entity.setProperty("vx", i*bindings.get("velocity"))
	i = Math.random()
	entity.setProperty("vy", i*bindings.get("velocity"))
}
