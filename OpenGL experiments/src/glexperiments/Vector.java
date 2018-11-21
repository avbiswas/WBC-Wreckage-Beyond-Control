package glexperiments;

import java.awt.Toolkit;

import com.jogamp.opengl.GL2;

public class Vector {
	float x;
	float res;
	float y;
	public Vector(){
		res= (float) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth())/(Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
	}
	public Vector(float x,float y){
		this.x=x;
		this.y=y;
		res= (float) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth())/(Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
		
	}
	public void set(float x,float y){
		this.x=x;
		this.y=y;
	}
	public Vector sum(Vector x2){
		return (new Vector(this.x+x2.x,this.y+x2.y));
	}
	public Vector subtract(Vector x1){
		return (new Vector(x1.x-this.x,x1.y-this.y));
	}
	public float distance(Vector x2){
		return (float) Math.sqrt((this.x-x2.x)*(this.x-x2.x)+(this.y-x2.y)*(this.y-x2.y));
	}
	public float scalar(){
		return (x*x+y*y);
	}
	public void show(){
		System.out.println(x+" "+y);
	}
	public void normalize(){
		set(x/scalar(), y/scalar());
		
	}
	public float angle(){
		return (float) Math.atan2(y, x);
		
	}
	public Vector section(Vector x1,int m,int n){
		Vector result=new Vector();
		result.x=(this.x*m+x1.x*n)/(m+n);
		result.y=(this.y*m+x1.y*n)/(m+n);
		return result;
	}
	public void drawLine(GL2 gl,Vector v2){
		gl.glLoadIdentity();
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex3f(this.x, this.y, 0.0f);
		gl.glVertex3f(v2.x, v2.y, 0.0f);
		gl.glEnd();
		gl.glLoadIdentity();
	}
	public Vector direction(Vector v2){
		Vector temp=this.subtract(v2);
		temp.normalize();
		return temp;
	}
	public boolean EllipseChecker(Vector v2,float rad){
		float h=rad*res;
		float temp=((((v2.x-this.x)*(v2.x-this.x))/(rad*rad))+(((v2.y-this.y)*(v2.y-this.y))/(h*h)));
		if (temp<=1){
			return true;
		}
		else{
			return false;
		}
	}
}
