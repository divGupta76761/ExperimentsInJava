package MazeGenerator;

import Vector2.Vector2;

public class DepthFirstSolver {
	Vector2 pos;
	Vector2 end;
	boolean[][][]walls;
	int rows;
	int cols;
	Cell[][]cells;
	public DepthFirstSolver(Vector2 startpos,Vector2 endpos, boolean[][][]_walls,int row,int col) {
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
		Init();
	}
	void Init() {
		cells = new Cell[rows][cols];
		for(int i=0;i<rows;i++) {
			for(int j=0;j<cols;j++) {
				try {
					cells[i][j] = new Cell((Vector2)new Vector2(i,j).clone(),0,rows,cols);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	public void Solve(GridGenerator grid) throws Exception {
		
	}
	class Cell{
		Vector2 pos;
		int dir;
		int rows;
		int cols;
		public Cell(Vector2 p,int d,int r, int c) throws Exception{
			pos = (Vector2)p.clone();
			dir = d;
			rows = r;
			cols = c;
		}
		public void ChangeDir() {
			int p = dir;
			int newDir = 0;
			if(p==3) {
				Vector2 n = GetNeighbour(p);
				if(n!=null) {
					dir = 3;
					
				}
			}
		}
		public void move() {
			if(dir==0) {
				pos.y--;
			}else if(dir==1) {
				pos.x++;
			}else if(dir==2) {
				pos.y++;
			}else if(dir==3) {
				pos.x--;
			}
		}
		public Vector2 GetNeighbour(int dir) {
			Vector2 n = null;
			if(dir==0) {
				if(pos.y-1>=0) {
					if(!walls[pos.x][pos.y][0]) {
						n = new Vector2(pos.x,pos.y-1);
					}
				}
			}else if(dir==1) {
				if(pos.x+1<rows) {
					if(!walls[pos.x][pos.y][1]) {
						n = new Vector2(pos.x+1,pos.y);
					}
				}
			}else if(dir==2) {
				if(pos.y+1<cols) {
					if(!walls[pos.x][pos.y][2]) {
						n = new Vector2(pos.x,pos.y+1);
					}
				}
			}else if(dir==3) {
				if(pos.x-1>=0) {
					if(walls[pos.x][pos.y][3]) {
						n = new Vector2(pos.x-1,pos.y);
					}
				}
			}
			return n;
		}
	}
}
