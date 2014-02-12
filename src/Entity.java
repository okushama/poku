
public abstract class Entity {

	public float posX, posY;
	public float sizeX = 1, sizeY = 1;
	
	public Entity(){
		posX = 0;
		posY = 0;
		
	}
	
	public abstract void render();
	
	public abstract void logic();
	
}
