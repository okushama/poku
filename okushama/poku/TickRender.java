package okushama.poku;

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
		Renderer.drawRect(0, Game.HEIGHT - 30, Game.WIDTH, 2, new float[] { 1f,
				1f, 1f, 1f });
		Renderer.drawRect(0, 0, Game.WIDTH, 2, new float[] { 1f, 1f, 1f, 1f });
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		String scores = Game.playerOne.score + " - " + Game.playerTwo.score;
		Game.font.drawString(Game.WIDTH / 2 - (Game.font.getWidth(scores) / 2),
				Game.HEIGHT - Game.font.getHeight(), scores);
		String[] line = { "" };
		if (Game.gamePaused) {
			line = new String[] { "PAUSED" };
		}
		if (!Game.gameStarted) {
			line = new String[] { "---POKI---", "","","","PRESS", "SPACE KEY" };
		}
		if (Game.gameFinished) {
			if (Game.winner == 1) {
				line = new String[] { "YOU LOSE!" };
			} else {
				line = new String[] { "YOU WIN!" };
			}
			if (Game.twoPlayer || Game.aiPlayerOne) {
				line = new String[] { "P" + (Game.winner + 1)+ " WINS!" };
			}
		}
		for (int i = 0; i < line.length; i++) {
			Game.font.drawString(Game.WIDTH / 2
					- (Game.font.getWidth(line[i]) / 2), Game.HEIGHT / 2
					- (Game.font.getHeight() * i) + 40, line[i]);
		}

		String[] pauseText = { "-----------", "Supports two players,",
				"Just hit SPACE ingame!", "", "Or just watch AI play,",
				" hit CTRL+SPACE ingame!", "-----------"};

		if (!Game.gameInAction()) {
			for (int i = 0; i < pauseText.length; i++) {
				Renderer.drawCenteredString(Game.fontTiny, pauseText[i],
						(Game.HEIGHT / 2 + 28)
								- (Game.fontTiny.getHeight() * i));
			}
			Renderer.drawCenteredString(Game.fontTiny, "(C) Mr_okushama 2014",
					3);
		}
		glDisable(GL_BLEND);
	}
}
