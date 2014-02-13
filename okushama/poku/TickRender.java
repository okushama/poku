package okushama.poku;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.Display;

public class TickRender extends Ticker {

	@Override
	public void onTick(float partialTick) {
		glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		if (Game.gameInAction()) {
			Game.playerOne.render(partialTick);
			Game.playerTwo.render(partialTick);
			for(EntityBall ball : Game.balls){
				ball.render(partialTick);
			}
		}
		Renderer.drawRect(0, Game.HEIGHT - 30, Game.WIDTH, 30, new float[] {
				0f, 0f, 0f, 1f });
		Renderer.drawRect(0, Game.HEIGHT - 30, Game.WIDTH, 2, new float[] { 1f,
				1f, 1f, 1f });
		Renderer.drawRect(0, 0, Game.WIDTH, 2, new float[] { 1f, 1f, 1f, 1f });
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		String sep = " - ";
		if(Game.playerOne.score == 0 && Game.playerTwo.score == 0){
			sep = "___";
		}
		String scores = Game.playerOne.score +sep+ Game.playerTwo.score;
		Renderer.drawString(Game.font, scores, Game.WIDTH / 2 - (Game.font.getWidth(scores) / 2), Game.HEIGHT - Game.font.getHeight());
		String[] line = { "" };
		if (Game.gamePaused) {
			line = new String[] { "PAUSED" };
		}
		if (!Game.gameStarted) {
			line = new String[] { "---POKU---"};
			if(System.currentTimeMillis() % 1000 > 440)
			Renderer.drawCenteredString(Game.fontTiny, "PRESS 'SPACE' TO BEGIN", 35);
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

		String[] pauseText2 = {"CONTROLS:",
				"P1 MOVEMENT: Arrows ", 
				"P2 MOVEMENT: Z and X",
				"EXTRA BALLS: B      ",
				"TWO PLAYERS: SPACE  ",
				"SPECTATE AI: ENTER  ",
				"PAUSE GAME : ESC    ",
				"RESET GAME : R      "};
		if(Game.gameInAction()){
			long tick = 2000;
			String p1id = "P1", p2id = "P2";
			if(System.currentTimeMillis() % tick >= tick/4){
				if(Game.aiPlayerOne){
					p1id = "CPU";
				}
				if(!Game.twoPlayer){
					p2id = "CPU";
				}
				Renderer.drawString(Game.fontTiny, p1id, 2, Game.HEIGHT - 28);
				Renderer.drawString(Game.fontTiny, p2id, Game.WIDTH - 1 - Game.fontTiny.getWidth(p2id), Game.HEIGHT - 28);
			}

		}
		if (!Game.gameInAction()) {
			for (int i = 0; i < pauseText2.length; i++) {
				Renderer.drawCenteredString(Game.fontTiny, pauseText2[i],
						(Game.HEIGHT / 2 + 30)
								- (Game.fontTiny.getHeight() * i));
			}
			
			Renderer.drawCenteredString(Game.fontTiny, "(C) Mr_okushama 2014", 3);
		}
		glDisable(GL_BLEND);
	}
}
