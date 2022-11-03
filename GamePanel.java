// Vinay Subramanian
// 5-1-20
// GamePanel.java
// This is the core of our game, and will feature the tennis game, where the user will play a 
// bot in a game of up to 5 points. During the game, in between every 3-4 hits the user
// will have to answer the question in order to continue playing
//It will be in a borderlayout, first with 2 panels(The tennis court and the question panel)
// on the east and west and within those panels, each of them have a borderlayout 
// for their needs.
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel; 
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent; 
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class GamePanel extends JPanel
{
	private String difficultyLevel;	//this determines difficulty level for the user (can be changed)
	private int numMisses;	//a random number that determines if the bot misses or not
	private int hitCounter;	//counts number of hits in the rally (if it reaches four, a question pops up)
	private Color backgroundColor;	//color of the outside of the court
	private Font gameFont;			//This is the font we will be using for all of the text in the game panel
	private MainPanel mpInstance;	//used so that it can be accessed when creating a new question panel
	private Timer timer;	//timer for the ball and the bot
	private boolean answerCorrect;	//checks whether or not the answer is correct
	private int bot, user;	//holds the scores for the bot and the user
	private TennisCourtGame game;	//instance of TennisCourtGame to be used in QuestionPanel
	private boolean restartGame;	//boolean that tells whether or not a new point needs to be started
	private JLabel difficultyStr;	//Jlabel to tell the difficulty
	private TennisCourtGame tcg;	//instance of TennisCourtGame added to the game panel
	// This adds all the panels in a borderLayout to the entire GamePanel, the tennis court
	// panel and the question panel. 
	public GamePanel(MainPanel mainPanel4)
	{
		difficultyLevel = new String("Easy");
		backgroundColor  = new Color(0, 170, 0);
		gameFont = new Font("Serif", Font.BOLD, 20);
		hitCounter = 3;
		answerCorrect = false;
		mpInstance = mainPanel4;
		numMisses = 0;
		
		setLayout(new BorderLayout());
		setBackground(Color.CYAN);
		
		tcg = new TennisCourtGame(mpInstance, this);
		tcg.setPreferredSize(new Dimension(600, 800));
		add(tcg, BorderLayout.WEST);
	}
	
	class TennisCourtGame extends JPanel
	{
		private ScoreHolder sh;		//creates an instance of the panel that holds the score
		private CourtHolder ch;	//creates an instance of the panel that holds the tennis court, 
								//user, bot and tennis ball
		private BackButtonHolder bbh;	//creates an instance of the panel that holds buttons that
										//can be used to go back to the start panel
		private MenuHolder mHolder;	//this holds the menu to determine difficulty level of the bot
		private GamePanel gPan;	//instance of GamePanel to be accessed when adding question panel to game panel
		private HighScoresPanel hsp; //instance of High scores panel that is used to give the high score 

		//border layout that has the score, court, back button, and menu
		public TennisCourtGame(MainPanel mp4, GamePanel gp)
		{
			setLayout(new BorderLayout());
			hsp = mpInstance.getHighScoresPanel();
						
			bot = 0;
			user = 0;
			sh = new ScoreHolder();
			sh.setPreferredSize(new Dimension(550, 60));
			add(sh, BorderLayout.NORTH);
			gPan = gp;
			ch = new CourtHolder(this);
			add(ch, BorderLayout.CENTER);
		
			bbh = new BackButtonHolder(mp4);		
			add(bbh, BorderLayout.SOUTH);
			
			mHolder = new MenuHolder();
			add(mHolder, BorderLayout.WEST);	
		}
		
		//this method is used to refresh the score on the scoreboard
		public void refreshScores() 
		{
			if(user == 5)
			{
				mpInstance.getUserProfile().setTotalScore(mpInstance.getUserProfile().getTotalScore() + 1);
				hsp.getReplaceScores();
				mpInstance.getStartPanel().showWinFrame();
				mpInstance.getCards().show(mpInstance.getCardPanels(), "Start");
				//replace score
			}
			else if(bot == 5)
			{
				mpInstance.getCards().show(mpInstance.getCardPanels(), "Start");
			}
			game.remove(sh);
			sh = new ScoreHolder();
			game.add(sh, BorderLayout.NORTH);
			game.revalidate();	//found on stack overflow (cited in citations)	
		}

		class ScoreHolder extends JPanel
		{
			private JLabel userLabel;	//JLabel that holds the user's score
			private JLabel botLabel;	//JLabel that holds the bot's score
			
			public ScoreHolder()
			{
				setLayout(new FlowLayout(FlowLayout.CENTER, 50, 30));
				setBackground(Color.CYAN);
			
				userLabel = new JLabel("You: " + user);
				userLabel.setLocation(240, 40);
				add(userLabel);
				
				botLabel = new JLabel("BOT: " + bot);
				botLabel.setLocation(340, 40);
				add(botLabel);
				
				setPreferredSize(new Dimension(550, 60));
			}
			
			//creates the score table
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.setColor(Color.BLACK);
				g.drawRect(200, 25, 200, 30);
				g.drawLine(300, 25, 300, 55);	
			}
		}
		
		class MenuHolder extends JPanel
		{
			//has a flow layout which creates and initializes the JMenuBar, JMenu, and JMenuItem
			//when this starts working, the user will be able to determine the difficulty level
			//of the bot while playing the game
			public MenuHolder()
			{
				setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
				setBackground(backgroundColor);
				setPreferredSize(new Dimension(100, 800));
				
				MenuHandler mh = new MenuHandler();
				difficultyStr = new JLabel(difficultyLevel);
				
				JMenuBar bar = new JMenuBar();
				JMenu difficulty = new JMenu("Difficulty level");
				JMenuItem easy = new JMenuItem("Easy");
				easy.addActionListener(mh);
				JMenuItem medium = new JMenuItem("Medium");
				medium.addActionListener(mh);
				JMenuItem hard = new JMenuItem("Hard");
				hard.addActionListener(mh);

				difficulty.add(easy);
				difficulty.add(medium);
				difficulty.add(hard);
				bar.add(difficulty);
				add(bar);
				add(difficultyStr);
			}
			class MenuHandler implements ActionListener
			{
				public void actionPerformed(ActionEvent evt)
				{
					difficultyLevel = evt.getActionCommand();	//will be used when we write code to determine the difficulty
					ch.repaint();
				}
			}
		}
		class CourtHolder extends JPanel implements ActionListener, KeyListener
		{
			//these integers below represent the x and y locations of the ball, the bot, and the user
			//they also represent the increment at which the bot will move when going to the ball, as
			//well as where the ball was hit by the user
			private int ballX, ballY, botX, botY, botIncrement, userX, userY, ballHitX, ballHitY;
			//these doubles are used to hold the increment at which the ball moves in the x direction,
			//the y direction, and the slope at which the ball goes from the user's side to the bot's
			//side
			private double ballIncrementX, ballIncrementY, slope;
			//radius of the ball
			private int radius;
			//x value at which the ball intersects the base line (top of the court)
			private double intersectionPoint;
			//instance of ScoreHolder, botPoints and userPoints are passed in to determine points by
			//the bot and the user
			private ScoreHolder getScores;	//instance of score holder
			private boolean botMiss;	//to tell whether or not the bot misses
			
			//CourtHolder has a border layout. The constructor sets the locations of the ball, bot,
			//and user as well as the increments, intersectionPoint and starting point.
			//There is also a timer that is used to move both the user and the bot at the same time.
			//There is also a KeyListener that is used for the user to move their player
			public CourtHolder(TennisCourtGame tcg)
			{
				ballX = 350;
				ballY = 660;
				botX = 147;
				botY = 20;
				ballIncrementX = -1;
				botIncrement = 0;
				ballIncrementY = -2;
				slope = ballIncrementY/ballIncrementX;
				radius = 5;
				userX = 280;
				userY = 650;
				restartGame = false;
				intersectionPoint = ballX - (50 - ballY)/(-1*slope);
				botMiss = false;
				game = tcg;
				
				getScores = new ScoreHolder();
				
				timer = new Timer(15, this);
				timer.start();

				setLayout(new BorderLayout());
				setBackground(backgroundColor);
				addKeyListener(this);
			}
			
			//calls the method that creates the tennis court
			public void paintComponent(Graphics g)
			{				
				super.paintComponent(g);
				makeTennisCourt(g);
				this.requestFocusInWindow();
			}
			//This method creates the tennis court, the moving tennis ball,
			//the moving bot, as well as the user.
			public void makeTennisCourt(Graphics g)
			{
				//creating tennis court
				g.setColor(Color.BLUE);
				g.fillRect(50, 50, 350, 600);
				g.setColor(Color.WHITE);
				g.drawRect(50, 50, 350, 600);
				g.drawRect(100, 50, 250, 600);
				g.drawRect(100, 200, 250, 300);
				g.drawLine(225, 200, 225, 500);
				g.drawLine(225, 50, 225, 60);
				g.drawLine(225, 650, 225, 640);
				g.fillRect(45, 345, 360, 10);
				
				//creating user's player
				g.setColor(mpInstance.getSettingsPanel().getColor());
				g.fillOval(userX, userY, 30, 30);
								
				//Resetting all of the values for the player, bot, and ball, so that a new point starts
				//after the point is won by either the user or the bot
				if(restartGame == true)
				{
					timer.start();
					ballX = 350;
					ballY = 660;
					ballIncrementX = -1;
					ballIncrementY = -2;
					botIncrement = 0;
					hitCounter = 3;
					userX = 280;
					userY = 650;
					botX = 147;
				    botY = 20;
					restartGame = false;
					botMiss = false;
					intersectionPoint = 50;
				}
				
				//Determining the increment at which the bot moves based on whether they are to the left,
				//or to the right of the bot
				else
				{
					if(botMiss)
					{
						botIncrement = 0;
					}
					else
					{
						if(intersectionPoint < botX)
						{
							botIncrement = -3;
						}
						else
						{
							botIncrement = 3;
						}
					}
					
					//determines if the bot is close enough to the intersection point that it can stop moving
					if(Math.abs(intersectionPoint - botX) < 2)
					{
						botIncrement = 0;
						//determines when the bot is close enough to the ball
						if(Math.abs(ballY - botY) < 20*Math.sqrt(2))
						{
								//determines a random slope at which the ball will move once hit by the bot
								ballIncrementX = (Math.random()*1) + 1;
								ballIncrementY = (Math.random()*3) + 4;	
								
								//if the ball is on the right side of the court, the direction changes
								if(ballX >= 225)
								{
									ballIncrementX *= -1;
								}
								slope = ballIncrementY/ballIncrementX;
						}					
					}
					//determines of the ball is close enough to be hit by the user
					if(Math.abs(ballX - userX) < 20*Math.sqrt(2))
					{
						if(Math.abs(ballY - userY) < 20*Math.sqrt(2))
						{
							//determines a random slope at which the ball is hit by the user
							ballIncrementX = -((Math.random()*1) + 1);
							ballIncrementY = -((Math.random()*3) + 4);
							numMisses = (int)((Math.random()*10)+1);
							
							if(difficultyLevel.equals("Easy") && numMisses > 7)
							{
								botMiss = true;
							}
							else if(difficultyLevel.equals("Medium") && numMisses > 8)
							{
								botMiss = true;
							}
							else if(difficultyLevel.equals("Hard") && numMisses > 9)
							{
								botMiss = true;
							}
							
							difficultyStr.setText(difficultyLevel);
							mHolder.repaint();
							mHolder.revalidate();
							//if the ball is hit on the left side of the court, the direction changes
							if(ballX < 225)
							{
								ballIncrementX *= -1;
							}
							
							slope = ballIncrementY/ballIncrementX;
							ballHitX = ballX;
							ballHitY = ballY;
							hitCounter++;
						}
					}
					botX+=botIncrement;
					ballX += ballIncrementX;
				}
				
				//to tell if the user or bot wins the point, ball gets stopped if it hits one of the sides of the panel
				if(ballX == 0 || ballX == getWidth())
				{
					timer.stop();
					ballIncrementX = 0;
					ballIncrementY = 0;
					botIncrement = 0;
					if(ballY < 345)
					{
						user++;
					}
					else
						bot++;
					restartGame = true;
					this.repaint();
				}
				
				//the user wins if the ball goes past the bot
				if(ballY == 0)
				{
					timer.stop();
					ballIncrementX = 0;
					ballIncrementY = 0;
					botIncrement = 0;
					user++;
					restartGame = true;
					this.repaint();
				}
				
				//the bot wins if the ball goes past the user
				if(ballY >= 669)
				{
					timer.stop();
					ballIncrementX = 0;
					ballIncrementY = 0;
					botIncrement = 0;
					bot++;
					restartGame = true;
					this.repaint();
				}
				else
					ballY += ballIncrementY;		
				game.refreshScores();	//refreshScores method used to refresh the score if bot or user misses
				
				intersectionPoint = ballX;
				//paint the ball and the bot
				g.setColor(Color.RED);
				g.fillOval(botX, botY, 30, 30);
				g.setColor(Color.GREEN);
				g.fillOval(ballX - radius, ballY - radius, radius * 2, radius * 2);
				
				//determines the amount of times the ball has been hit, based on this, the game will throw a question
				if(hitCounter == 4)
				{
					hitCounter = 0;
					timer.stop();
					QuestionPanel qp = new QuestionPanel(mpInstance, gPan);
					qp.setPreferredSize(new Dimension(450, 800));	
					gPan.add(qp, BorderLayout.EAST);
					gPan.revalidate();
				}
			}
			
			//used to repaint when the timer is still going
			public void actionPerformed(ActionEvent evt)
			{
				this.repaint();
			}
			
			//this method is used to determine the direction the user wants their player to move
			public void keyPressed(KeyEvent evt) 
			{
				int command = evt.getKeyCode();
				
				if(command == KeyEvent.VK_UP)
				{
					userY -= 20;
				}
				if(command == KeyEvent.VK_DOWN)
					userY += 20;
				if(command == KeyEvent.VK_RIGHT)
					userX += 20;
				if(command == KeyEvent.VK_LEFT)
					userX -= 20;
				repaint();
			}
			public void keyReleased(KeyEvent evt) {}
			public void keyTyped(KeyEvent evt) {}
		}
		
		
		class BackButtonHolder extends JPanel
		{
			private MainPanel getVar2;	//an instance of main panel to access MainPanel's card layout
			public BackButtonHolder(MainPanel mainPanel5)
			{
				getVar2 = mainPanel5;
				setLayout(new FlowLayout(FlowLayout.LEFT, 30, 5));
				setBackground(Color.CYAN);
				BackButtonHandler bbHandler = new BackButtonHandler();
			
				JButton backButton = new JButton("Back to Home");
				backButton.addActionListener(bbHandler);
				add(backButton);
			}
			class BackButtonHandler implements ActionListener
			{
				//If the home button is pressed, the card layout shows the start panel
				public void actionPerformed(ActionEvent evt)
				{
					String command = evt.getActionCommand();
					if(command.equals("Back to Home"))
					{
						getVar2.getCards().show(getVar2.getCardPanels(), "Start");
					}
				}
			}
		}
		
	}
	//This panel holds all of the questions
		class QuestionPanel extends JPanel
		{
			private Scanner questionReader;	//reading questions from a file
			private Scanner answerReader;		//reading answers from a file
			private int questionSelector;		//selecting the type of question (MC, Checkbox, Text field)
			private String questionType;		//holds the type of question
			private String question;			//holds the question that is selected from the file
			private int questionNumber;		//used to determine which question is chosen
			private int randomQ;				//used to tell which question should be thrown
			private String answer;			//holds the answer to each question
			private String line;			//holds the line that is being read in the file
			private GamePanel gp;				//instance of game panel for removing question panel once answered
			private DisplayQuestions dq;		//instance of display questions to be added and removed
			
			public QuestionPanel(MainPanel mp5, GamePanel game)
			{
				questionSelector = 0;
				questionType = new String("MultipleChoice");
				answerReader = null;
				questionReader = null;
				question = new String("");
				questionNumber = 0;
				gp = game;
				
				setLayout(new BorderLayout());
				this.requestFocusInWindow();
				
				dq = new DisplayQuestions(this);
				add(dq, BorderLayout.CENTER);
				dq.setVisible(true);
				DisplayButtons db = new DisplayButtons(mp5);
				add(db, BorderLayout.SOUTH);
				db.setVisible(true);
			}
			
			//opens the files for reading (questions and answers)
			public void openFiles()
			{
				String fileReadName1 = new String(questionType+"Q.txt");
				String fileReadName2 = new String(questionType+"A.txt");
				File questions = new File(fileReadName1);
				File answers = new File(fileReadName2);
				
				try
				{
					questionReader = new Scanner(questions);
				}
				catch(FileNotFoundException e)
				{
					System.err.println(fileReadName1 + " could not be opened.");
					System.exit(0);
				}
				
				try
				{
					answerReader = new Scanner(answers);
				}
				catch(FileNotFoundException e)
				{
					System.err.println(fileReadName2 + " could not be opened.");
					System.exit(1);
				}
			}
			
			class DisplayQuestions extends JPanel
			{
				private JTextArea questionLabel;	//JLabel that displays the question on the question panel
				private QuestionPanel qp;	//instance of question panel that 
				private DisplayQuestions dq2;	//this instance of dq2
				//calls method that chooses the question type, sets the question, and displays the question
				public DisplayQuestions(QuestionPanel p)
				{
					qp = p;
					dq2 = this;
					questionLabel = new JTextArea("");
					setLayout(new FlowLayout(FlowLayout.CENTER, 300, 40));
					setBackground(Color.CYAN);
				
					chooseQuestionType();
					
					questionLabel.setPreferredSize(new Dimension(250, 60));
					questionLabel.setLineWrap(true);
					questionLabel.setEditable(false);
					questionLabel.setBackground(Color.CYAN);
					questionLabel.setText(question.substring(question.indexOf(' ')));
					add(questionLabel);
					displayAnswers();	
				}

				public void chooseQuestionType()
				{
					//field variable that selects type of question and includes a rarity
					questionSelector = (int)(Math.random()*10 + 1);
					
					if(questionSelector >= 1 && questionSelector <= 8)	//50% chance of a multiple choice question
					{
						questionType = "MultipleChoice";
						questionNumber = 70;
					}
					
					else if(questionSelector > 8)	//20% chance of a text field question
					{
						questionType = "TextField";
						questionNumber = 40;
					}
					
					openFiles();
					readQuestions();
				}
				public void readQuestions()
				{
					int counter = 1;	//tells what line is being read
					boolean questionFound = questionReader.hasNext();
					randomQ = (int)(Math.random()*questionNumber + 1);
					
					String line = new String("");
					while(questionFound)
					{
						line = questionReader.nextLine();
						if(counter == randomQ)
						{
							question = line;
							questionFound = false;
						}
						counter++;
					}
				}
				
				public void displayAnswers()
				{
					answer = new String("");
					line = new String("");
					if(questionType.equals("MultipleChoice"))
					{
						DisplayRadioButtons drb = new DisplayRadioButtons();
						dq2.add(drb);
					}
					
					else if(questionType.equals("TextField"))
					{
						JTextField tf = new JTextField("");
						int counter = 1;	//line number
						while(answerReader.hasNext())
						{
							line = answerReader.nextLine();
							if(counter == randomQ)
							{
								answer = line;
							}
							counter++;
						}
						
						TextFieldHandler tfh = new TextFieldHandler();
						
						tf.setPreferredSize(new Dimension(200, 25));
						tf.addActionListener(tfh);
						this.add(tf);
					}
				}
				
				class DisplayRadioButtons extends JPanel
				{
					public DisplayRadioButtons()
					{
						setLayout(new GridLayout(4, 1));
						JPanel c1Pan = new JPanel();
						JPanel c2Pan = new JPanel();
						JPanel c3Pan = new JPanel();
						JPanel c4Pan = new JPanel();
						
						c1Pan.setLayout(new FlowLayout(FlowLayout.LEFT, 100, 30));
						c2Pan.setLayout(new FlowLayout(FlowLayout.LEFT, 100, 30));
						c3Pan.setLayout(new FlowLayout(FlowLayout.LEFT, 100, 30));
						c4Pan.setLayout(new FlowLayout(FlowLayout.LEFT, 100, 30));
						
						c1Pan.setBackground(Color.CYAN);
						c2Pan.setBackground(Color.CYAN);
						c3Pan.setBackground(Color.CYAN);
						c4Pan.setBackground(Color.CYAN);
						
						String[] allWrongAnswers = new String[69];	//holds all of the wrong answers to the MC question
						String[] wrongAnswers = new String[3];	//three randomly chosen wrong answers
						int wrongA1;	//random number to determine one wrong answer used
						int wrongA2;	//random number to determine one wrong answer used
						int wrongA3;	//random number to determine one wrong answer used
						
						int rightAns = (int)(Math.random()*4 + 1);	//determines whether the right answer is on the first,
																	//second, third or fourth answer choice
						ButtonGroup bg = new ButtonGroup();
						JRadioButton choice1 = new JRadioButton();
						JRadioButton choice2 = new JRadioButton();
						JRadioButton choice3 = new JRadioButton();
						JRadioButton choice4 = new JRadioButton();
						
						int counter = 0;	//for allWrongAnswers array
						int counter2 = 1;	//for line number
						while(answerReader.hasNext())
						{
							line = answerReader.nextLine();
							line = line.substring(line.indexOf(' '));
							if(line.length() > 40)
							{
								line = "<html>" + line.substring(0, 41) + "<br>"+ line.substring(41) + "</html>";
							}
							
							if(counter2 == randomQ)
							{
								answer = line;
							}
							else
							{
								if(counter < 69)
								{
									allWrongAnswers[counter] = line;
									counter++;
								}
							}
							counter2++;
						}
						
						wrongA1 =(int)(Math.random()*23);
						wrongA2 = (int)(Math.random()*23 + 23);
						wrongA3 = (int)(Math.random()*23 + 46);
						
						wrongAnswers[0] = allWrongAnswers[wrongA1];
						wrongAnswers[1] = allWrongAnswers[wrongA2];
						wrongAnswers[2] = allWrongAnswers[wrongA3];
						
						//setting values of all of the radio buttons randomly
						
						if(rightAns == 1)
						{
							choice1.setText(answer);
							choice2.setText(wrongAnswers[0]);
							choice3.setText(wrongAnswers[1]);
							choice4.setText(wrongAnswers[2]);
						}
						else if(rightAns == 2)
						{
							choice1.setText(wrongAnswers[0]);
							choice2.setText(answer);
							choice3.setText(wrongAnswers[1]);
							choice4.setText(wrongAnswers[2]);
						}
						else if(rightAns == 3)
						{
							choice1.setText(wrongAnswers[1]);
							choice2.setText(wrongAnswers[0]);
							choice3.setText(answer);
							choice4.setText(wrongAnswers[2]);
						}
						else if(rightAns == 4)
						{
							choice1.setText(wrongAnswers[2]);
							choice2.setText(wrongAnswers[0]);
							choice3.setText(wrongAnswers[1]);
							choice4.setText(answer);
						}
						
						bg.add(choice1);
						bg.add(choice2);
						bg.add(choice3);
						bg.add(choice4);
						
						c1Pan.add(choice1);
						c2Pan.add(choice2);
						c3Pan.add(choice3);
						c4Pan.add(choice4);
						
						RadioButtonHandler rbh = new RadioButtonHandler();
						
						choice1.addActionListener(rbh);
						choice2.addActionListener(rbh);
						choice3.addActionListener(rbh);
						choice4.addActionListener(rbh);
						
						add(c1Pan);
						add(c2Pan);
						add(c3Pan);
						add(c4Pan);
					}
				}
				
				public void createExplanationFrame()
				{
					Font font = new Font("Serif", Font.BOLD, 20);
					JFrame answerFrame = new JFrame();
					JPanel answerPanel = new JPanel();
					
					JLabel answerLabel = new JLabel(answer);
					answerLabel.setFont(font);
					
					answerLabel.setForeground(Color.RED);
					answerPanel.add(answerLabel);
					answerPanel.setBackground(Color.GREEN);
					
					answerFrame = new JFrame("Wrong answer entered. This is the correct one.");
					answerFrame.setDefaultCloseOperation(answerFrame.DISPOSE_ON_CLOSE);
					answerFrame.setSize(500, 500);
					answerFrame.setLocation(700, 220);
					answerFrame.getContentPane().add(answerPanel);
					answerFrame.setVisible(true);
				}
				
				
				class RadioButtonHandler implements ActionListener
				{
					String command;	//for getting the action performed by the user
					public void actionPerformed(ActionEvent evt)
					{
						command = evt.getActionCommand();
						//determines whether answer is righ or not
						//if answer is right rally will continue
						if(command.equals(answer))
						{
							gp.remove(qp);
							timer.start();
							gp.revalidate();
							gp.repaint();
							answerCorrect = true;
							gp.setFocusable(true);
							
						}
						//if the answer is wrong user loses the point
						else
						{
							createExplanationFrame();
							gp.remove(qp);
							gp.revalidate();
							restartGame = true;
							gp.repaint();
							bot++;
							game.refreshScores();
						}
					}
				}
				
				class TextFieldHandler implements ActionListener
				{
					String command;	//for getting the action performed by the user
					public void actionPerformed(ActionEvent evt)
					{
						answer = answer.substring(2).trim();
						command = evt.getActionCommand();
						
						//deciding if answer is correct or not
						//user wins the point if the answer is correct
						if(command.equalsIgnoreCase(answer))
						{
							gp.remove(qp);
							timer.start();
							gp.revalidate();
							gp.repaint();
							answerCorrect = true;
							user++;
							restartGame = true;
						}
						//user loses the point if the answer is wrong
						else
						{
							createExplanationFrame();
							gp.remove(qp);
							gp.revalidate();
							restartGame = true;
							gp.repaint();
							bot++;
							gp.setFocusable(true);
						}
						game.refreshScores();	//updates scores
					}
				}	
			}
			
			class DisplayButtons extends JPanel
			{
				private MainPanel mp6;	//instance of main panel to access its card layout
				public DisplayButtons(MainPanel mainPanel6)
				{
					setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
					setBackground(Color.RED);
					
					QuitButtonHandler qbh = new QuitButtonHandler();
					mp6 = mainPanel6;
					
					JButton quit = new JButton("Quit");
					quit.addActionListener(qbh);
					add(quit);
				}
				class QuitButtonHandler implements ActionListener
				{
					//if quit is pressed, the entire game quits
					public void actionPerformed(ActionEvent evt)
					{
						String input = evt.getActionCommand();
						if(input.equals("Quit"))
						{
							System.exit(1);
						}
					}
				}
			}
		}
	}		