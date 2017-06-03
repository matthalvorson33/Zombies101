
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Random;

class GamePanel extends JPanel implements ActionListener
{
	Random rand = new Random();
    // Creating images for single objects
    private final Image rz_background = new ImageIcon("images/library.png").getImage(); // Background Image
    private final Image rz_still_right = new ImageIcon("images/player.png").getImage(); // Standing still
    private final Image rz_still_left = new ImageIcon("images/playerleft.png").getImage(); // Walking left
    private final Image rz_walk_left2 = new ImageIcon("images/player.png").getImage(); //
    private final Image rz_walk_right2 = new ImageIcon("images/playerright.png").getImage(); // Walking right
    private final Image rz_jump_right = new ImageIcon("images/playerjump.png").getImage(); // jumping
    private final Image rz_jump_left = new ImageIcon("images/playerjump.png").getImage(); //
    private final Image zombieImage = new ImageIcon("images/zombieleft.png").getImage(); // pipe
    private final Image playerhurt = new ImageIcon("images/playerhurt.png").getImage(); // hurt player
    private final Image spitImage = new ImageIcon("images/spitImage.png").getImage(); // zombie bullet
    
    private final Image bulletImage = new ImageIcon("images/bullet.png").getImage(); // pew pew
    private final Image gun1 = new ImageIcon("images/gun1.png").getImage(); // pew pew
    private final Image shotgun = new ImageIcon("images/shotgun.png").getImage(); // pew pew
    
    
    int gun = 1; // keeps track of gun type
   

    Image obj = rz_still_right; // Temporary Image reference

    final private int BKMIN_X = 900, BKMAX_X = 3600; // Min and Max of background
    public int bk_x = 695; // background x and y coordinates
    public int bk_y = 800;

    static boolean moveableRight = true; // variable for collision detection
    static boolean moveableLeft = true;
    static boolean moveableDown = false;

    boolean jumpright = true;

    static boolean jump = false; // For jump
    private Timer time;

    static boolean pause = false;
    int run = 0;

    Player player = new Player(300, 615);

    //Collections
    ArrayList<Zombie> zombies = new ArrayList<Zombie>();
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    ArrayList<Integer> ammo = new ArrayList<Integer>(); //keeps track of ammo left for each gun


    GamePanel()
    {
        setLayout(null);
        time = new Timer(30, this); // starting a timer and passing the
        // actionlistener for the running animation
        time.start(); // starting
        System.out.println("hello");
        
        initZombies();
        initGuns();
        
        addKeyListener(new KeyAdapter() // Movement
        {
            public void keyPressed(KeyEvent kp)
            {

                if (kp.getKeyCode() == KeyEvent.VK_RIGHT
                        & moveableRight == true)
                {
                    player.setDirection(2);// right
                }
                if ((kp.getKeyCode() == KeyEvent.VK_LEFT)
                        & moveableLeft == true)
                {
                    player.setDirection(3); // left
                }
                if (kp.getKeyCode() == KeyEvent.VK_SPACE)
                {
                    if (jump == false & player.getYPos() == 615)
                    {
                        jump = true;
                        moveableDown = true;
                        if (player.getDirection() == 2)
                        {
                            jumpright = true;
                        }
                        if (player.getDirection() == 3)
                        {
                            jumpright = false;
                        }
                    }
                }
                if(kp.getKeyCode() == KeyEvent.VK_SHIFT)
                {
                	shoot();
                }
                if(kp.getKeyCode() == KeyEvent.VK_C)
                {
                	toggle_gun();
                }
            } // end keyPressed

            public void keyReleased(KeyEvent kr)
            {
                if (player.getDirection() == 2)
                {
                    player.setImage(rz_still_right); // if direction is right
                }
                if (player.getDirection() == 3)
                {
                    player.setImage(rz_still_left); // if direction is left
                }
                player.setDirection(0); // set still image
            }
        });// end anonymous class and KeyListener
    }// end constructor

