package okushama.poku;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;

public class Renderer {

	
	public static void drawRect(float x, float y, float w, float h, float[] c){
		glColor4f(c[0], c[1], c[2], c[3]);
		glBegin(GL_QUADS);
		glVertex2f(x, y);
		glVertex2f(x+w, y);
		glVertex2f(x+w, y+h);
		glVertex2f(x, y+h);
		glEnd();
	}
	
	public static void drawCenteredString(TTF font, String text, float y){
		font.drawString(Game.WIDTH / 2 - (font.getWidth(text) / 2), y, text);
	}
	
	public static void drawString(TTF font, String text, float x, float y){
		font.drawString(x, y, text);
	}
}
