package TopplingSand;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class Toppling extends JPanel{
	static int frameSize = 600;
	int[][]piles;
	BufferedImage mainImage;
	Toppling(){
		setLayout(null);
		mainImage = new BufferedImage(frameSize,frameSize,BufferedImage.TYPE_INT_RGB);
		piles = new int[frameSize][frameSize];
		piles[300][300] = 100000;
	}
	public static void main(String[] args) {
		Toppling toppling = new Toppling();
		JFrame f = new JFrame();
		f.setSize(frameSize,frameSize);
		f.setDefaultCloseOperation(3);
		f.getContentPane().add(toppling);
		f.setVisible(true);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		toppling.Start();
	}
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
	void NextGen() {
		int [][]next = piles.clone();
		for(int i=0;i<piles.length;i++) {
			for(int j=0;j<piles[i].length;j++) {
				int num = piles[i][j];
				if(num>=4) {
					if(i-1>=0) {
						next[i][j]--;
						next[i-1][j]++;
					}
					if(i+1<frameSize) {
						next[i][j]--;
						next[i+1][j]++;
					}
					if(j+1<frameSize) {
						next[i][j]--;
						next[i][j+1]++;
					}
					if(j-1>=0) {
						next[i][j]--;
						next[i][j-1]++;
					}
				}
			}
		}
		piles = next;
	}
	void Update() {
		NextGen();
		
		repaint();
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i=0;i<piles.length;i++) {
			for(int j=0;j<piles[i].length;j++) {
				if(piles[i][j]==0) {
					mainImage.setRGB(i, j, new Color(255,255,0).getRGB());
				}else if(piles[i][j]==1) {				

					mainImage.setRGB(i, j, new Color(0,185,63).getRGB());
				}else if(piles[i][j]==2) {
					mainImage.setRGB(i, j, new Color(0,104,255).getRGB());
				}else if(piles[i][j]==3) {

					mainImage.setRGB(i, j, new Color(122,0,229).getRGB());
				}
			}
		}
		
		g.drawImage(mainImage, 0, 0, frameSize, frameSize, null);
	}

}
