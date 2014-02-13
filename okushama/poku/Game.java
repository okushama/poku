package okushama.poku;
import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;

public class Game {

	public static Game instance = null;
	public static boolean isRunning = false;
	public Timer timer = new Timer();
	public Ticker tickLogic = new TickLogic();
	public Ticker tickRender = new TickRender();
	public static EntityPlayer playerOne = new EntityPlayer(0);
	public static EntityPlayer playerTwo = new EntityPlayer(1);
	public static ArrayList<EntityBall> balls = new ArrayList<EntityBall>();
	public static EntityBall mainBall = new EntityBall();
	public static int WIDTH = 200, HEIGHT = 200, WINDOW_WIDTH = 400, WINDOW_HEIGHT = 400;
	public static Sound sound;
	public static TTF font, fontTiny;
	public static boolean gamePaused = false;
	public static boolean gameStarted = false;
	public static boolean gameFinished = false;
	public static int scoreToWin = 21;
	public static int winner = -1;
	public static boolean twoPlayer = false;
	public static boolean aiPlayerOne = false;

	public static void main(String[] args){
		instance = new Game();
	}
	
	public Game(){
		try {
			init();
			run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean gameInAction(){
		return gameStarted && !gameFinished && !gamePaused;
	}
	
	public static void resetBalls(){
		balls.clear();
		mainBall.reset();
		balls.add(mainBall);
	}
	
	public static EntityBall getClosestBall(EntityPlayer player){
		float closest = 9999f;
		EntityBall closestObj = null;
		
		for(EntityBall ball : balls){
			if(player.playerSlot == 0){
				if(ball.posY - (player.posY + player.sizeY)  < closest){
					closest = (ball.posY - player.posY);	
					closestObj = ball;
					closestObj.distanceToClosestPlayer = closest;
					closestObj.closestPlayerSlot = 0;

				}
			}else{
				if(player.posY - (ball.posY + ball.sizeY)  < closest){
					closest = player.posY - (ball.posY + ball.sizeY);	
					closestObj = ball;
					closestObj.distanceToClosestPlayer = closest;
					closestObj.closestPlayerSlot = 1;
				}
			}
		}
		return closestObj;
	}
	
	
	
	public void init() throws Exception{
		isRunning = true;
		Display.setTitle("o7");
		Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
		Display.create();
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0, WIDTH, 0.0, HEIGHT, -1.0, 1.0);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glViewport(0, 0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());
		sound = new Sound();
		String fontf = "assets/font/font.TTF";
		InputStream inputStream = ResourceLoader.getResourceAsStream(fontf);
		java.awt.Font awtFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, inputStream);
		awtFont = awtFont.deriveFont(24f);
		font = (TTF) new TTF(awtFont, false);
		awtFont = awtFont.deriveFont(10f);
		fontTiny = (TTF) new TTF(awtFont, false);
		resetBalls();
		Game.sound.playBackgroundMusic("main", 1f, true);
	}
	
	public void cleanup(){
		isRunning = false;
		tickRender.destroy();
		tickLogic.destroy();
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
		AL.destroy();
		System.out.println("Clean up successful!");
		System.exit(0);
	}
	
	public void run(){
		//tickLogic.create(10);
		timer.start();
		timer.lastFPS = timer.getTime();
		while(true){
			float delta = timer.getDelta()/10;
			//System.out.println(delta);
			Display.update();
			tickLogic.onTick(delta);
			tickRender.onTick(delta);
			Display.sync(60);
			if(Display.isCloseRequested()){
				cleanup();
			}
		}
	}
}
