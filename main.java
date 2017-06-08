package game;
import javax.swing.JFrame;

public class main extends JFrame
{
    GamePanel gp = new GamePanel();
    Menu menu = new Menu();
    static boolean close;
    main()
    {
    	close = false;
    	add(menu);
    	menu.createMenu();
        setSize(1024, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        boolean done = false;
		while(done == false){
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

    public static void main(String[] args)
    {
        main rz = new main();
        if (close == true) {
        	System.exit(0);
        }
    }// end main
}// end frame class
