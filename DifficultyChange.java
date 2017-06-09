package game;


import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
@SuppressWarnings("unused")
public class DifficultyChange extends JPanel {
	public static final int SCALE = 3;
	public static final String NAME = "Zombies101";
	private BufferedImage img;
	protected BackgroundPanel container;
	public boolean playGame = false;
	public boolean clicked;
	public String difficulty;
	public DifficultyChange(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public String getDifficulty() {
		return difficulty;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this);
	}
	
	public void createDfficulty()
	{
		container = new BackgroundPanel();
		container.setAlignmentX(CENTER_ALIGNMENT);
		container.setAlignmentY(CENTER_ALIGNMENT);
		container.setPreferredSize(new Dimension(640, 480));
		playGame = false;
		/**JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		
		container.add(panel1);
		container.add(panel2);**/
		
		
		try {
			img = ImageIO.read(new File("images/difficulty.png"));
		}catch (IOException e) {
			e.printStackTrace();
		}
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(container);
		 JRadioButton easy = new JRadioButton("Easy");
		 easy.setAlignmentX(CENTER_ALIGNMENT);
		 easy.setAlignmentY(CENTER_ALIGNMENT);
		 easy.setPreferredSize(new Dimension(100, 150));

		 easy.addActionListener(e -> {
			 difficulty = "easy";
			 clicked = true;
		 });
		 
		 /*easy.addActionListener(new ActionListener() {
			 @Override
		      public void actionPerformed(ActionEvent e)
		      {
		        // display/center the jdialog when the button is pressed
		        difficulty = "easy";
		        clicked = true;
		      }
		    });*/
		 JRadioButton medium = new JRadioButton("medium");
		 medium.setAlignmentX(CENTER_ALIGNMENT);
		 medium.setAlignmentY(CENTER_ALIGNMENT);
		 medium.setPreferredSize(new Dimension(100, 150));

		 medium.addActionListener(e -> {
			 difficulty = "medium";
			 clicked = true;
		 });
		 
		 /*medium.addActionListener(new ActionListener() {
			 @Override
		      public void actionPerformed(ActionEvent e)
		      {
		        // display/center the jdialog when the button is pressed
		        difficulty = "medium";
		        clicked = true;
		      }
		    });*/
		 JRadioButton hard = new JRadioButton("Hard");
		 hard.setAlignmentX(CENTER_ALIGNMENT);
		 hard.setAlignmentY(CENTER_ALIGNMENT);
		 hard.setPreferredSize(new Dimension(100, 150));

		 hard.addActionListener(e -> {
			 difficulty = "hard";
			 clicked = true;
		 });
		 
		 /*hard.addActionListener(new ActionListener() {
		     @Override 
			 public void actionPerformed(ActionEvent e)
		      {
		        // display/center the jdialog when the button is pressed
		        difficulty = "hard";
		        clicked = true;
		      }
		    });*/
		 ButtonGroup group = new ButtonGroup();
		    group.add(easy);
		    group.add(medium);
		    group.add(hard);
		 add(easy);
		 add(medium);
		 add(hard);
		 add(Box.createRigidArea(new Dimension(0, 40)));
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.setVisible(true);
		revalidate();
		repaint();
		
	}
	
	public boolean getClicked() {
		return clicked;
	}
	
	//Regular button
	
	//Button with an icon
	public void addButton(String name, JPanel p){
		 JButton button = new JButton(name);
		 p.add(button);
	}
	
	public class BackgroundPanel extends JPanel
	{
		@Override
	    public void paintComponent(Graphics g)
	    {              
		super.paintComponent(g);
		  Image image=new ImageIcon("").getImage();  
	      g.drawImage(image,0,0,null);
	    }  
	}
	
}
