//Vinay Subramanian
//5-20-20
//WinFrame.java
//This class displays a j frame that tells the user they won

import java.awt.Graphics;



import java.awt.LayoutManager;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class WinPanel extends JPanel implements ActionListener
{
	Timer confettiTimer;	//Timer used to move the confetti
	private int yInc;		//increment at which the confetti moves down
	private int[] confettiX;	//all x values for the confetti
	private int[] confettiY;	//all y values for confetti
	
	public WinPanel()
	{
		setBackground(Color.GREEN);
		confettiTimer = new Timer(30, this);
		confettiX = new int[]{20, 40, 60, 80, 100, 120, 140, 160, 180, 200, 220, 240, 260, 280};
		confettiY = new int[]{20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20};
		yInc = 2;
		confettiTimer.start();
	}
	public void paintComponent(Graphics g)
	{
		int colorChoose;
		super.paintComponent(g);
		g.setColor(Color.YELLOW);
		g.drawArc(85, 50, 50, 50, 90, 230);
		g.fillArc(100, -35, 100, 150, 180, 180);
		g.drawArc(165, 50, 50, 50, 90, -230);
		g.fillRect(140, 95, 20, 100);
		g.fillRect(110, 175, 80, 20);
		
		g.setColor(Color.BLUE);
		for(int i = 0; i < confettiX.length; i++)
		{
			colorChoose = (int)(Math.random()*2 + 1);	//each confetti piece flickers from blue to pink based on number
			if(colorChoose == 1)
			{
				g.setColor(Color.BLUE);
			}
			else
			{
				g.setColor(Color.PINK);
			}
			g.fillRect(confettiX[i], confettiY[i], 10, 10);
			confettiY[i] += yInc;
		}
	}
	public void actionPerformed(ActionEvent evt)
	{
		this.repaint();
	}
}