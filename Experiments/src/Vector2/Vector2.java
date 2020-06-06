package Vector2;

public class Vector2 implements Cloneable {
	public int x;
	public int y;
	public Vector2(int x,int y) {
		this.x = x;
		this.y = y;
	}
	public void add(Vector2 other) {
		 this.x+=other.x;
		 this.y+=other.y;
	}
	public void mult(float other) {
		 this.x*=other;
		 this.y*=other;
	}
	public void mul(Vector2 other) {
		 this.x*=other.x;
		 this.y*=other.y;
	}
	public void sub(Vector2 other) {
		 this.x-=other.x;
		 this.y-=other.y;
	}
	public void div(Vector2 other) {
		 this.x/=other.x;
		 this.y/=other.y;
	}
	public Object clone()throws CloneNotSupportedException {
		return super.clone();
	}
	public static int CalculateDis(Vector2 start,Vector2 end) {
		int d = 0;
		d = (int) (Math.sqrt(Math.pow(end.x-start.x,2)+Math.pow(end.y-start.y, 2)));
		return d;
	}
}
