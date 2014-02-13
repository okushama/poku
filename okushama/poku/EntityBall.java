package okushama.poku;

import java.util.Random;

import org.lwjgl.opengl.Display;

public class EntityBall extends Entity {

	public float motionX, motionY;

	public float distanceToClosestPlayer = 0f;
	public int closestPlayerSlot = -1;
	public int lastHittingPlayer = -1;

	public EntityBall() {
		super();
		sizeX = sizeY = 6;
		reset();
	}

	public void reset() {
		if (this != Game.mainBall) {
			Game.balls.remove(this);
		}
		posX = (Game.WIDTH / 2) - (sizeX / 2);
		posY = (Game.HEIGHT / 2) - (sizeY / 2);
		boolean left = new Random().nextBoolean();
		float dir = left ? -new Random().nextFloat() : new Random().nextFloat();
		motionX = dir;
		motionY = left ? 0.5f : -0.5f;
	}

	@Override
	public void render(float partialTick) {
		float r = 1f, g = 1f, b = 1f;
		float hot = 6f;
		if (this != Game.mainBall) {
			r = g = b = 0.5f;
		}
		if (motionY > hot || motionY < -hot || motionX > hot || motionX < -hot) {
			if ((int) posY % 2 > 0) {
				r = 1f;
				b = 0f;
				g = 1f;
			}
		}
		Renderer.drawRect(posX, posY, sizeX, sizeY, new float[] { r, g, b, 1f });
	}

	public void hit(float partialTick, boolean changeY) {
		if (this.posY > Game.WIDTH / 2) {
			Game.sound.playSound("hit1", 1f);
		} else {
			Game.sound.playSound("hit0", 1f);
		}
		float accel = 0.15f;
		float newMotionY = -(motionY - (accel * partialTick));
		if (lastHittingPlayer == 1) {
			newMotionY = -(motionY + (accel * partialTick));
		}

		if (changeY)
			motionY = newMotionY;
		float max = 12f;
		if (motionY > max) {
			motionY = max;
		}
		if (motionY < -max) {
			motionY = -max;
		}
	}

	public void miss() {
		Game.sound.playSound("miss", 1f);

		if (posY < Game.HEIGHT / 2) {
			Game.playerTwo.score++;
		} else {
			Game.playerOne.score++;
		}

		reset();
	}

	@Override
	public void logic(float partialTick) {
		// move based on motion
		posY += motionY * partialTick;
		posX += motionX * partialTick;

		// Wall bounce
		if (posX < 0) {
			posX = 0;
			motionX = -motionX;
			Game.sound.playSound("wall", 1f);

		}
		if (posX > Game.WIDTH - sizeX) {
			posX = Game.WIDTH - sizeX;
			motionX = -motionX;
			Game.sound.playSound("wall", 1f);
		}

		// ball detection
		for (EntityBall otherBall : Game.balls) {
			if (otherBall == this)
				continue;
			if (this.posX + this.sizeX > otherBall.posX
					&& this.posX < otherBall.posX + otherBall.sizeX) {
				if (this.posY + this.sizeY > otherBall.posY
						&& this.posY < otherBall.posY + otherBall.sizeY) {
					if (this.posY < otherBall.posY) {
						motionY = -motionY;
						otherBall.motionY = -motionY;
					}
					motionX = -motionX;
					otherBall.motionX = -otherBall.motionX;
					break;
				}

			}
		}

		// player detection
		if (posY < Game.playerOne.posY + Game.playerOne.sizeY
				&& posY > Game.playerOne.posY) {
			if (posX + sizeX > Game.playerOne.posX
					&& posX < Game.playerOne.posX + Game.playerOne.sizeX) {
				boolean left = Game.playerOne.lastX > Game.playerOne.posX;
				float dir = left ? -rand.nextFloat() : rand.nextFloat();
				// dir = dir * (new Random().nextInt(3)+1);
				boolean changeY = true;
				//if (posY >= Game.playerOne.posY + Game.playerOne.sizeY - 5) 
				{
					posY = Game.playerOne.posY + Game.playerOne.sizeY + 1;
				}
				// motionX = 0;
				motionX = (dir * 2);
				lastHittingPlayer = 0;
				hit(partialTick, changeY);
				return;
			}
		}
		if (posY + sizeY > Game.playerTwo.posY + 1
				&& posY + sizeY < Game.playerTwo.posY + Game.playerTwo.sizeY) {
			if (posX + sizeX > Game.playerTwo.posX
					&& posX < Game.playerTwo.posX + Game.playerTwo.sizeX) {
				boolean left = Game.playerTwo.lastX > Game.playerTwo.posX;
				float dir = left ? -rand.nextFloat() : rand.nextFloat();
				boolean changeY = true;
				//if (posY + sizeY < Game.playerTwo.posY - 5) 
				{
					posY = Game.playerTwo.posY - sizeY - 1;
				}
				//posY = Game.playerTwo.posY - sizeY - 3;
				// motionX = 0;
				motionX = (dir * 2);
				lastHittingPlayer = 1;
				hit(partialTick, changeY);
				return;
			}
		}
		// motionX = motionX * partialTick;

		// back wall detection
		if (posY > Game.HEIGHT - 30) {
			miss();
		}
		if (posY + (sizeY * 2) <= 0) {
			miss();
		}
	}

}
