package okushama.poku;
import java.util.Random;

import org.lwjgl.input.Keyboard;

public class EntityPlayer extends Entity {

	public int playerSlot = -1;
	public int score = 0;
	int[][] keys = { new int[] { Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT },
			new int[] { Keyboard.KEY_Z, Keyboard.KEY_X } };

	public EntityPlayer(int slot) {
		sizeX = 20;
		sizeY = 10;
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
		posY = playerSlot == 0 ? 2 : Game.HEIGHT - 40;

		// Ai Portion
		boolean topHalf = playerSlot == 1;
		boolean shouldMove = false;
		if(topHalf){
			if(Game.ball.posY+(Game.ball.sizeY/2) > Game.HEIGHT/2 - 30 && Game.ball.posY < posY){
				shouldMove = true;
			}
		}else{
			if(Game.ball.posY+(Game.ball.sizeY/2) < Game.HEIGHT/2 - 30 && Game.ball.posY > posY + sizeY){
				shouldMove = true;
			}
		}
		if(playerSlot == 0 && !Game.aiPlayerOne) shouldMove = false;
		if(playerSlot == 1 && Game.twoPlayer) shouldMove = false;
		if (shouldMove)
		{
			float speed = 3.5f;
			if (posX + (sizeX / 2) + 5 < Game.ball.posX + (Game.ball.sizeX / 2)) {
				posX += speed;
			}
			if (posX + (sizeX / 2) - 5 > Game.ball.posX + (Game.ball.sizeX / 2)) {
				posX -= speed;
			}
		}
	}

}
