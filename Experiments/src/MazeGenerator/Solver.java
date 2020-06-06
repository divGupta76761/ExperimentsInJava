package MazeGenerator;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import Vector2.Vector2;

public class Solver {
	Vector2 pos;
	Vector2 end;
	boolean[][][]walls;
	int rows;
	int cols;
	Vector2 pre = null;
	ArrayList<Vector2>backtrack;
	public Solver(Vector2 startpos,Vector2 endpos, boolean[][][]_walls,int row,int col) {
		rows = row;
		cols = col;
		try {
			pos = (Vector2) startpos.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		try {
			end = (Vector2) endpos.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		this.walls = _walls.clone();
	}
	public void Solve(GridGenerator grid) throws Exception {
		Random r = new Random();
		ArrayList<Vector2>neighbours = GetNeighbours((Vector2)pos.clone());
		int len = 0;
		for(int i=0;i<neighbours.size();i++) {
			if(neighbours.get(i)!=null) {
				len++;
			}
		}
		if(len>0) {
			int index  = r.nextInt(len);
			pre = (Vector2)pos.clone();
			pos = (Vector2) neighbours.get(index).clone();
		}else {
			Vector2 temp = (Vector2)pos.clone();
			pos = (Vector2)pre.clone();
			pre = (Vector2)temp.clone();
		}
		grid.repaint();
	}
	int GetDirection(Vector2 start,Vector2 end) {
		int dir = -1;
		if(start.x+1==end.x) {
			dir = 0;
		}if(start.y+1==end.y) {
			dir = 2;
		}if(start.y-1==end.y) {
			dir = 3;
		}if(start.x-1==end.x) {
			dir = 1;
		}
		
		return dir;
	}
	ArrayList<Vector2> GetNeighbours(Vector2 start){
		ArrayList<Vector2>n = new ArrayList<Vector2>();
		if(pos.x<rows-1) {
			if(!walls[start.x][start.y][1]) {
				Vector2 neigh = new Vector2(start.x+1,start.y);
				n.add(neigh);

				
			}
		}if(pos.x>0) {
			if(!walls[start.x][start.y][3]) {
				Vector2 neigh = new Vector2(start.x-1,start.y);
				n.add(neigh);
				
			}
		}if(pos.y<cols-1) {
			if(!walls[start.x][start.y][2]) {
				Vector2 neigh = new Vector2(start.x,start.y+1);
				n.add(neigh);

			}
		}if(pos.y>0) {
			if(!walls[start.x][start.y][0]) {
				Vector2 neigh = new Vector2(start.x,start.y-1);
				n.add(neigh);
			}
		}
		for(int i=0;i<n.size();i++) {
			Vector2 ne = n.get(i);
			if(pre!=null) {
				if(ne.x==pre.x&&ne.y==pre.y) {
					n.remove(i);
				}
			}
		}
		return n;
	}
}
