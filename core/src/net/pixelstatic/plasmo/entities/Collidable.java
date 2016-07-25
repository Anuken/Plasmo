package net.pixelstatic.plasmo.entities;

public interface Collidable{
	public float hitboxSize();
	
	public boolean collides(Entity other);
	public void collided(Entity other);
}
