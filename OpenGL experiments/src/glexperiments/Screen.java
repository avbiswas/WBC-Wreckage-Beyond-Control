package glexperiments;

import com.jogamp.opengl.GL2;

public interface Screen {
	public void display(GL2 gl);

	public void initialize(GL2 gl);

	public void dispose();
}
