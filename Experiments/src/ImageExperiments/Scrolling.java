package ImageExperiments;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import SimplexNoise.OpenSimplexNoise;
import javax.swing.*;
import Mapper.Mapper;

public class Scrolling extends JPanel{
	int zoom = 2;
	OpenSimplexNoise noise;
	BufferedImage mainImage;
	static int frameSize = 600;
	Scrolling(){
		noise = new OpenSimplexNoise();
		mainImage = new BufferedImage(frameSize/zoom,frameSize/zoom,BufferedImage.TYPE_INT_RGB);
		setLayout(null);
	}
	public static void main(String[] args) {
		Scrolling scrol = new Scrolling();
		JFrame f = new JFrame();
		f.setSize(frameSize,frameSize);
		f.setDefaultCloseOperation(3);
		f.getContentPane().add(scrol);
		f.setVisible(true);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		scrol.Start();
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(mainImage, 0, 0, frameSize, frameSize,null);
	}
	float time = 0f;
	void Start() {
		while(true) {
			Update();
			try {
				TimeUnit.MILLISECONDS.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	void Update() {
		for(int i=0;i<frameSize/zoom;i++) {
			for(int j=0;j<frameSize/zoom;j++) {
				float sample = 0f;
				float sample1 = (float) (noise.eval(i*.5, (j*.4), (time*.3))); 
				float sample2 = (float) (noise.eval((i*.5), (j*.4), (time*.3))); 
				float sample3 = (float) (noise.eval((i*.5), (j*.4), (time*.3))); 
				sample = sample1+sample2+sample3;
				sample = Mapper.map(sample, -3f, 3f, -1, 1);
				sample = Mapper.map(sample, -1, 1, 0, 255);
				int bright = (int) sample;
				Color c = new Color(bright,bright,bright);
				mainImage.setRGB(i, j, c.getRGB());
			}
		}
		repaint();
		time+=0.01f;
	}
}
