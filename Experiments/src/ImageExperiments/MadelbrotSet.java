package ImageExperiments;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
public class MadelbrotSet extends JPanel{
	BufferedImage mainImage;
	static int frameSize = 600;

	MadelbrotSet(){
		mainImage =new BufferedImage(frameSize,frameSize,BufferedImage.TYPE_INT_RGB);
		setLayout(null);
		 
	}
	public static void main(String[] args) {
		MadelbrotSet set = new MadelbrotSet();
		JFrame f = new JFrame();
		f.setSize(frameSize,frameSize);
		f.setDefaultCloseOperation(3);
		f.getContentPane().add(set);
		f.setVisible(true);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		set.Start();
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.clearRect(0, 0, frameSize, frameSize);
		g.drawImage(mainImage, 0, 0, frameSize, frameSize,null);
	}
	void Start() {
		float maxiterations = 100;
		for(int i=0;i<frameSize;i++) {
			for(int j=0;j<frameSize;j++) {
				float a = map(i, 0, frameSize, -2f, 2f);
				float b = map(j, 0, frameSize, -2f, 2f);
				int n = 0;
				float ca = a;
				float cb = b;
				while (n < maxiterations) {
			        float aa = a * a - b * b;
			        float bb = 2 * a * b;
			        a = aa + ca;
			        b = bb + cb;
			        if (a * a + b * b > 16) {
			          break;
			        }
			        n++;
			      }
				float bright = map(n, 0, maxiterations, 0, 1);
				bright = map((float)(Math.sqrt(bright)), 0f, 1f, 0f, 255f);
				if (n == maxiterations) {
			        bright = 0;
			    }
				int sample = (int)bright;
				Color c = new Color(sample,sample,sample);
				//Color c = Color.getHSBColor(sample, sample, sample);
				mainImage.setRGB(i, j, c.getRGB());
			}
		}
		repaint();
	}
	void Update() {
		
	}
	float map(float n,float start1,float stop1,float start2,float stop2) {
		float newval = (n - start1) / (stop1 - start1) * (stop2 - start2) + start2;
		return newval;
	}

}
