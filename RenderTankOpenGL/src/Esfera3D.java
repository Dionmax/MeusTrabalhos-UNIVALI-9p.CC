import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.glu.Sphere;

import obj.Vector3f;

public class Esfera3D extends Objeto3D {
	Sphere sphere = new Sphere();
	Vector3f cor = new Vector3f();
	
	Model model;

	public Esfera3D(Model model) {
		this.model = model;
	}
	
	public void desenhase() {
		
		
		glPushMatrix();
		
	    glDisable(GL_TEXTURE_2D);
	    glColor3f(cor.x, cor.y, cor.z);
	    
		glTranslatef(x,y,z);
		
		glScalef(0.1f, 0.1f, 0.1f);
		
		
		
		//sphere.draw(0.01F, 16, 16);
		model.draw();
		
		glPopMatrix();
	}
}
