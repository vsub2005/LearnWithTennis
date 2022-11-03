//Vinay Subramanian and Sameer Maheshwari
//5-15-20
//Profile.java
//This program takes in the username and password of the user. This is used later
//if applicable for high scores. If the username already exists, the user cannot 
//use the username, and the username cannot be username or password

import java.awt.Graphics;
import javax.swing.JPasswordField;
import java.awt.LayoutManager;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileWriter;

public class Profile extends JPanel
{
	private MainPanel mp7;	//instance of main panel
	private Font font;		//font of the error message
	private JTextField username;	//text field where user enters username
	private JPasswordField password;	//text field where user enters password
	private boolean errorMessage;	//tells whether or not the username already exists
	private String errorText;		//displays a certain error
	private Profile profile;		//instance of profile
	private EastPanel ep;			//east panel which has the the error message
	private JTextArea error;		//j text area which is used to display the error message
	private int totalScore;	//score which is used for high scores (number of wins)
	private String usernameStr;	//String to hold the username
	private String passwordStr;	//String to hold the password
	
	public Profile(MainPanel instance)
	{
		totalScore = 0;
		errorMessage = false;
		errorText = new String("");
		mp7 = instance;
		font = new Font("Serif", Font.BOLD, 30);
		profile = this;
		
		ep = new EastPanel();
		NorthPanel np = new NorthPanel();
		CenterPanel cp = new CenterPanel();
		SouthPanel sp = new SouthPanel();
		WestPanel wp = new WestPanel();
				
		setBackground(Color.GREEN);
		setLayout(new BorderLayout(0, 0));
		
		add(np, BorderLayout.NORTH);
		add(cp, BorderLayout.CENTER);
		add(sp, BorderLayout.SOUTH);	
		add(ep, BorderLayout.EAST);	
		add(wp, BorderLayout.WEST);
	}
	public String getUsernameStr() 
	{
		return usernameStr;
	}
	public void setUsernameStr(String usernameStr) 
	{
		this.usernameStr = usernameStr;
	}
	public String getPasswordStr() 
	{
		return passwordStr;
	}
	public void setPasswordStr(String passwordStr) 
	{
		this.passwordStr = passwordStr;
	}
	
	public int getTotalScore() 
	{
		return totalScore;
	}

	public void setTotalScore(int totalScore) 
	{
		this.totalScore = totalScore;
	}
	
	class EastPanel extends JPanel
	{
		public EastPanel()
		{
			setLayout(new FlowLayout(FlowLayout.CENTER, 10, 200));	//for displaying error message
			setBackground(Color.GREEN);
			error = new JTextArea(errorText, 10, 8 );
			error.setFont(new Font("Serif", Font.BOLD, 20));
			error.setPreferredSize(new Dimension(300, 50));
			error.setForeground(Color.RED);
			error.setBackground(Color.GREEN);
			add(error);
		}
	}
	class WestPanel extends JPanel
	{
		public WestPanel()
		{
			setLayout(new FlowLayout(FlowLayout.CENTER, 10, 200));	//instructions on how to log in
			setBackground(Color.GREEN);
		
			String text = new String("Type any username and password"
				+ "\nto create your first account\nIf you forgot your password\ncreate a new account"
				+ " or good luck!!");
			JTextArea welcome = new JTextArea(text, 10, 5 );
			welcome.setFont(new Font("Serif", Font.BOLD, 20));
			welcome.setPreferredSize(new Dimension(300, 100));
			welcome.setForeground(Color.BLACK);
			welcome.setBackground(Color.GREEN);
		
			add(welcome);
		}
	}
	
