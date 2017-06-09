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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public class Menu extends JPanel {
	public static final int SCALE = 3;
	public static final String NAME = "Zombies101";
	private transient BufferedImage img;
	protected backgroundPanel container;
	public static boolean playGame;
	public static boolean exitGame;
	public Menu(){
		JPanel panel1 = new JPanel();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	// Display Exit Code	
	public boolean getExit() {
		return exitGame;
	}
	//Paint and Draw Component
   @Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(img, 0, 0, this);
	}
	//Run Code
	public boolean getPlay() {
		return playGame;
	}
	//Menu
	public void createMenu()
	{
		container = new backgroundPanel();
		container.setAlignmentX(CENTER_ALIGNMENT);
		container.setAlignmentY(CENTER_ALIGNMENT);
		container.setPreferredSize(new Dimension(640, 480));
		playGame = false;
		/**Old used Code - In Menu
		 * Neglect JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		
		container.add(panel1);
		container.add(panel2);**/
		
		
		try {
			img = ImageIO.read(new File("images/zombies101.png"));
		}catch (IOException e) {
			//LOGGER.info(e);
			//e.printStackTrace();
         throw new IOException();
		}
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(container);
		JButton play = new JButton("Play");
		 play.setAlignmentX(CENTER_ALIGNMENT);
		 play.setAlignmentY(CENTER_ALIGNMENT);
		 play.setPreferredSize(new Dimension(150, 150));

		 play.addActionListener(new ActionListener() {

         @Override
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

		 exit.addActionListener(new ActionListener() {
          @Override
		      public void actionPerformed(ActionEvent e)
		      {
		        // display/center the jdialog when the button is pressed
		        exitGame = true;
		      }
		    });
		 
		 add(play);
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
