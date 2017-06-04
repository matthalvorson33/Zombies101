package game;


import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class Menu extends JPanel {
	private int width;
	private int height;
	public static final int SCALE = 3;
	public static final String NAME = "Zombies101";
	private BufferedImage img;
	protected backgroundPanel container;
	public boolean playGame;
	public boolean exitGame;
	public Menu(){
		JPanel panel1 = new JPanel();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public boolean getExit() {
		return exitGame;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g.drawImage(img, 0, 0, this);
	}
	public boolean getPlay() {
		return playGame;
	}
	
	public void createMenu()
	{
		container = new backgroundPanel();
		container.setAlignmentX(CENTER_ALIGNMENT);
		container.setAlignmentY(CENTER_ALIGNMENT);
		container.setPreferredSize(new Dimension(640, 480));
		playGame = false;
		/**JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		
		container.add(panel1);
		container.add(panel2);**/
		try {
			img = ImageIO.read(new File("/home/tmula/workspace/Zombies101/src/game/images/zombies101.png"));
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(container);
		JButton play = new JButton("Play");
		 play.setAlignmentX(CENTER_ALIGNMENT);
		 play.setAlignmentY(CENTER_ALIGNMENT);
		 play.setPreferredSize(new Dimension(150, 150));
		 add(play);
		 play.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e)
	      {
	        // display/center the jdialog when the button is pressed
	        playGame = true;
	      }
	    });
		 JButton exit = new JButton("Exit");
		 exit.setAlignmentX(CENTER_ALIGNMENT);
		 exit.setAlignmentY(CENTER_ALIGNMENT);
		 exit.setPreferredSize(new Dimension(100, 150));
		 add(exit);
		 exit.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e)
		      {
		        // display/center the jdialog when the button is pressed
		        exitGame = true;
		      }
		    });
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.setVisible(true);
		revalidate();
		repaint();
	}
	
	//Regular button
	
	//Button with an icon
	public void addButton(String name, JPanel p, String imgURL){
		 JButton button = new JButton(name);
		 p.add(button);
	}
	
	public class backgroundPanel extends JPanel
	{
		@Override
	    public void paintComponent(Graphics g)
	    {              
		super.paintComponent(g);
		  Image image=new ImageIcon("images/zombies101.png").getImage();  
	      g.drawImage(image,0,0,this);
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
