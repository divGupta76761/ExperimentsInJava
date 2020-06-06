package MazeGenerator;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import Vector2.Vector2;

public class Clearer {
	ArrayList<Vector2> backtrack;
	int cols;
	int rows;
	public int direction;
	public Vector2 pos;
	int index = 0;
	public Clearer(int x,int y,int col,int row) {
		backtrack = new ArrayList<Vector2>();
		pos = new Vector2(x,y);
		this.cols = col;
		this.rows = row;
		Save();
	}
	public boolean[][]Change(boolean[][]visited) {
		int dir = GetDir(visited);
		direction = dir;
		
		move(dir);
		if(!visited[pos.x][pos.y]) {
			visited[pos.x][pos.y] = true;
		}
		return visited;
	}
	int GetDir(boolean[][]visited) {
		Random r = new Random();
		int dir = r.nextInt(4);
		int end = -1;
		int curX = pos.x;
		int curY = pos.y;
		if(dir==0) {
			int pre = pos.x;
			pos.x++;
			if(!(pos.x<rows&&pos.x>=0)) {
				pos.x = pre;
			}
		}else if(dir==1) {
			int pre = pos.y;
			pos.y++;
			if(!(pos.y<cols&&pos.y>=0)) {
				pos.y = pre;
			}
		}else if(dir==2) {int pre = pos.x;
			pos.x--;
			if(!(pos.x<rows&&pos.x>=0)) {
				pos.x = pre;
			}
		}else if(dir==3) {int pre = pos.y;
			pos.y--;
			if(!(pos.y<cols&&pos.y>=0)) {
				pos.y = pre;
			}
		}
		if(!visited[pos.x][pos.y]) {
			end = dir;
		}
		pos.x = curX;
		pos.y = curY;
		return end;
	}
	public void Save() {
		Vector2 p = null;
		try {
			p = (Vector2) pos.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		backtrack.add(index, p);
		index++;
	}
	public void BackTrack(GridGenerator grid) {
		for(int i = backtrack.size()-1;i>0;i--) {
			try {
				pos = (Vector2) backtrack.get(i).clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			boolean stop = GetNeighbours(grid.visited.clone(),grid);
			if(stop) {
				grid.visited[pos.x][pos.y] = true;
				grid.repaint();

				break;
			}
			grid.repaint();

			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	boolean GetNeighbours(boolean[][]visited,GridGenerator grid){
		boolean shouldStop = false;
		int neighbourIndex = 0;
		Vector2 start = null;
		try {
			start = (Vector2) pos.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		//System.out.println("PosX = "+start.x+" PosY = "+start.y);
		ArrayList<Vector2>neighbours = new ArrayList<Vector2>();
		if(start.x+1<rows) {
			Vector2 neighbour = new Vector2(start.x+1,start.y);
			neighbours.add(neighbourIndex, neighbour);
			neighbourIndex++;
		}
		if(start.x-1>=0) {
			Vector2 neighbour = new Vector2(start.x-1,start.y);
			neighbours.add(neighbourIndex, neighbour);
			neighbourIndex++;
		}
		if(start.y+1<cols) {
			Vector2 neighbour = new Vector2(start.x,start.y+1);
			neighbours.add(neighbourIndex, neighbour);
			neighbourIndex++;
		}
		if(start.y-1>=0) {
			Vector2 neighbour = new Vector2(start.x,start.y-1);
			neighbours.add(neighbourIndex, neighbour);
			neighbourIndex++;
		}
		for(int i=0;i<neighbours.size();i++) {
			Vector2 n = null;
			try {
				n = (Vector2) neighbours.get(i).clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			//System.out.println("X = "+n.x+" Y = "+n.y);
			//System.out.println("Condition = "+visited[n.x][n.y]);
			if(!visited[n.x][n.y]) {
				int dir = ConvertToDir(start,n);
				//System.out.println("Direction = "+dir);
				grid.ClearWall(start, n, dir);
				shouldStop = true;
				move(dir);
			}
		}
		//System.out.println();
		return shouldStop;
	}
	int ConvertToDir(Vector2 start,Vector2 end) {
		int dir = -1;
		if(start.x+1==end.x) {
			dir = 0;
		}
		if(start.y+1==end.y) {
			dir = 1;
		}
		if(start.x-1==end.x) {
			dir = 2;
		}
		if(start.y-1==end.y) {
			dir = 3;
		}
		return dir;
	}
	void move(int dir) {
		if(dir==0) {
			int pre = pos.x;
			pos.x++;
			if(!(pos.x<rows&&pos.x>=0)) {
				pos.x = pre;
			}
		}else if(dir==1) {
			int pre = pos.y;
			pos.y++;
			if(!(pos.y<cols&&pos.y>=0)) {
				pos.y = pre;
			}
		}else if(dir==2) {int pre = pos.x;
			pos.x--;
			if(!(pos.x<rows&&pos.x>=0)) {
				pos.x = pre;
			}
		}else if(dir==3) {int pre = pos.y;
			pos.y--;
			if(!(pos.y<cols&&pos.y>=0)) {
				pos.y = pre;
			}
		}else {
			return;
		}
		Save();
	}
}
