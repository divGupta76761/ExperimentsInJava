package ImageExperiments;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import Vector2.Vector2;
import Mapper.Mapper;
public class MetaBall extends JPanel{
	static Blob[]blobs;
	static int frameSize = 600;
	int total = 4;
	BufferedImage mainImage;
	MetaBall(){
		blobs = new Blob[total];
		Random r = new Random();
		for(int i=0;i<blobs.length;i++) {
			Vector2 v = new Vector2(r.nextInt(4)+2,r.nextInt(4)+2);
			v.mult(0.7f);
			Vector2 vel = null;
			try {
				vel = (Vector2) v.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			blobs[i] = new Blob(r.nextInt(frameSize),r.nextInt(frameSize),vel,frameSize);
		}
		setLayout(null);
	}
	public static void main(String[] args) {
		MetaBall ball = new MetaBall();
		JFrame f = new JFrame();
		f.setSize(frameSize,frameSize);
		f.setDefaultCloseOperation(3);
		f.getContentPane().add(ball);
		f.setVisible(true);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		ball.Start();
	}
	void Start() {
		mainImage = new BufferedImage(frameSize,frameSize,BufferedImage.TYPE_INT_RGB);
		while(true) {
			Update();
			try {
				TimeUnit.MILLISECONDS.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	void Update() {
		for(int i=0;i<blobs.length;i++) {
			blobs[i].Update();
		}
		for(int i=0;i<mainImage.getWidth();i++) {
			for(int j=0;j<mainImage.getHeight();j++) {
				float sum = 0;
				
				for(int k=0;k<blobs.length;k++) {
					if(blobs[k].show) {
						Blob b = blobs[k];
						float dis = (float) (Math.sqrt(Math.pow((b.pos.x-i),2)+Math.pow(b.pos.y-j,2)));
						sum+=200*blobs[k].r/dis;	
					}
				}
				int c = (int)sum;
				//Color col = Color.getHSBColor(sum, 1, 1);
				mainImage.setRGB(i, j, c);
			}
		}
		repaint();
	}
	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, frameSize, frameSize);
		g.drawImage(mainImage, 0, 0, frameSize, frameSize,null);
		/*for(int i=0;i<blobs.length;i++) {
			g.setColor(Color.BLACK);
			Blob b = blobs[i];
			g.fillOval(b.pos.x, b.pos.y, b.r*2, b.r*2);
		}*/
	}
	class Blob {
		int size;
		Vector2 pos;
		Vector2 vel;
		int r;
		Random rand;
		boolean show = true;
		public Blob(int x,int y,Vector2 v,int size) {
			rand = new Random();
			r = 50;//rand.nextInt(50)+10;
			pos = new Vector2(x,y);
			vel = v;
			this.size = size;
		}
		public void Update() {
				if(show) {
				pos.add(vel);
				if(pos.x>=size) {
					vel.x*=-1;
				}if(pos.x<=0) {
					vel.x*=-1;
				}
				if(pos.y>=size) {
					vel.y*=-1;
				}if(pos.y<=0) {
					vel.y*=-1;
				}
			}else {
				
			}
		}
	}
	float Squish(float x) {
		return (float)(1/1+(Math.pow(Math.E,-x)));
	}
}
