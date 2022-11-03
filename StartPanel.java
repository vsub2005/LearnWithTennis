//Vinay Subramanian
//4-22-20	Per. 2
//StartPanel.java
//This class extends JPanel, and is used in AceFrame, as the starting panel. From
//this panel, the user can click buttons to go to any other panels. This will be
//the first panel in the card layout.
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

public class StartPanel extends JPanel
{
	private BackgroundPanel bp;	//JPanel that holds the image
	private MainPanel mainPanelInst;	//instance mainPanel used for accessing other instances
	private JFrame winFrame;	//a j frame that pops up if the user wins the game

	public StartPanel(MainPanel mainPanel)
	{
		setLayout(null); //null layout being used for the start panel
		//declaring and initializing all buttons and panels (which is for the card layout)
		
		mainPanelInst = mainPanel;
		bp = new BackgroundPanel();
		
		JButton startButton = new JButton("Start Playing");
		JButton instructionsButton = new JButton("Instructions");
		JButton highScoresButton = new JButton("High Scores");
		JButton settingsButton = new JButton("Settings");
		JButton quitButton = new JButton("Quit");
		
		//instance of a button handler class
		StartPanelButtonHandler bh = new StartPanelButtonHandler(mainPanelInst);
		//setting sizes for all of the buttons
		startButton.setSize(100, 40);
		instructionsButton.setSize(100, 40);
		highScoresButton.setSize(100, 40);
		settingsButton.setSize(100, 20);
		quitButton.setSize(100, 20);
		
		//setting locations for all of the buttons
		startButton.setLocation(175, 600);
		instructionsButton.setLocation(725, 600);
		highScoresButton.setLocation(460, 600);
		settingsButton.setLocation(175, 725);
		quitButton.setLocation(725, 725);
				
		//adding buttons to the start panel
		add(startButton);
		add(instructionsButton);
		add(highScoresButton);
		add(settingsButton);
		add(quitButton);
				
		startButton.addActionListener(bh);
		instructionsButton.addActionListener(bh);
		highScoresButton.addActionListener(bh);
		settingsButton.addActionListener(bh);
		quitButton.addActionListener(bh);
	}
	public void paintComponent(Graphics g)
	{
		Font font = new Font("Serif", Font.BOLD, 50);
		super.paintComponent(g);
		Image backImage = bp.backgroundImage;
		g.drawImage(backImage, 0, 0, 1000, 800, this);
		g.setColor(Color.CYAN);
		g.setFont(font);
		g.drawString("Ace Learning", 350, 300);
	}
	
	public void showWinFrame()
	{
		WinPanel wp = new WinPanel();
		winFrame = new JFrame("You Win!");
		winFrame.setDefaultCloseOperation(winFrame.DISPOSE_ON_CLOSE);
		winFrame.setSize(300, 300);
		winFrame.setLocation(310, 220);
		winFrame.getContentPane().add(wp);
		winFrame.setVisible(true);
	}
	class BackgroundPanel extends JPanel
	{
		private Image backgroundImage;	//image that is in the background
		public BackgroundPanel()
		{
			backgroundImage = getMyImage();
		}
		public Image getMyImage()
		{
			Image img = null;
			String fileName = new String("AceFrameCoverImage.jpg");
			File imageFile = new File("Images/" + fileName);
			try
			{
				img =  ImageIO.read(imageFile);
			}
			catch(IOException e)
			{
				System.err.println("Cannot open picture " + fileName );
				e.printStackTrace();
			}
			return img;
		}
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(backgroundImage, 0, 0, 1000, 800, this);
		}
	}
	class StartPanelButtonHandler implements ActionListener
	{
		private MainPanel mainPanel;	//instance of mainPanel for accessing the different panels
		public StartPanelButtonHandler(MainPanel mp)	//for button handler to access card layout
		{
			mainPanel = mp;
		}
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			GamePanel gp = new GamePanel(mainPanel);
			
			//had to do this so that every time we went back to the game panel,
			//a new one would show up, not the original instance which has not been reset
			mainPanel.getCards().removeLayoutComponent(mainPanel.getGamePanel());
			mainPanel.getCardPanels().add(gp, "Game");
		
			//for game panel to show up
			if(command.equals("Start Playing"))
				mainPanel.getCards().show(mainPanel.getCardPanels(), "Game"); 
				
			//for instructions panel to show up
			else if(command.equals("Instructions"))
				mainPanel.getCards().show(mainPanel.getCardPanels(), "Instructions");
			//for high scores panel to show up
			else if(command.equals("High Scores")) 
				mainPanel.getCards().show(mainPanel.getCardPanels(), "High Scores");
			//for settings to show up
			else if(command.equals("Settings"))
				mainPanel.getCards().show(mainPanel.getCardPanels(), "Settings");
			//for quitting
			else if(command.equals("Quit"))
				System.exit(0);
		}
	}
}