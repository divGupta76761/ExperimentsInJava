package NormalExperiments;

import java.awt.Color;
import java.awt.*;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.* ;

public class Slashes extends JPanel{
	int x = 0;
	int y = 0;
	static int frameSize = 600;
	Slashes(){
		setLayout(null);
	}
	public static void main(String[] args) {
		Slashes s = new Slashes();
		JFrame f = new JFrame();
		f.setSize(frameSize,frameSize);
		f.setDefaultCloseOperation(3);
		f.getContentPane().add(s);
		f.setVisible(true);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		
	}
	void Start() {
		repaint();
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		y = 0;
		x = 0;
		Random r = new Random();
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(3));
		for(int i=0;i<60;i++) {
			for(int j=0;j<60;j++) {
				if(r.nextFloat()>0.5f) {
					g2.drawLine(0+x, 0+y, 8+x, 10+y);
				}else {
					g2.drawLine(10+x, 0+y, 0+x, 10+y);
				}
				x+=10;
			}
			y+=10;
			x=0;
		}
	}
}
