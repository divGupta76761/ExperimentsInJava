package MazeGenerator;

import javax.swing.*;

import Vector2.Vector2;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;
public class GridGenerator extends JPanel{
	boolean [][][]walls;
	boolean[][]visited;
	Clearer clear;
	int rows;
	int cols;
	int res = 20;
	static int frameSize = 600;
	Solver solver = null;
	boolean done = false;
	GridGenerator(){
		rows = frameSize/res;
		cols = frameSize/res;
		setLayout(null);
	}
	public static void main(String[] args) {
		GridGenerator gen = new GridGenerator();
		JFrame f = new JFrame();
		f.setSize(frameSize+30,frameSize+35);
		f.setDefaultCloseOperation(3);
		f.getContentPane().add(gen);
		f.setVisible(true);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		gen.Start();
	}
	void Start() {	
		int clearX = 0;
		int clearY = 10;
		clear = new Clearer(clearX,clearY,cols,rows);
		walls = new boolean[rows][cols][4];
		visited = new boolean[rows][cols];
		visited[clearX][clearY] = true;
		for(int i=0;i<rows;i++) {
			for(int j=0;j<cols;j++) {
				for(int k=0;k<4;k++) {
					walls[i][j][k] = true;
				}
			}
		}
		walls[clearX][clearY][3] = false;
		repaint();
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		long genertaionStart = System.nanoTime();
		while(!done) {
			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Update();
			done = CheckFininshed();
		}
		long generationEnd = System.nanoTime();
		Random r = new Random();
		int endX = rows-1;
		int endY = r.nextInt(cols-1);
		if(endY==0){
			endY+=1;
		}
		walls[endX][endY][1] = false;
		Vector2 end = new Vector2(endX,endY+1);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e3) {
			e3.printStackTrace();
		}
		int startX = 0;
		int startY = 10;
		solver = new Solver(new Vector2(startX,startY),end,walls.clone(),rows,cols);
		long startTime = System.nanoTime();
		for(int i=0;i<1000000000;i++) {
			try {
				solver.Solve(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(solver.pos.x==end.x&&solver.pos.y==end.y) {
				break;
			}
			try {
				TimeUnit.MILLISECONDS.sleep(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long finished = System.nanoTime();
		long elapsedTime = finished - startTime;
		long gen = generationEnd - genertaionStart;
		double genElapsed = TimeUnit.MILLISECONDS.convert(gen , TimeUnit.NANOSECONDS);
		double secondsElapsed = TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
		System.out.println("Milliseconds taken to solve = "+secondsElapsed+"ms");
		System.out.println("Milliseconds taken to Generate = "+genElapsed+"ms");
		repaint();
	}
	void Update() {
		Vector2 old = null;
		try {
			old = (Vector2) clear.pos.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		visited = clear.Change(visited);
		Vector2 cur = null;
		try {
			cur = (Vector2)clear.pos.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		ClearWall(old,cur,-1);
		boolean shouldBacktrack = ShouldBacktrack();
		if(shouldBacktrack) {
			clear.BackTrack(this);
			//System.out.println("Backtracking");
		}
		repaint();
	}
	boolean CheckFininshed() {
		boolean isDone = true;
		for(int i=0;i<visited.length;i++) {
			for(int j=0;j<visited[i].length;j++) {
				if(!visited[i][j]) {
					isDone = false;
					break;
				}
			}
			if(!isDone) {
				break;
			}
		}
		return isDone;
	}
	boolean ShouldBacktrack() {
		Vector2 start = null;
		try {
			start = (Vector2) clear.pos.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		boolean should = true;
		if(start.x+1<rows) {
			if(!visited[start.x+1][start.y]) {
				should = false;
			}
		}if(start.x-1>=0) {
			if(!visited[start.x-1][start.y]) {
				should = false;
			}
		}if(start.y+1<cols) {
			if(!visited[start.x][start.y+1]) {
				should = false;
			}
		}if(start.y-1>=0) {
			if(!visited[start.x][start.y-1]) {
				should = false;
			}
		}
		return should;
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.clearRect(0, 0, frameSize, frameSize);
		g.translate(15, 0);
		if(walls!=null) {
			for(int i=0;i<rows;i++) {
				for(int j=0;j<cols;j++) {
					for(int k=0;k<4;k++) {
						if(visited!=null) {
							if(visited[i][j]) {
								g.setColor(Color.RED);
							}
						}
						if(walls[i][j][k]) {
							int offsetX = 1;
	                    	int offsetY = 1;
	                    	int x = (i*res);
	                    	int y = (j*res);  
	                    	if(k==0&&walls[i][j][k]){
	                        	g.drawLine(x+offsetX,y+offsetY,(x+res)-offsetX,y+offsetY);
	                    	}else if(k==1&&walls[i][j][k]){
	                        	g.drawLine((x+res)-offsetX,y+offsetY,(x+res)-offsetX,y+res-offsetY);
	                    	}else if(k==2&&walls[i][j][k]){
	                    		g.drawLine((x+res)-offsetX,y+res-offsetY,x+offsetX,y+res-offsetY);
	                    	}else if(k==3&&walls[i][j][k]){
	                        	g.drawLine(x+offsetX,y+res-offsetY,x+offsetX,y+offsetY);
	                    	}

						}
                    	g.setColor(Color.BLACK);
                    
					}
				}
			}
		}
		if(solver==null) {
			g.setColor(Color.BLUE);
			if(clear!=null) {
			g.fillOval((clear.pos.x*res)+(res/4), (clear.pos.y*res)+(res/4), res/2, res/2);
			}
		}
		if(solver!=null) {
			g.setColor(Color.MAGENTA);
			g.fillOval((solver.pos.x*res)+(res/4), (solver.pos.y*res)+(res/4), res/2, res/2);
		}
		g.setColor(Color.BLACK);
	}
	void ClearWall(Vector2 pre,Vector2 cur,int direction) {
		int dir = 0;
		if(direction==-1) {
			dir = clear.direction;
		}else {
			dir = direction;
		}
		if(dir!=-1) {
			int formatted = FormatDir(dir);
			if(formatted!=-1) {
				walls[pre.x][pre.y][formatted] = false;
				int inversed = (formatted +2+4)%4;
				walls[cur.x][cur.y][inversed] = false;
			}
		}
	}
	int FormatDir(int original) {
		int formatted = -1;
		if(original ==0) {
			formatted = 1;
		}else if(original==1) {
			formatted = 2;
		}else if(original==2) {
			formatted = 3;
		}else if(original==3) {
			formatted = 0;
		}
		return formatted;
	}
}
