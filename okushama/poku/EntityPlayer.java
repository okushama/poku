package okushama.poku;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

public class EntityPlayer extends Entity {

	public int playerSlot = -1;
	public int score = 0;
	public int[][] keys = { new int[] { Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT },
			new int[] { Keyboard.KEY_Z, Keyboard.KEY_X } };
	public float lastX = -999f;

	public EntityPlayer(int slot) {
		sizeX = 24;
		sizeY = 6;
		playerSlot = slot;
	}

	@Override
	public void render() {
		float[] c = {0.8f,0.8f,0.8f,1f};
		if(playerSlot == 0){
			if(!Game.aiPlayerOne)
			c = new float[]{0.8f, 0.3f, 0.3f, 1f};
		}else{
			if(Game.twoPlayer)
				c = new float[]{0.3f, 0.5f, 0.8f, 1f};
		}
		Renderer.drawRect(posX, posY, sizeX, sizeY, c);
	}

	@Override
	public void logic() {
		lastX = posX;
		if((playerSlot == 0 && !Game.aiPlayerOne) || Game.twoPlayer){
			
		}
		if (Keyboard.isKeyDown(keys[playerSlot][0])
				&& !Keyboard.isKeyDown(keys[playerSlot][1])) {
			posX -= 2f;
		} else if (Keyboard.isKeyDown(keys[playerSlot][1])
				&& !Keyboard.isKeyDown(keys[playerSlot][0])) {
			posX += 2f;
		}
		if (posX < 3) {
			posX = 3;
		}
		if (posX > Game.WIDTH - sizeX - 3) {
			posX = Game.WIDTH - sizeX - 3;
		}
		posY = playerSlot == 0 ? 2 : Game.HEIGHT - 36;

		// Ai Portion
		boolean topHalf = playerSlot == 1;
		boolean shouldMove = false;
		EntityBall ball = Game.getClosestBall(this);
		if(ball != null){
			if(topHalf){
				if(ball.posY+(ball.sizeY/2) > (Game.HEIGHT/2) - 15 && (ball.posY+ball.sizeY) < posY){
					shouldMove = true;
				}
			}else{
				if(ball.posY+(ball.sizeY/2) < (Game.HEIGHT/2) - 30 && ball.posY > posY + sizeY){
					shouldMove = true;
				}
			}
			if(playerSlot == 0 && !Game.aiPlayerOne) shouldMove = false;
			if(playerSlot == 1 && Game.twoPlayer) shouldMove = false;
			if (shouldMove)
			{
				float speed = 4f;
				if (posX + (sizeX / 2) + 5 < ball.posX + (ball.sizeX / 2)) {
					posX += speed;
				}
				if (posX + (sizeX / 2) - 5 > ball.posX + (ball.sizeX / 2)) {
					posX -= speed;
				}
			}
		}
	}
	
	public void magnetizeBalls(){
		float maxDist = 50f;
		float strength = 0.002f;
		EntityBall nearest = Game.getClosestBall(this);
		//if(nearest.posX > posX-(sizeX/2) && nearest.posX + nearest.sizeX < posX+(sizeX*1.5f))
		if(nearest.distanceToClosestPlayer < maxDist && nearest.closestPlayerSlot == playerSlot && nearest.motionY < 0){
			Vector2f pos = new Vector2f(posX+(sizeX/2), posY+sizeY);
			Vector2f ballpos = new Vector2f(nearest.posX+(nearest.sizeX/2), nearest.posY);
			Vector2f dist = new Vector2f();
			Vector2f.sub(pos, ballpos, dist);
			//nearest.motionX = 0;
			//nearest.motionY = 1f;
			nearest.motionX += dist.getX() * strength;
			//nearest.posY += dist.getY() * strength;
		}
		
	}

}
