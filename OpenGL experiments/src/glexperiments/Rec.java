package glexperiments;

public class Rec {
	float topLeftX;
	float topLeftY;
	float bottomRightX;
	float bottomRightY;
	public Rec (float x1,float y1,float x2,float y2){
		topLeftX=x1;
		topLeftY=y1;
		bottomRightX=x2;
		bottomRightY=y2;
	}
	public boolean isWithin(float x,float y){
		
		if (x>=(topLeftX-0.001f) && x<=(bottomRightX+0.001f) && y<=(topLeftY+0.001f) && y>=(bottomRightY-0.001f)){
			return true;
		}
		return false;
	}
	public boolean isWithin(Vector x1){
	
		if (x1.x>topLeftX && x1.x<bottomRightX && x1.y<topLeftY && x1.y>bottomRightY){
			return true;
		}
		return false;
	}
	
	public float getMidX(){
		return (topLeftX+bottomRightX)/2;
	}
	public float getMidY(){
		return (topLeftY+bottomRightY)/2;
	}
	public void show(){
		System.out.println();
		System.out.println(topLeftX+" "+topLeftY+"\n                       "+bottomRightX+" "+bottomRightY);
	}
}
