package game;


import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
@SuppressWarnings("unused")
public class Menu extends JPanel {
	private int widthUsed;
	private int heightUsed;
	public static final int SCALE = 3;
	public static final String NAME = "Zombies101";
	private transient BufferedImage img;
	protected BackgroundPanel container;
	private boolean playGame;
	private boolean exitGame;
	private boolean difficultychange = false;
	public Menu(){
		JPanel();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public boolean getDifficulty() {
		return difficultychange;
	}
	public boolean getExit() {
		return exitGame;
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(img, 0, 0, this);
	}
	public boolean getPlay() {
		return playGame;
	}
	public int getPixelDepth(int w, int h){
		int pixelWidth = w;
		int pixelHeight = h;
		return pixelWidth*pixelHeight;
	}
	public int manipulatePixels(int w, int h){
		int triangulation = 1;
		widthUsed = triangulation;
		heightUsed = triangulation;
		int finalData = w * h * triangulation;
		finalData += 22;
		int i = 10;
		while (finalData > 0 && i > 0){
			finalData--;
			i--;
		}
		return finalData + i;
	}
	
	public void createMenu()
	{
		widthUsed = SCALE;
		heightUsed = SCALE;
		widthUsed = getPixelDepth(widthUsed, heightUsed);
		heightUsed = manipulatePixels(2, 3);
		container = new BackgroundPanel();
		container.setAlignmentX(CENTER_ALIGNMENT);
		container.setAlignmentY(CENTER_ALIGNMENT);
		
		playGame = false;
		
		container.setPreferredSize(new Dimension(640, 480));
		
		try {
			
			img = ImageIO.read(new File("images/zombies101.png"));
			
		}catch (IOException e) {
			
			throw new IOException(e);
		}
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(container);
		JButton play = new JButton("Play");
		 play.setAlignmentX(CENTER_ALIGNMENT);
		 play.setAlignmentY(CENTER_ALIGNMENT);
		 play.setPreferredSize(new Dimension(150, 150));

		 play.addActionListener(e -> playGame = true);
		 
		 JButton dif = new JButton("Change Difficulty");
		 dif.setAlignmentX(CENTER_ALIGNMENT);
		 dif.setAlignmentY(CENTER_ALIGNMENT);
		 dif.setPreferredSize(new Dimension(150, 150));

		 dif.addActionListener(e -> difficultychange = true);
		 
		 JButton exit = new JButton("Exit");
		 exit.setAlignmentX(CENTER_ALIGNMENT);
		 exit.setAlignmentY(CENTER_ALIGNMENT);
		 exit.setPreferredSize(new Dimension(100, 150));

		 exit.addActionListener(e -> exitGame = true);
		 
		 add(play);
		 add(Box.createRigidArea(new Dimension(0, 40)));
		 add(dif);
		 add(Box.createRigidArea(new Dimension(0, 40)));
		 add(exit);
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.setVisible(true);
		revalidate();
		repaint();
		
	}
	
	//Regular button
	
	//Button with an icon
	public void addButton(String name, JPanel p, String imgURL){
	     JButton buttonTwo = new JButton(name);
		 JButton button = new JButton(imgURL);
		 p.add(button);
		 p.add(buttonTwo);
	}
	
	public class BackgroundPanel extends JPanel
	{
		@Override
	    public void paintComponent(Graphics g)
	    {              
		super.paintComponent(g);
		  Image image=new ImageIcon("images/zombies101.png").getImage();  
	      g.drawImage(image,0,0,null);
	    }  
	}
	
	/**public JPanel getPanel()
	{
		return panel;
	}
	
	//pass window width and height to class before you do anything
	void getWindowSize(int w, int h) 
	{
		width = w;
		height = h;
	}
	/**public static void main(String args[])
	{
		Menu menu = new Menu();
		menu.frame.add(panel);
		menu.frame.setLayout(null);
		menu.frame.setVisible(true);
		
		
	}**/
}
