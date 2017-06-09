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
	private transient BufferedImage img;
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
	
	public void createDfficulty() throws IOException
	{
		container = new BackgroundPanel();
		container.setAlignmentX(CENTER_ALIGNMENT);
		container.setAlignmentY(CENTER_ALIGNMENT);
		container.setPreferredSize(new Dimension(640, 480));
		playGame = false;
		
		try {
			img = ImageIO.read(new File("images/difficulty.png"));
		}catch (IOException e) {
			e.printStackTrace();
			throw new IOException(e);
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
		 
		 JRadioButton medium = new JRadioButton("medium");
		 medium.setAlignmentX(CENTER_ALIGNMENT);
		 medium.setAlignmentY(CENTER_ALIGNMENT);
		 medium.setPreferredSize(new Dimension(100, 150));

		 medium.addActionListener(e -> {
			 difficulty = "medium";
			 clicked = true;
		 });
		 

		 JRadioButton hard = new JRadioButton("Hard");
		 hard.setAlignmentX(CENTER_ALIGNMENT);
		 hard.setAlignmentY(CENTER_ALIGNMENT);
		 hard.setPreferredSize(new Dimension(100, 150));

		 hard.addActionListener(e -> {
			 difficulty = "hard";
			 clicked = true;
		 });
		 
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
