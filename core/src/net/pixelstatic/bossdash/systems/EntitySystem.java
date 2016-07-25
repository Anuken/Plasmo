package net.pixelstatic.bossdash.systems;

import net.pixelstatic.bossdash.entities.Entity;

public abstract class EntitySystem{
	public abstract void process(Iterable<Entity> entities);
}