    // ///////////////////////////// TIMED ACTION LISTENER \\\\\\\\\\\\\\\\\\\\\\\
    public void actionPerformed(ActionEvent e)
    {
        if (player.getDirection() == 2)
        {
            right();
        }
        if (player.getDirection() == 3)
        {
            left();
        }

        checkCollisions();
        moveZombies();
        moveBullets();
        // repaint(); //repaint after 30ms
    }

    public void checkCollisions()
    {
    	Iterator<Bullet> iter = bullets.iterator();
    	while(iter.hasNext())
    	{
    		Bullet bullet = iter.next();
            if (player.getHitbox().intersects(bullet.getHitbox()))
            {
                iter.remove(); 
                player.decrementHealth();
            }

            Iterator<Zombie> zombie_iter = zombies.iterator();
            while(zombie_iter.hasNext())
            {
            	Zombie zombie = zombie_iter.next();
                if (zombie.getHitbox().intersects(bullet.getHitbox()))
                {
                	iter.remove();
                    zombie.decrementHealth();
                    if (zombie.getLives() == 0)
                    {
                        zombie_iter.remove();
                    }
                }
            }
        }
        
        for (Zombie zombie : zombies)
        {

        	if(player.getHitbox().intersects(zombie.getHitbox()))
        	{
        		System.out.println("ouch ");
        	}
        }
    }

    // ////////////////////////////////// PAINT FUNCTION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        requestFocus(); // get focus after changing card
        setFocusable(true);

        // setting background points and cash in the game
        setBackground(g2d);
        setZombies(g2d);
        drawBullets(g2d);

        // checking jump collision and enemy death
        jump();

