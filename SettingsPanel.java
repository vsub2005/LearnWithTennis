//Vinay Subramanian
//4-22-20	Per. 2
//SettingsPanel.java
//This program is called in AceFrame and the user can use this panel to change
//the color of their person. There will be three sliders, one for the amount of
//red, blue, and green. The color changes based on the value of all of the sliders.

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import java.awt.Color;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Dimension;

public class SettingsPanel extends JPanel
{
	private Color color;	//the color of the user
	private int red, green, blue;	//amounts of red, green, and blue
	private JSlider redSlider;	//the slider for amount of red
	private JSlider greenSlider;	//the slider for amount of green
	private JSlider blueSlider;	//the slider for amount of blue
	private ColorChanged cc;	//JPanel that shows the color chosen
	private EditColors ec;		//JPanel that holds ColorChanged and SliderPanel
	private SliderPanel sp;		//holds all of the sliders
	
	public SettingsPanel(MainPanel mainPanel)	//parameter for the button handler to access the card layout						
	{
		JButton backButton = new JButton("Back");
		BackButtonHandler bbh = new BackButtonHandler(mainPanel);

		setLayout(new BorderLayout(0, 0));
		setBackground(Color.ORANGE);
		//added button to south and added action listener
		//add(settingsLabel, BorderLayout.NORTH);
		add(backButton, BorderLayout.SOUTH);
		backButton.addActionListener(bbh);
		
		ec = new EditColors();
		add(ec, BorderLayout.CENTER);
	}
	
	public Color getColor() 
	{
		return color;
	}

	public void setColor(Color color) 
	{
		this.color = color;
	}

	class BackButtonHandler implements ActionListener
	{
		MainPanel mainPanel;	//instance of main panel to access the card layout
		public BackButtonHandler(MainPanel mp)
		{
			mainPanel = mp;
		}
		public void actionPerformed(ActionEvent evt)
		{
			String command = evt.getActionCommand();
			//if back is pressed
			if(command.equalsIgnoreCase("Back"))
			{
				//goes back to start panel
				mainPanel.getCards().show(mainPanel.getCardPanels(), "Start");
			}
		}
	}
	
	class EditColors extends JPanel
	{
		public EditColors()
		{
			setLayout(new GridLayout(2, 1));
			setBackground(Color.ORANGE);
			cc = new ColorChanged();
			sp = new SliderPanel();
			add(cc);
			add(sp);
		}
	}
	class ColorChanged extends JPanel
	{
		public ColorChanged()
		{
			setBackground(Color.ORANGE);
			red = green = 255;
			blue = 255;
		}
		public void paintComponent(Graphics g)
		{
			setColor(new Color(red, green, blue));
			super.paintComponent(g);
			g.setColor(getColor());
			g.fillOval(400, 100, 200, 200);
		}
	}
	
	class SliderPanel extends JPanel
	{
		public SliderPanel()
		{
			setPreferredSize(new Dimension(1000, 300));
			setBackground(Color.ORANGE);
			setLayout(new GridLayout(1, 3));
			SliderHandler sh = new SliderHandler();
			
			redSlider = new JSlider(0, 255, 255);
			redSlider.setMajorTickSpacing(20);	// create tick marks on slider every 20 units
			redSlider.setPaintTicks(true);
			redSlider.setLabelTable(redSlider.createStandardLabels(40)); // create labels on tick marks
			redSlider.setPaintLabels(true);
			redSlider.setOrientation(JSlider.HORIZONTAL);
			redSlider.addChangeListener(sh);
			
			greenSlider = new JSlider(0, 255, 255);
			greenSlider.setMajorTickSpacing(20);	// create tick marks on slider every 20 units
			greenSlider.setPaintTicks(true);
			greenSlider.setLabelTable(greenSlider.createStandardLabels(40)); // create labels on tick marks
			greenSlider.setPaintLabels(true);
			greenSlider.setOrientation(JSlider.HORIZONTAL);
			greenSlider.addChangeListener(sh);
			
			blueSlider = new JSlider(0, 255, 0);
			blueSlider.setMajorTickSpacing(20);	// create tick marks on slider every 20 units
			blueSlider.setPaintTicks(true);
			blueSlider.setLabelTable(blueSlider.createStandardLabels(40)); // create labels on tick marks
			blueSlider.setPaintLabels(true);
			blueSlider.setOrientation(JSlider.HORIZONTAL);
			blueSlider.addChangeListener(sh);
			
			add(redSlider);
			add(greenSlider);
			add(blueSlider);
		}
	}
	
	class SliderHandler implements ChangeListener
	{
		public void stateChanged(ChangeEvent evt)
		{
			red = redSlider.getValue();
			green = greenSlider.getValue();
			blue = blueSlider.getValue();
			cc.repaint();
		}
	}
}