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
}
