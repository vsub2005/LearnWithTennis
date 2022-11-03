//Vinay Subramanian
//4-21-20	Period 2
//AceFrame.java
//This set of classes creates a JFrame, which holds a panel, created by 
//the class MainPanel. MainPanel has a card layout, so from the starting panel,
//the user can go to any panel.

import java.awt.Graphics;
import java.awt.LayoutManager;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AceFrame 
{
	public void AceFrame()
	{
		
	}
	
	public static void main(String[] args)
	{
		AceFrame af = new AceFrame();
		af.openGame();
	}
	
	//This method opens the start panel, where the user
	//can start playing the game, read the instructions,
	//check high scores, etc.
	public void openGame()
	{
		JFrame aceLearning = new JFrame("Ace Learning");
		aceLearning.setSize(1000, 800);
		aceLearning.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainPanel mp = new MainPanel();
		aceLearning.getContentPane().add(mp);
		aceLearning.setResizable(false);
		aceLearning.setVisible(true);
	}
}

class MainPanel extends JPanel
{
	private CardLayout cards;	//for the card layout
	private JPanel cardPanels;	//for the card layout
	//all of the panels being added to the card layout
	private StartPanel startPanel;	//instance of startPanel
	private InstructionPanel instructionsPanel;	//instance of instructionsPanel
	private HighScoresPanel highScoresPanel;	//instance of highScoresPanel
	private SettingsPanel settingsPanel;	//instance of settingsPanel
	private GamePanel gamePanel;	//instance of game panel
	private Profile userProfile;	//instance of profile
	
	public MainPanel()
	{
		//creating card layout and initializing panels that go into the card layout
		cards = new CardLayout();
		setLayout(cards);
		cardPanels = new JPanel(cards);
		//startPanel = new StartPanel(this);
		instructionsPanel = new InstructionPanel(this);
		setHighScoresPanel(new HighScoresPanel(this));
		settingsPanel = new SettingsPanel(this);
		gamePanel = new GamePanel(this);
		startPanel = new StartPanel(this);
		userProfile = new Profile(this);
		
		//adding panels to the card layout
		cardPanels.add(userProfile, "Login");
		cardPanels.add(startPanel, "Start");
		cardPanels.add(instructionsPanel, "Instructions");
		cardPanels.add(getHighScoresPanel(), "High Scores");
		cardPanels.add(settingsPanel, "Settings");
		cardPanels.add(gamePanel, "Game");
		
		//adding the panel with the card layout to the main panel
		add(cardPanels);
	}

	public HighScoresPanel getHighScoresPanel() 
	{
		return highScoresPanel;
	}

	public void setHighScoresPanel(HighScoresPanel highScoresPanel) 
	{
		this.highScoresPanel = highScoresPanel;
	}	
	
	public CardLayout getCards() 
	{
		return cards;
	}

	public void setCards(CardLayout cards) 
	{
		this.cards = cards;
	}

	public JPanel getCardPanels() 
	{
		return cardPanels;
	}

	public void setCardPanels(JPanel cardPanels) 
	{
		this.cardPanels = cardPanels;
	}

	public StartPanel getStartPanel() 
	{
		return startPanel;
	}

	public void setStartPanel(StartPanel startPanel) 
	{
		this.startPanel = startPanel;
	}

	public InstructionPanel getInstructionsPanel() 
	{
		return instructionsPanel;
	}

	public void setInstructionsPanel(InstructionPanel instructionsPanel) 
	{
		this.instructionsPanel = instructionsPanel;
	}

	public SettingsPanel getSettingsPanel() 
	{
		return settingsPanel;
	}

	public void setSettingsPanel(SettingsPanel settingsPanel) 
	{
		this.settingsPanel = settingsPanel;
	}

	public GamePanel getGamePanel() 
	{
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel) 
	{
		this.gamePanel = gamePanel;
	}

	public Profile getUserProfile() 
	{
		return userProfile;
	}

	public void setUserProfile(Profile userProfile) 
	{
		this.userProfile = userProfile;
	}
}