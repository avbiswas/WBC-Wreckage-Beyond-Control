package glexperiments;

import java.util.ArrayList;

public class Grid {
	ArrayList <ArrayList<Rec>> grid;
	ArrayList <Rec> map;
	int order;
	public  Grid(ArrayList<Rec> map){
		this.map=map;
		order =7;
		grid= new ArrayList<ArrayList<Rec>>();
		process();
		//show(0.0f,0.3f);
		//show(31);
	}
	/*
	private void show(float x,float y){
		for (int i=0;i<grid.size();i++){
			for (int j=0;j<grid.get(i).size();j++){
				if (grid.get(i).get(j).isWithin(x, y)){
					System.out.println(i);
				}
			}
		}
	}
	private void show(int x){
		System.out.println("case  "+x);;
		for (int i=0;i<grid.get(x).size();i++){
			grid.get(x).get(i).show();
		}
		System.out.println();
	}
	private void show() {
		// TODO Auto-generated method stub
		for (int i=0;i<grid.size();i++){
			System.out.println("Grid: "+i);
			for (int j=0;j<grid.get(i).size();j++){
				grid.get(i).get(j).show();
			}
			System.out.println();
		}
	}
	private void show2(){
		for (int i=0;i<map.size();i++){
			for (int j=0;j<grid.size();j++){
				if (grid.get(j).contains(map.get(i))){
					System.out.print(j);
				}
			}
			System.out.println();
		}
	}
	*/
	private int whichGrid(int x, int y){
		
		
		return order*y+x;
		
	}
	private int  convert(float n){
		float temp= order*(n+1)/2;
		if (temp>=order)temp=order-1;
		return (int)temp;
	}
	private void process() {
		// TODO Auto-generated method stub
		int i=0;
		for (i=0;i<(order*order);i++){
			grid.add(new ArrayList<Rec>());
		}
		for (i=0;i<map.size();i++){
			int[] x = {0,0};
			int[] y = {0,0};
			x[0]=convert(map.get(i).topLeftX);
			x[1]=convert(map.get(i).bottomRightX);
			y[0]=convert(map.get(i).topLeftY);
			y[1]=convert(map.get(i).bottomRightY);
			ArrayList<Integer> gridsToAdd=new ArrayList<Integer>();
			gridsToAdd.add(whichGrid(x[0], y[0]));
			
			if (!(gridsToAdd.contains(whichGrid(x[1],y[0]))))
				gridsToAdd.add(whichGrid(x[1], y[0]));
			
			if (!(gridsToAdd.contains(whichGrid(x[1],y[1]))))
				gridsToAdd.add(whichGrid(x[1], y[1]));
			if (!(gridsToAdd.contains(whichGrid(x[0],y[1]))))
				gridsToAdd.add(whichGrid(x[0], y[1]));
				
			fillGaps(gridsToAdd);
			for (int j=0;j<gridsToAdd.size();j++){
				grid.get(gridsToAdd.get(j)).add(map.get(i));
			}
			
		}
	}
	private void fillGaps(ArrayList<Integer> gridsToAdd) {
		// TODO Auto-generated method stub
		int num1,num2;
		int size=gridsToAdd.size();
		if (size==1) return;
		for (int i=0;i<size;i++){
			 num1=gridsToAdd.get(i);
					for (int j=i+1;j<size;j++){
						num2=gridsToAdd.get(j);
						if (Math.abs(num2-num1)<7 && (num2/order==num1/order)){
							int min=num1<num2?num1:num2;
							int max=num1>num2?num1:num2;
							for (int k=min;k<=max;k++){
								if (!gridsToAdd.contains(k)){
									gridsToAdd.add(k);
								}
							}
						}
					}
		}
	}
	
}
