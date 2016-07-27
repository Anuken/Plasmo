package net.pixelstatic.plasmo.systems;

import net.pixelstatic.plasmo.Plasmo;
import net.pixelstatic.plasmo.entities.Collidable;
import net.pixelstatic.plasmo.entities.Entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ObjectSet;

public class CollisionSystem extends EntitySystem{
	ObjectSet<Long> collided = new ObjectSet<Long>();
	
	@Override
	public void process(Iterable<Entity> entities){
		collided.clear();
		
		for(Entity entity : entities){
			if(!(entity instanceof Collidable) || Plasmo.i.removingEntities.contains(entity)) continue;
			if(!entity.loaded()) continue;
			collided.add(entity.id);
			Collidable a = (Collidable)entity;
			
			Rectangle.tmp.setSize(a.hitboxSize());
			
			Rectangle.tmp.setCenter(entity.x, entity.y);
			
			for(Entity other : entities){
				if(!(other instanceof Collidable) || other == entity || collided.contains(other.id)) continue;
				
				Collidable b = (Collidable)other;
				if(!a.collides(other) || !b.collides(entity) ||  Plasmo.i.removingEntities.contains(other)) continue;
				
				
				Rectangle.tmp2.setSize(b.hitboxSize());
				
				Rectangle.tmp2.setCenter(other.x, other.y);
				
				if(Rectangle.tmp.overlaps(Rectangle.tmp2)){
					a.collided(other);
					b.collided(entity);
				}
					
			}
			
		}
	}

}
