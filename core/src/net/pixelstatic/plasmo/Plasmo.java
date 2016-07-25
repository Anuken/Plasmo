package net.pixelstatic.plasmo;

import java.util.concurrent.ConcurrentHashMap;

import net.pixelstatic.plasmo.entities.*;
import net.pixelstatic.plasmo.systems.CollisionSystem;
import net.pixelstatic.plasmo.systems.EntitySystem;
import net.pixelstatic.utils.graphics.Textures;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Bloom;
import com.bitfire.utils.ShaderLoader;

public class Plasmo extends ApplicationAdapter{
	public static Plasmo i;
	public ConcurrentHashMap<Long, Entity> entities = new ConcurrentHashMap<Long, Entity>();
	public Array<EntitySystem> systems = new Array<EntitySystem>();
	public SpriteBatch batch;
	public Player player;
	public OrthographicCamera camera;
	public Input input;
	public int zoom = 5;
	public Matrix4 matrix;
	public PostProcessor processor;
	public Bloom bloom;
	public float shaketime = 0;
	public float shakeintensity = 7;
	public float bloomtime = 0;
	public float hittime;
	public boolean dead = false;
	public boolean started = false;
	public BitmapFont font;
	public int lastspawn = 0;
	public ObjectMap<String, Sound> sounds = new ObjectMap<String, Sound>();
	public ObjectMap<String, Integer> framesounds = new ObjectMap<String, Integer>();
	public Array<Star> stars = new Array<Star>();
	int escalation = 3000;
	

	@Override
	public void create(){
		i = this;
		Textures.load("textures/");
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth() / zoom, Gdx.graphics.getHeight() / zoom);
		matrix = new Matrix4();
		input = new Input();
		addStars();
		player = (Player)new Player().add();

		systems.add(new CollisionSystem());

		ShaderLoader.BasePath = "shaders/";

		processor = new PostProcessor(false, true, true);

		bloom = new Bloom(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4);
		processor.addEffect(bloom);
		
		loadSound("laser");
		loadSound("laser2");
		loadSound("wave");
	}
	
	void addStars(){
		for(int i = 0; i < 150; i ++)
			stars.add((Star)new Star().set(MathUtils.random(-900, 900), MathUtils.random(-900, 900)));
	}
	
	void loadSound(String name){
		sounds.put(name, Gdx.audio.newSound(Gdx.files.internal("sounds/" + name + ".wav")));
		framesounds.put(name, 0);
	}
	
	public void playSound(String name){
		playSound(name, 0.05f);
	}
	
	public void playSound(String name, float volume){
		if(framesounds.get(name) > 1) return;
		sounds.get(name).play(volume);
		framesounds.put(name, framesounds.get(name) + 1);
	}

	@Override
	public void render(){
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		for(String key : framesounds.keys())
			framesounds.put(key, 0);

		if(started && !dead) spawnStuff();

		for(Entity entity : entities.values())
			entity.update();

		camera.position.set(player.x, player.y, 0);

		if( !dead){

			if(hittime > 0){
				hittime -= Gdx.graphics.getDeltaTime() * 60f;
			}

			float base = 1f;

			if(bloomtime > 0){
				bloom.setBloomIntesity(base + bloomtime);
				bloomtime -= Gdx.graphics.getDeltaTime() * 60f;
				bloom.setBaseSaturation(0.85f + bloomtime / 5f);
			}else{
				bloom.setBloomIntesity(base);
				bloom.setBaseSaturation(0.85f);
			}

		}else{
			if(bloom.getBloomIntensity() < 20)
			bloom.setBloomIntesity(bloom.getBloomIntensity() + 0.1f);
		}
		if(shaketime > 0){
			camera.position.add(MathUtils.random( -shakeintensity, shakeintensity), MathUtils.random( -shakeintensity, shakeintensity), 0);
			shaketime -= Gdx.graphics.getDeltaTime() * 60f;
		}
		camera.update();

		batch.setProjectionMatrix(camera.combined);

		processor.capture();
		batch.begin();

		input.update();
		
		for(Star star : stars){
			star.update();
			star.draw(batch);
		}

		for(EntitySystem system : systems){
			system.process(entities.values());
		}

		for(Entity entity : entities.values()){
			if(entity.loaded())
			entity.draw(batch);
		}

		batch.end();
		processor.render();

		batch.setProjectionMatrix(matrix);

		batch.begin();
		drawGUI();
		batch.end();
	}

	void spawnStuff(){
		spawn(Enemy1.class, 0.004);
		
		spawnGroup(Enemy2.class, 0.002, MathUtils.random(2, 5));
		
		spawn(Enemy4.class, 0.003);
		
		spawn(Boss1.class, 0.0005);
		spawn(Boss2.class, 0.0005);
		spawn(Boss3.class, 0.0005);
		
		if((int)(player.lifetime() / 4000) > lastspawn){
			new Boss4().add();
			lastspawn = (int)(player.lifetime() / 4000);
		}
	}
	
	void spawnGroup(Class<? extends Enemy> c, double chance, int amount){
		if(!checkSpawn()) return;
		try{
			if(chance(chance)){
				float x = player.x + MathUtils.random( -150, 150), y = player.y + MathUtils.random( -150, 150);
				for(int i = 0; i < amount; i ++)
				c.newInstance().set(x + MathUtils.random( -15, 15), y + MathUtils.random( -15, 15)).spawn();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	void spawn(Class<? extends Enemy> c, double chance){
		if(!checkSpawn()) return;
		try{
			if(chance(chance)) c.newInstance().set(player.x + MathUtils.random( -150, 100), player.y + MathUtils.random( -150, 100)).spawn();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	boolean checkSpawn(){
		return entities.size() < 210;
	}

	boolean chance(double d){
		return Math.random() < d * (1f + player.lifetime() / escalation);
	}

	void drawGUI(){
		font.setColor(Color.CORAL);
		
		if(!started){
			font.draw(batch,  "Controls: WASD to move, click to shoot. Rightclick to deflect bullets."
					+ "\nThe more enemies you destroy, the more powerful you become."
					+ "\n[ENTER] to start.",  0, Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth(), Align.center, true);
			
			if(Gdx.input.isKeyPressed(Keys.ENTER)){
				started = true;
			}
			
		}
		
		
		if(dead){
			batch.setColor(new Color(0,0,0,0.3f));
			batch.draw(Textures.get("white"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			font.draw(batch,  "You have died. Score: "+ player.totalXP()
					+ "\n[ENTER] to restart.",  0, Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth(), Align.center, true);
			batch.setColor(Color.WHITE);
			
			if(Gdx.input.isKeyPressed(Keys.ENTER)){
				dead = false;
				player = new Player();
				entities.clear();
				player.add();
				
			}
		}
	}

	@Override
	public void resize(int width, int height){
		camera.setToOrtho(false, width / zoom, height / zoom);
		camera.update();
		matrix.setToOrtho2D(0, 0, width, height);

		processor.setViewport(new Rectangle(0, 0, width, height));

	}

	@Override
	public void dispose(){
		Textures.dispose();
		batch.dispose();
	}
}