	class NorthPanel extends JPanel
	{
		public NorthPanel()
		{
			setLayout(new FlowLayout(FlowLayout.CENTER, 0,20));
			setBackground(Color.GREEN);
			JLabel signIn = new JLabel("Ace Learning Sign In");
			signIn.setFont(font);
			
			add(signIn);
		}
		
	}
	class CenterPanel extends JPanel
	{
		public CenterPanel()
		{
			setLayout(new FlowLayout(FlowLayout.CENTER, 700, 150));	//text fields used to sign in
			setBackground(Color.GREEN);
			
			username = new JTextField("Username");
			password = new JPasswordField("");
			
			username.setPreferredSize(new Dimension(200, 25));
			password.setPreferredSize(new Dimension(200, 25));
			
			add(username);
			add(password);
		}
	}
	class SouthPanel extends JPanel
	{
		public SouthPanel()
		{
			setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));	//button to enter
			setBackground(Color.GREEN);
			JButton enter = new JButton("Enter");
			EnterButtonHandler ebh = new EnterButtonHandler();
			enter.addActionListener(ebh); 
			
			add(enter);
		}
	}
	
	//Sameer did this part
	class EnterButtonHandler implements ActionListener
	{
		private String command;	//the action performed by the user
		private PrintWriter output;	//the PrintWriter that writes to the UserInfo.txt file
		private FileWriter append;	//used to append to the file
		private Scanner input;	//to read UserInfo to see if the username exists
		private String line;	//the current line being read
		private boolean usernameSame;	//boolean to check if the username is the same
		private boolean passwordSame;	//boolean to check if the password is the same
		private String passInString;	//error message thrown
		
		public EnterButtonHandler()
		{
			passInString = new String("");
			usernameStr = new String("");
		}

		public void getFile()
		{
			String inOutFileName = new String("UserInfo.txt");
			File inputOutputFile = new File(inOutFileName);
			
			output = null;
			input = null;
			append = null;
			
			try
			{
				append = new FileWriter(inputOutputFile, true);
				output = new PrintWriter(append);
			}
			catch(IOException e)
			{
				System.err.println("Cannot write to " + inOutFileName);
				System.exit(2);
			}
			try
			{
				input = new Scanner(inputOutputFile);
			}
			catch(FileNotFoundException e)
			{
				System.err.println("Cannot find " + inOutFileName);
				System.exit(3);
			}
		}
		
		public void actionPerformed(ActionEvent evt)
		{
			command = evt.getActionCommand();
			usernameSame = false;
			passwordSame = false;
			
			usernameStr = username.getText();
	
			passwordStr = password.getText();
			if(usernameStr.equalsIgnoreCase("Username") || passwordStr.equalsIgnoreCase("Password"))
			{
				errorMessage = true;
				passInString = "Must have a different\nusername or password";
			}
			else if(command.equalsIgnoreCase("Enter"))
			{
				usernameStr = username.getText();
				getFile();
				
				mp7.getHighScoresPanel().setGetUsername("Username: " + usernameStr);
				
				while(input.hasNext())
				{
					line = input.nextLine();
					String[] userInfo = line.split(",");
					if(userInfo[0].equals(usernameStr))
					{
						usernameSame = true;
						if(userInfo[1].equals(passwordStr))
						{
							passwordSame = true;
							mp7.getCards().next(mp7.getCardPanels());
							totalScore = Integer.parseInt(userInfo[2]);
						}
						else
						{
							errorMessage = true;
							passInString = "Wrong password.\nCreate new account\nor retry";
						}						
					}
				}
				
				if(!usernameSame && !passwordSame)
				{
					output.append(usernameStr + "," + passwordStr + "," + totalScore + "\n");
					mp7.getCards().next(mp7.getCardPanels());
				}
				output.close();
				input.close();
			}
			errorText = passInString;
			refreshMessage();
		}
		
		public void refreshMessage() 
		{
			profile.remove(ep);
			if(errorMessage == true)
				error.setText(errorText);
			profile.add(ep, BorderLayout.EAST);
			ep.revalidate();
			ep.repaint();
			error.revalidate();	//found on stack overflow (cited in citations)
			error.repaint();		
		}
	}
}