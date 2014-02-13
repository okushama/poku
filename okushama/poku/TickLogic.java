package okushama.poku;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class TickLogic extends Ticker {

	@Override
	public void onTick() {
		//Game.sound.playBackgroundMusic("main", 1f, 1f, true);
		if (Game.winner > -1) {
			Game.gameFinished = true;
		}
		while (Mouse.next()) {
			int btn = Mouse.getEventButton();
			boolean state = Mouse.getEventButtonState();
			int mwheel = Mouse.getDWheel();
			if (btn > -1 && state) {
				onMouseClick(btn);
			}
			if (mwheel != 0) {
				onMouseScroll(mwheel);
			}
		}
		while (Keyboard.next()) {
			if (!Keyboard.isRepeatEvent() && !Keyboard.getEventKeyState()) {
				onKeyPress(Keyboard.getEventKey());
			}
		}
		if (Keyboard.isKeyDown(Keyboard.getEventKey())) {
			onKeyHeld(Keyboard.getEventKey());
		}
		if (Game.gameInAction()) {
			Game.playerOne.logic();
			if(Keyboard.isKeyDown(Keyboard.KEY_M)){
					Game.playerOne.magnetizeBalls();
			}
			Game.playerTwo.logic();
			for(int i = 0; i < Game.balls.size(); i++){
			EntityBall ball = Game.balls.get(i);
				ball.logic();
			}
			if (Game.playerOne.score >= Game.scoreToWin) {
				Game.winner = 0;
				return;
			}
			if (Game.playerTwo.score > Game.scoreToWin) {
				Game.winner = 1;
				return;
			}
		}
	}

	public void onMouseClick(int b) {
		Game.sound.setGain(0.05f);
	}

	public void onMouseScroll(int dir) {
		System.out.println(":D");
		if(dir > 0){
			Game.sound.setGain(Game.sound.getMusicGain()-0.1f);
		}else if(dir < 0){
			Game.sound.setGain(Game.sound.getMusicGain()+0.1f);
		}
	}

	public void resetGame(boolean toMenu) {
		if (!toMenu){
			Game.gameStarted = true;
		}else{
			Game.gameStarted = false;
		}
		Game.gamePaused = false;
		Game.resetBalls();
		Game.winner = -1;
		Game.playerOne.score = 0;
		Game.playerTwo.score = 0;
		Game.gameFinished = false;
	}

	public void onKeyPress(int k) {
		if(k == Keyboard.KEY_RETURN){
			if(Game.gameInAction()){
				if(!Game.aiPlayerOne){
					Game.aiPlayerOne = true;
					Game.twoPlayer = false;
				}else{
					Game.aiPlayerOne = false;
				}
			}
		}
		if(k == Keyboard.KEY_R){
			this.resetGame(true);
		}
		if (k == Keyboard.KEY_B){
			EntityBall ball = new EntityBall();
			ball.reset();
			Game.balls.add(ball);
		}
		
		if (k == Keyboard.KEY_SPACE) {
			if (Game.gameInAction()){
				if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
					Game.aiPlayerOne = !Game.aiPlayerOne;
					return;
				}
				Game.twoPlayer = !Game.twoPlayer;
			}
			if (Game.gameFinished) {
				Game.sound.playSound("win", 1f);
				resetGame(true);
				return;
			}
			if (!Game.gameStarted) {
				Game.sound.playSound("pause", 1f);
				resetGame(false);
			}
		}
		if (k == Keyboard.KEY_ESCAPE) {
			if (Game.gameFinished) {
				resetGame(true);
				return;
			}
			Game.sound.playSound("pause", 1f);
			Game.gamePaused = !Game.gamePaused;
		}
	}

	public void onKeyHeld(int k) {
		
	}

}
