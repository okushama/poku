import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;

public class TickRender extends Ticker {

	@Override
	public void onTick() {
		glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
		if (Game.gameInAction()) {
			Game.playerOne.render();
			Game.playerTwo.render();
			Game.ball.render();
		}
		Renderer.drawRect(0, Game.HEIGHT - 30, Game.WIDTH, 30, new float[] {
				0f, 0f, 0f, 1f });
		Renderer.drawRect(0, Game.HEIGHT - 30, Game.WIDTH, 2, new float[] {
				1f, 1f, 1f, 1f });
		Renderer.drawRect(0, 0, Game.WIDTH, 2,
				new float[] { 1f, 1f, 1f, 1f });
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		String scores = Game.playerOne.score + " - " + Game.playerTwo.score;
		Game.font.drawString(Game.WIDTH / 2 - (Game.font.getWidth(scores) / 2), Game.HEIGHT - Game.font.getHeight(), scores);
		String[] line = {""};
		if (Game.gamePaused) {
			line = new String[]{"GAME", "PAUSED"};
		}
		if(!Game.gameStarted){
			line = new String[]{"PRESS", "SPACE TO", "PLAY"};
		}
		if(Game.gameFinished){
			line = new String[]{"PLAYER "+(Game.winner+1), "WINS"};
		}
		for(int i = 0; i < line.length; i++){
			Game.font.drawString(Game.WIDTH / 2 - (Game.font.getWidth(line[i]) / 2), Game.HEIGHT / 2 - (Game.font.getHeight()*i), line[i]);
		}

		glDisable(GL_BLEND);
	}
}
