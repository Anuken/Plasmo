package net.pixelstatic.plasmo.systems;

import net.pixelstatic.plasmo.entities.Entity;

public abstract class EntitySystem{
	public abstract void process(Iterable<Entity> entities);
}
