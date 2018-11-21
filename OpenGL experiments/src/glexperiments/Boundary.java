package glexperiments;

import java.awt.Toolkit;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;








import com.jogamp.opengl.GL2;

public class Boundary 
{
	public ArrayList<Rec> map;
	FloatBuffer pixel;
	int scanWidthPix;
	int scanHeightPix;
	float width,height;
	
	public Boundary(){
		map=new ArrayList<Rec>();
		
		width=(float) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	    height=(float) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	    scanHeightPix=nearest(height*0.1f);
	    scanWidthPix=nearest(width*0.1f);
	    ByteBuffer pixeldata=ByteBuffer.allocateDirect(3*scanWidthPix*scanHeightPix).order(ByteOrder.nativeOrder());
	    pixel=pixeldata.asFloatBuffer();
	    
	}
	private int nearest(float f) {
		// TODO Auto-generated method stub
		return (int) Math.ceil(f);
	}
	private float convertX(float x) {
		// TODO Auto-generated method stub
		x=(float)((x+1)/2);
		return  x;
	}
	private float convertY(float y) {
		// TODO Auto-generated method stub
		y=(float)((y+1)/2);
		return  y;
	}
	public void getPixels(GL2 gl,int x,int y,int width,int height){
		gl.glReadPixels(x,y,width,height, GL2.GL_RED, GL2.GL_FLOAT, pixel);
		
	}
	
	
	public void mapFinder(GL2 gl){
			float widthCounter=0.1f;
		float heightCounter=0.1f;
		float scanX=-1.0f;
		float scanY=-1.0f;
		float topX,topY,bottomX,bottomY;
		topX=scanX;
		topY=scanY+heightCounter;
		bottomX=scanX;
		bottomY=scanY;
		float offset=0;
		int c=-1;
		boolean blood;
		while (scanY<=1.0f){
			scanX=-1f;
			
			c++;
			if (c==9) offset=0.03f;
			if (c==13) offset=0.06f;
			//System.out.println(scanY);
			blood=false;
		while (scanX<=1f){
			pixel.clear();
					getPixels(gl,(int)(convertX(scanX)*width),(int)(convertY(scanY-offset)*height),scanWidthPix/2, scanHeightPix/2);
						
					
					
					
				float ratio=findBlackPercentage(0,0,scanWidthPix/2,scanHeightPix/2);
				if (ratio>0.7){
					if (!blood){
						topX=scanX;
						bottomX=scanX;
					}
					bottomX=bottomX+widthCounter;
					blood=true;
				}
				else  {
					if (blood){
					map.add(new Rec(topX,topY,bottomX,bottomY));
					topX=bottomX;
					/*
					if (c==11){
						
						System.out.println("11");
						map.get(map.size()-1).show();
					}
					else if (c==12){
						System.out.println();
						System.out.println("12");
						map.get(map.size()-1).show();
					}
					else if (c==13){
						System.out.println();
						System.out.println("13");
						map.get(map.size()-1).show();
					}
					*/
					}
					blood=false;
					
					//System.out.println(convertX(topX)+" "+convertY(topY)+" "+convertX(bottomX)+" "+convertY(bottomY));
				}
								
					scanX=scanX+widthCounter;
			}
		if (blood){
			map.add(new Rec(topX,topY,bottomX,bottomY));
			
		}
			
			scanY=scanY+heightCounter;
			topX=-1;
			bottomX=-1;
			bottomY=scanY;

			topY=scanY+heightCounter;
			if (c==8){
				 bottomY=scanY-0.02f;
			}
			if (c==12){
				bottomY=scanY-0.06f;
			}
			blood=false;
			
			
		}
		
		
		
		
		
	}
	
	private float findBlackPercentage(int XoffSet,int YoffSet,int RowSize,int ColSize) {
		// TODO Auto-generated method stub
		int no_of_Reds=0;
		int i=0,j=0;
		for (i=YoffSet;i<YoffSet+ColSize;i++){
			for(j=XoffSet;j<XoffSet+RowSize;j++){
			//System.out.println(i+" "+pixel.get(i));
			if (pixel.get((i*RowSize)+j)>0.5) {
				no_of_Reds++;
				}
			
			}
		
		}
		
		return (float)(no_of_Reds/((float)RowSize*ColSize));
		
		
		
	}
	
}