        //to turn razmazio in normal still state after jump
        if (player.getYPos() == 615 & player.getDirection() != 3 & player.getDirection() != 2)
        {
            if (player.getImage() == rz_jump_left)
            {
                player.setImage(rz_still_left);
            }
            if (player.getImage() == rz_jump_right)
            {
                player.setImage(rz_still_right);
            }
        }
        g2d.drawImage(player.getImage(), player.getXPos(), player.getYPos(), 200, 200, this); // Drawing the character image
        drawGun(g2d);
        drawAmmo(g2d);
        repaint();
    }

    // /////////////////////////////// DIRECTION CONDITIONS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    void jump() // jump mechanism
    {

        if (moveableDown == true)
        {
            if (jump == true & player.getYPos() >= 450) // For upward motion during jump
            {
                if (jumpright == true)
                {
                    player.setImage(rz_jump_right);
                }
                else
                {
                    player.setImage(rz_jump_left);
                }

                player.move(0, -1);
                if (player.getYPos() <= 450)
                {
                    jump = false;
                }
            }
            if (jump == false & player.getYPos() < 615) // For downward motion during jump
            {
                if (jumpright == true)
                {
                    player.setImage(rz_jump_right);
                }
                else
                {
                    player.setImage(rz_jump_left);
                }
                player.move(0, 1);
            }
        }

    }

    void left()
    {
        if (moveableLeft == true & bk_x > BKMIN_X)
        {
            bk_x -= 8; // decrease xcoord while moving left

            if (run % 3 == 0 | run % 5 == 0)
            {
                player.setImage(rz_still_left); // set image
            }
            else
            {
                player.setImage(rz_walk_left2);
            }
            run++;
            for(Zombie z : zombies)
            {
            	z.move(8, 0);
            }
        }
    }

    void right()
    {
        if (moveableRight == true & bk_x < BKMAX_X - 800)
        {
            bk_x += 8; // increasing xcoord while moving right
            player.setImage(rz_walk_right2);
            run++;
            for(Zombie z : zombies)
            {
            	z.move(-8, 0);
            }
        }// end if
    }// end right
    
    void shoot()
    {
    	if(gun == 0) // gun 1
    	{
    		int ammoGun1 = ammo.get(0);
    		if(ammoGun1 > 0) //if there's ammo left
    		{
    			Bullet b = new Bullet(player.getXPos()+200, player.getYPos() + 75, 1);
            	bullets.add(b);
            	ammo.set(0, ammoGun1 -1);  // update ammo
    		}
    		
    	}
    	else if (gun == 1) //shotgun
    	{
    		int ammoShotgun = ammo.get(1);
    		if(ammoShotgun > 0)  // if there's ammo left
    		{
    			Bullet b1 = new Bullet(player.getXPos()+200, player.getYPos() + 75, 1);
            	bullets.add(b1);
            	Bullet b2 = new Bullet(player.getXPos()+200, player.getYPos() + 75, 2);
            	bullets.add(b2);
            	Bullet b3 = new Bullet(player.getXPos()+200, player.getYPos() + 75, 3);
            	bullets.add(b3);
            	ammo.set(1, ammoShotgun -1);  // update ammo
    		}
    		
    	}
    	
    }

    // ////////////////////////////////////// DRAW FUNCTIONS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    void setBackground(Graphics g2d)
    {
        g2d.drawImage(rz_background, 700 - bk_x, 0, null); // Drawing background relative to character
    }

    void setZombies(Graphics g2d)
    {
    	for(Zombie z : zombies)
    	{
    		g2d.drawImage(zombieImage, z.getXPos(), z.getYPos(), 200, 200, null);
    	}
        //g2d.drawImage(zombieImage, 1200 - bk_x, 600, 200, 200, null); // first Zombie
        //g2d.drawImage(zombieImage, 3200 - bk_x, 600, 200, 200, null); // second Zombie
    }
    
    
    // ///////////////////////////////////// WEAPON FUNCTIONS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    
    void initGuns()
    {
        setFont(new java.awt.Font("Veranda", 1, 24));
        ammo.add(50); //gun1
        ammo.add(20); //shotgun
    }
    
    void drawGun(Graphics g2d)
    {
    	if(gun == 0) //handgun
    	{
    		g2d.drawImage(gun1, player.getXPos() + 175, player.getYPos() + 75, 50, 50, null);		
    	}
    	else if (gun == 1) // shotgun
    	{
    		g2d.drawImage(shotgun, player.getXPos() + 175, player.getYPos() + 75, 150, 50, null);
    	}
    }
    
    void drawBullets(Graphics g2d)
    {
    	for(Bullet b: bullets)
    	{
         if (b.getType() == 4)
         {
            g2d.drawImage(spitImage, b.getXPos(), b.getYPos(), 40, 20, null);
         }
         else {
    		   g2d.drawImage(bulletImage, b.getXPos(), b.getYPos(), 40, 20, null);
         }
    	}
    }
    
    void moveBullets()
    {
    	for(Bullet b: bullets)
    	{
    		if(b.getType() == 1) // straight
    		{
    			b.move(30, 0);	
    		}
    		else if (b.getType() == 2) // angle up
    		{
    			b.move(30,  -2);
    		}
    		else if (b.getType() == 3) // angle down
    		{
    			b.move(30, 2);
    		}
         else if (b.getType() == 4) //zombie bullet
         {
            b.move (-30, 0);
         }
    	}
    }
    
    void toggle_gun()
    {
    	gun = (gun+1) % 2;
    }
    
    void drawAmmo(Graphics g2d)
    {
    	String str = "Ammo: " + Integer.toString(ammo.get(gun));
    	g2d.drawString(str, 30, 30);
    }
    
 // ///////////////////////////////////// ZOMBIE FUNCTIONS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    void initZombies()
    {
        Zombie z1 = new Zombie(1200, 600, rand.nextInt(20) + 10);
        zombies.add(z1);
        Zombie z2 = new Zombie(2200, 600, rand.nextInt(20) + 10);
        zombies.add(z2);
    }
    
    void moveZombies()
    {
    	for(Zombie z : zombies)
    	{
    		if(z.getUpdown() == 1)
    		{
    			z.move(0, z.getSpeed()*-1);
    			if(z.getYPos() < 15)
    			{
    				z.updown = 0;
    			}
    		}
    		else
    		{
    			z.move(0, z.getSpeed());
    			if(z.getYPos() > 585)
    			{
    				z.updown = 1;
    			}
    		}
    	}
    }
}
