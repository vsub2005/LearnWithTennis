// Sameer Maheshwari
// HighScoresPanel.java
// 5-1-20
// This panel will hold the highscores for the players that play our game
// Later, we will add a user password system where users can log in with their name 
// and then after playing the game, will display how many games they have won in total. 
import java.io.File;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

import java.awt.FlowLayout;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel; 
import javax.swing.JPanel;

import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent; 

import java.awt.Dimension;


public class HighScoresPanel extends JPanel
{
	private MainPanel instance;	//instance of MainPanel used to access game panel
	private Scanner readerUserInfo;	//Scanner to read UserInfo.txt
	private HighScoresPanel hsInstance;	//this instance of high scores panel
	private PrintWriter writerReplacement;	//Print writer used to write to a replacement file
	private int[] scores;	//array of all of the high scores
	private String[] names;	//array of all of the high score usernames
	private Profile getWins;	//instance of profile that is used in MainPanel
	private ListedScores ls;	//instance of listed scores
	private String getUsername;	//holds the username that is entered
	private String getPassword;	//holds the password that is entered
	
	// This is a borderlayout, and the panel in the north holds the high scores
	// and the panel in the south holds the buttons to go back to start, quit, and play the
	// game instantly
	public HighScoresPanel(MainPanel mainPanel2)
	{
		setLayout(new BorderLayout());
		setBackground(Color.RED);
		instance = mainPanel2;
		hsInstance = this;
		ls = new ListedScores();
		ls.setPreferredSize(new Dimension(1000, 600));
		add(ls, BorderLayout.NORTH);
				
		ButtonMovement bm = new ButtonMovement(instance);
		bm.setPreferredSize(new Dimension(1000, 200));
		add(bm, BorderLayout.SOUTH);
	}	
	public void getReplaceScores()
	{
		ls.replaceScores();
		hsInstance.remove(ls);
		ls = new ListedScores();
		ls.setPreferredSize(new Dimension(1000, 600));
		hsInstance.add(ls, BorderLayout.NORTH);
		hsInstance.revalidate();
		hsInstance.repaint();
	}
	public String getGetUsername() 
	{
		return getUsername;
	}
	public void setGetUsername(String getUsername) 
	{
		this.getUsername = getUsername;
	}
	class ListedScores extends JPanel
	{
		// The high scores are all JLabels, and listed in a flowlayout, with a space of 75 in
		// between each of them. They are all set to an arbitruary font to look good. 
		public ListedScores()
		{
			readerUserInfo = null;
			writerReplacement = null;
			
			setBackground(Color.RED);
			
			getWins = new Profile(instance);
			setLayout(new FlowLayout(FlowLayout.CENTER, 800, 75));
		
			Font font1 = new Font("Serif", Font.BOLD, 30);
			scores = new int[5];
			for(int i = 0; i < scores.length; i++)
			{
				scores[i] = -1;
			}
			names = new String[5];
			openUserFiles();
			getHighScores();
		
			JLabel one = new JLabel("1. " + names[0] + ", " + scores[0]);
			one.setForeground(Color.BLUE);
			one.setFont(font1);
			add(one);
			
			JLabel two = new JLabel("2. " + names[1] + ", " + scores[1]);
			two.setFont(font1);
			add(two);
		
			JLabel three = new JLabel("3. " + names[2] + ", " + scores[2]);
			three.setFont(font1);
			add(three);
		
			JLabel four = new JLabel("4. " + names[3] + ", " + scores[3]);
			four.setFont(font1);
			add(four);
		
			JLabel five = new JLabel("5. " + names[4] + ", " + scores[4]);
			five.setFont(font1);
			add(five);
		}
		public void openUserFiles()
		{
			String readInOutUserInfo = new String("UserInfo.txt");
			File readInOutFile1 = new File(readInOutUserInfo); 
			String readInOutReplacement = new String("ReplacementFile.txt");
			File readInOutFile2 = new File(readInOutReplacement);

			try
			{
				readerUserInfo = new Scanner(readInOutFile1);
			}
			catch(FileNotFoundException e)
			{
				System.err.println("Cannot read file " + readInOutUserInfo);
				System.exit(7);
			}
			
			try
			{
				writerReplacement = new PrintWriter(readInOutFile2);
			}
			catch(IOException e)
			{
				System.err.println("Cannot write to file " + readInOutReplacement);
				System.exit(11);
			}
		}
		
