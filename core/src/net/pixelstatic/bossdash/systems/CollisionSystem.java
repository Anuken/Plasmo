package net.pixelstatic.bossdash.systems;

import net.pixelstatic.bossdash.entities.Collidable;
import net.pixelstatic.bossdash.entities.Entity;

import com.badlogic.gdx.math.Rectangle;

public class CollisionSystem extends EntitySystem{

	@Override
	public void process(Iterable<Entity> entities){
		for(Entity entity : entities){
			if(!(entity instanceof Collidable)) continue;
			if(!entity.loaded()) continue;
			Collidable a = (Collidable)entity;
			
			Rectangle.tmp.setSize(a.hitboxSize());
			
			Rectangle.tmp.setPosition(entity.x, entity.y);
			
			for(Entity other : entities){
				if(!(other instanceof Collidable) || other == entity) continue;
				
				Collidable b = (Collidable)other;
				if(!a.collides(other) || !b.collides(entity)) continue;
				
				
				Rectangle.tmp2.setSize(b.hitboxSize());
				
				Rectangle.tmp2.setPosition(other.x, other.y);
				
				if(Rectangle.tmp.overlaps(Rectangle.tmp2)){
					a.collided(other);
					b.collided(entity);
				}
					
			}
		}
	}

}
