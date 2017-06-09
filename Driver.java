package game;
import java.io.IOException;

import javax.swing.JFrame;

public class Driver extends JFrame
{
    GamePanel gp = new GamePanel();
    Menu menu = new Menu();
    static boolean close;
    Driver() throws IOException
    {
    	close = false;
    	add(menu);
    	menu.createMenu();
        setSize(1024, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        boolean done = false;
		while(!done){
         System.out.print("");
        	if (menu.getPlay()) 
        	{
        		remove(menu);
        		add(gp);
        		revalidate();
        		repaint();
        		done = true;
        	}
        	else if(menu.getExit()) 
        	{
        		dispose();
        		close = true;
        	}
    	}
        
    }

    public static void main(String[] args) throws IOException
    {
        new Driver();
        if (close) {
        	System.exit(0);
        }
    }// end main
}// end frame class
