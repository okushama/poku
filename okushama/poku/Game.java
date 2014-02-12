package okushama.poku;
import java.awt.Font;
import java.io.InputStream;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;

public class Game {

	public static Game instance = null;
	public Ticker tickLogic = new TickLogic();
	public Ticker tickRender = new TickRender();
	public static EntityPlayer playerOne = new EntityPlayer(0);
	public static EntityPlayer playerTwo = new EntityPlayer(1);
	public static EntityBall ball = new EntityBall();
	public static int WIDTH = 200, HEIGHT = 200;
	public static Sound sound;
	public static TTF font, fontTiny;
	public static boolean gamePaused = false;
	public static boolean gameStarted = false;
	public static boolean gameFinished = false;
	public static int scoreToWin = 9;
	public static int winner = -1;
	public static boolean twoPlayer = false;
	public static boolean aiPlayerOne = false;

	public static void main(String[] args){
		instance = new Game();
	}
	
	public static boolean gameInAction(){
		return gameStarted && !gameFinished && !gamePaused;
	}
	
	public Game(){
		try {
			init();
			run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() throws Exception{
		Display.setTitle("o7");
		Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
		Display.create();
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0.0, Display.getDisplayMode().getWidth(), 0.0, Display.getDisplayMode().getHeight(), -1.0, 1.0);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glViewport(0, 0, Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());
		sound = new Sound();
		String fontf = "assets/font/font.TTF";
		InputStream inputStream = ResourceLoader.getResourceAsStream(fontf);
		java.awt.Font awtFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, inputStream);
		awtFont = awtFont.deriveFont(24f);
		font = (TTF) new TTF(awtFont, true);
		awtFont = awtFont.deriveFont(10f);
		fontTiny = (TTF) new TTF(awtFont, true);
		Game.sound.playBackgroundMusic("main", 1f, true);
	}
	
	public void run(){
		tickLogic.create(10);
		while(true){
			Display.update();
			tickRender.onTick();
			Display.sync(60);
			if(Display.isCloseRequested()){
				System.exit(0);
			}
		}
	}
}
