// Sameer Maheshwari
// 4-21-20 Period 2
// InstructionPanel.java
// This panel will display the instructions by using 2 panels in a BorderLayout
// The first panel will be places in the north of the panel, and inside will have a 
// FLowLayout. That displays the instructions
// The second panel will be in the south and will have another borderlayout with three 
// buttons, a back button, a button to play the game, and the quit button. 

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea; 
import javax.swing.JPanel;
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent; 
import java.awt.Dimension;

public class InstructionPanel extends JPanel
{
	// This creates instances of the 2 panels being added and adds one panel
	// to the north and one panel to the south
	public InstructionPanel(MainPanel mainPanel)
	{
		setLayout(new BorderLayout());
		
		InstructionTitle it = new InstructionTitle();
		add(it, BorderLayout.NORTH);
		
		InstructionSteps is = new InstructionSteps();
		add(is, BorderLayout.CENTER);
		
		InstructionButtons ib = new InstructionButtons(mainPanel);
		ib.setPreferredSize(new Dimension(1000, 100));
		add(ib, BorderLayout.SOUTH);
		
	}
	class InstructionSteps extends JPanel
	{
		// This the the panel being added to the north. It will have a flowlayout that 
		// adds the instructions in the form of a JLabel with a space of 50 in between them
		public InstructionSteps()
		{
			setLayout(new FlowLayout(FlowLayout.CENTER, 20, 100));
			setBackground(Color.BLUE);
			
			Font font = new Font("Open Sans", Font.BOLD, 30);
			
			JTextArea instructions = new JTextArea("The game will start automatically once you join."
				+ "\n1. Choose your difficulty level while playing, which you can change if you feel it to be " 
				+ "to hard or too easy.\n    This will determine how much energy the bot will have while playing."
				+ "\n2. Move your player using the up, down, left, and right keys in order to hit the ball.\n"
				+ "3. After your first shot, you will receive a question, and every 4 shots afterwards a question will be displayed."
				+ "\n    If you answer a multiple choice question correctly, the rally will continue on.\n    However, if you answer a free-response"
				+ " question, you instantly win the point!\n4. If you get to 5 points, you win!\n Good Luck!!!");
			instructions.setPreferredSize(new Dimension(700, 200));
			instructions.setBackground(Color.GREEN);
			instructions.setFont(new Font("Serif", Font.BOLD, 15));
			add(instructions);
		}
	}
	class InstructionTitle extends JPanel
	{
		public InstructionTitle()
		{
			setLayout(new FlowLayout(FlowLayout.CENTER, 50, 15));
			setBackground(Color.BLUE);
			
			JLabel instructionTitle = new JLabel("Instructions");
			instructionTitle.setForeground(Color.MAGENTA);	
			instructionTitle.setFont(new Font("Serif", Font.BOLD, 30));
			add(instructionTitle);	
		}
	}
	class InstructionButtons extends JPanel
	{
		// This is the panel being added to the south. It will have a borderlayout, with
		// a button to go back the the startpanel, another button to go directly to the game
		// and another button to instantly quit the program. All of the buttons use a 
		// single handler class for simplicity and efficiency. 
	
		public InstructionButtons(MainPanel sp)
		{
			setLayout(new FlowLayout(FlowLayout.CENTER, 200, 30));
			setBackground(Color.BLUE);
			
			ButtonHandler bh = new ButtonHandler(sp);

			JButton backButton = new JButton("Back");
			backButton.setSize(new Dimension(100, 50));
			backButton.addActionListener(bh);
			add(backButton);
			
			JButton gameButton = new JButton("Play Game");
			gameButton.setSize(100, 50);
			gameButton.addActionListener(bh);
			add(gameButton);
			
			JButton quitButton = new JButton("Quit");
			quitButton.setSize(100, 50);
			quitButton.addActionListener(bh);
			add(quitButton);
		}
		
		class ButtonHandler implements ActionListener
		{
			private MainPanel mainPanel; // This is used to get an instance of MainPanel
			// The constructor is used to initialize MainPanel
			public ButtonHandler(MainPanel mp)
			{
				mainPanel = mp;
			}
			// This is the handler class to take in the users actions and determine where to go
			// from their action. It uses a instance of the StartPanel class in order to call variables
			// that are needed in that class to switch between the card layouts. 
			public void actionPerformed(ActionEvent evt)
			{
				String command = evt.getActionCommand();
				
				mainPanel.getCards().removeLayoutComponent(mainPanel.getGamePanel());
				mainPanel.getCardPanels().add(mainPanel.getGamePanel(), "Game");
				
				if(command.equals("Back"))
				{
					mainPanel.getCards().previous(mainPanel.getCardPanels());
				}
				else if(command.equals("Play Game"))
				{
					mainPanel.getCards().show(mainPanel.getCardPanels(), "Game");
				}
				else if(command.equals("Quit"))
				{
					System.exit(1);
				}
			}
		}
	}
}