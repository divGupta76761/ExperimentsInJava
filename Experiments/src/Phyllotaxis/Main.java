package Phyllotaxis;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class Main extends JPanel{
	int n = 0;
	int c = 6;
	static int frameSize = 600;
	Main(){
		setLayout(null);
	}
	public static void main(String[] args) {
		Main main = new Main();
		JFrame f = new JFrame();
		f.setSize(frameSize,frameSize);
		f.setDefaultCloseOperation(3);
		f.getContentPane().add(main);
		f.setVisible(true);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		main.Start();
	}
	void Start() {
		while(true) {
			Update();
			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}void Update(){
		
		n++;
		repaint();
	}
	public void paintComponent(Graphics g) {
		g.translate(frameSize/2, frameSize/2);
		//angles = 137.5,137.3,137.6
		float a = n*137.5f;
		float r = (float) (c * Math.sqrt(n));
		int x = (int) Math.round(r*Math.cos(a));
		int y = (int) Math.round(r*Math.sin(a));
		g.setColor(GetColor());
		g.fillOval(x, y, c, c);
	}
	Color GetColor() {
		int i = (n+7)%7;
		Color c = null;
		if(i==0) {
			c = new Color(50,0,100);
		}else if(i==1) {
			c = new Color(29,0,51);
		}else if(i==2) {
			c = Color.BLUE;
		}else if(i==3) {
			c = Color.GREEN;
		}else if(i==4) {
			c = Color.YELLOW;
		}else if(i==5) {
			c = Color.ORANGE;
		}else if(i==6) {
			c = Color.RED;
		}
		return c;
	}
}