		public void getHighScores()
		{
			String collectName;
			int counter = 0;
			int collectScore = 0;
			boolean placeHigh = false;
			String lineIn = new String("");
			while(readerUserInfo.hasNext())
			{
				lineIn = readerUserInfo.nextLine();
				String[] infoUser = lineIn.split(",");
				
				collectName = infoUser[0];
				collectScore = Integer.parseInt(infoUser[2]);
				for(int j = 0; j < scores.length; j++)
				{
					if(scores[j] < collectScore)
					{
						 if(scores[j] != -1)
						 {
						 	changeScores(j);
						 }
					 	
						 scores[j] = collectScore;
						 names[j] = collectName;
						 placeHigh = true;
					}
					if(placeHigh)
						j = scores.length;					
				}
				placeHigh = false;
			}
			readerUserInfo.close();
			writerReplacement.close();
		}
		public void changeScores(int startingPoint)
		{
			for(int k = scores.length - 1; k > startingPoint; k-- )
			{
				scores[k] = scores[k - 1];
				names[k] = names[k - 1];
			}
		
		}
		public void replaceScores()
		{
			String getLine = new String("");
			openUserFiles();
			setGetUsername(instance.getUserProfile().getUsernameStr());
			getPassword = instance.getUserProfile().getPasswordStr();
			
			while(readerUserInfo.hasNext())
			{
				getLine = readerUserInfo.nextLine();
				if(getLine.indexOf(getGetUsername() + ",") == 0)
					writerReplacement.println(getGetUsername() + "," + getPassword + "," + instance.getUserProfile().getTotalScore());
				else
					writerReplacement.println(getLine);
			}
			
			readerUserInfo.close();
			writerReplacement.close();
			File userInfoFile = new File("UserInfo.txt");
			File replacementFile = new File("ReplacementFile.txt");
			
			replacementFile.renameTo(userInfoFile);
		}
	
		
	}
}

class ButtonMovement extends JPanel
{
	// This is a borderlayout, which is hold the buttons in the west, center, and the east
	// The button in the west will allow the user to travel back to the StartPanel
	// The button in the center will allow the user to instantly play the game
	// The button in the eat will allow the user to instantly quit the program if required
	public ButtonMovement(MainPanel mp)
	{
		setLayout(new FlowLayout(FlowLayout.CENTER, 30, 75));
		setBackground(Color.RED);
		ButtonHandler2 bh2 = new ButtonHandler2(mp);
		
		JButton home = new JButton("Back to Home");
		home.addActionListener(bh2);
		add(home, BorderLayout.WEST);
		
		JButton quit2 = new JButton("Quit");
		quit2.addActionListener(bh2);
		add(quit2, BorderLayout.EAST);
		
		JButton playGame = new JButton("Play Game");
		playGame.addActionListener(bh2);
		add(playGame, BorderLayout.CENTER);
	}
	
	class ButtonHandler2 implements ActionListener
	{
		private MainPanel getVars; // This is an instance of MainPanel used for access its Cardlayout
		// The constructor is used to initialize getVars
		public ButtonHandler2(MainPanel mp2)
		{
			getVars = mp2;
		}
		// This is the actionListener class for the buttons and uses the command pressed
		// to take them to the respective places
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			
			getVars.getCards().removeLayoutComponent(getVars.getGamePanel());
			getVars.getCardPanels().add(getVars.getGamePanel(), "Game");
			
			if(command.equals("Back to Home"))
				getVars.getCards().show(getVars.getCardPanels(), "Start");
			else if(command.equals("Quit"))
			{
				System.exit(1);
			}
			else if(command.equals("Play Game"))
				getVars.getCards().show(getVars.getCardPanels(), "Game");	
		}
	}
}