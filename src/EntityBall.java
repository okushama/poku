import java.util.Random;

import org.lwjgl.opengl.Display;


public class EntityBall extends Entity{

	public float motionX, motionY;
	
	public EntityBall(){
		super();
		sizeX = sizeY = 6;
		reset();
	}
	
	public void reset(){
		posX = (Game.WIDTH/2) - (sizeX/2);
		posY = (Game.HEIGHT/2) - (sizeY/2);
		boolean left = new Random().nextBoolean();
		float dir = left ? -new Random().nextFloat() : new Random().nextFloat();
		motionX = dir;
		motionY = left ? 2f : -2f;
	}
	
	@Override
	public void render() {
		float r = 1f, g = 1f, b = 1f;
		float hot = 6f;
		if(motionY > hot || motionY < -hot || motionX > hot || motionX < -hot){
			if((int)posY % 2 == 0)
				b = 0f;
		}
		Renderer.drawRect(posX, posY, sizeX, sizeY, new float[]{r,g,b,1f});		
	}
	
	public void hit() {
		if(this.posY > Game.WIDTH/2){
			Game.sound.playSound("hit1", 1f, 1f);
		}else{
			Game.sound.playSound("hit0", 1f, 1f);
		}
		
		motionY = -(motionY / 0.95f);
		float max = 10f;
		if(motionY > max){
			motionY = max;
		}
		if(motionY < -max){
			motionY = -max;
		}
	}
	
	public void miss() {
		Game.sound.playSound("miss", 1f, 1f);
		if(posY < Game.HEIGHT/2){
			Game.playerTwo.score++;
		}else{
			Game.playerOne.score++;
		}

		reset();
	}

	@Override
	public void logic() {
		posY += motionY;
		posX += motionX;
		
		if(posX < 3){
			posX = 3;
			motionX = -motionX;
			Game.sound.playSound("wall", 1f, 1f);

		}
		if(posX > Game.WIDTH-3-sizeX){
			posX = Game.WIDTH-3-sizeX;
			motionX = -motionX;
			Game.sound.playSound("wall", 1f, 1f);
		}
		if(posY < Game.playerOne.posY + Game.playerOne.sizeY){
			if(posX+sizeX > Game.playerOne.posX && posX < Game.playerOne.posX+Game.playerOne.sizeX){
				boolean left = new Random().nextBoolean();
				float dir = left ? -new Random().nextFloat() : new Random().nextFloat();
				dir = dir * (new Random().nextInt(3)+1);
				posY = Game.playerOne.posY + Game.playerOne.sizeY+1;
				motionX+= dir;
				hit();
				return;
			}
		}
		if(posY+sizeY >= Game.playerTwo.posY){
			if(posX+sizeX > Game.playerTwo.posX && posX < Game.playerTwo.posX+Game.playerTwo.sizeX){
				boolean left = new Random().nextBoolean();
				float dir = left ? -new Random().nextFloat() : new Random().nextFloat();
				posY = Game.playerTwo.posY - sizeY - 3;
				motionX = dir;
				hit();
				return;
			}
		}
		if(posY > Game.HEIGHT){
			miss();
		}
		if(posY + (sizeY*2) <= 0){
			miss();
		}
	}

}
