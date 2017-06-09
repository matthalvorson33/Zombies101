package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener
{
    Random rand = new Random();
    // Creating images for single objects
    private final transient Image background = new ImageIcon("images/lib_long.png").getImage(); // Background Image
    private final transient Image stillRight = new ImageIcon("images/player.png").getImage(); // Standing still
    private final transient Image stillLeft = new ImageIcon("images/playerleft.png").getImage(); // Walking left
    private final transient Image walkRight = new ImageIcon("images/playerright.png").getImage(); // Walking right
    private final transient Image jumpImg = new ImageIcon("images/playerjump.png").getImage(); // jumping
    private final transient Image zombieImage = new ImageIcon("images/zombieleft.png").getImage(); // pipe
    private final transient Image spitImage = new ImageIcon("images/spitImage.png").getImage(); // zombie bullet

    private final transient Image bulletImage = new ImageIcon("images/bullet.png").getImage(); // pew pew <-- Solid, useful comments here. Good work.
    private final transient Image gun1 = new ImageIcon("images/gun1Right.png").getImage(); // pew pew
    private final transient Image shotgun = new ImageIcon("images/shotgunRight.png").getImage(); // pew pew
    private final transient Image gun1Right = new ImageIcon("images/gun1.png").getImage(); // pew pew
    private final transient Image shotgunRight = new ImageIcon("images/shotgun.png").getImage(); // pew pew
    int gun = 1; // keeps track of gun type

    int spitMax;
    int spitTimer;

    transient Image obj = stillRight; // Temporary Image reference

    private static final int backgroundMinX = 900;
    private static final int backgroundMaxX = 10800; // Min and Max of background
    private int backgroundX = 695; // background x and y coordinates
    private int backgroundY = 800;

    private int score = 1000;

    static boolean moveableRight = true; // variable for collision detection
    static boolean moveableLeft = true;
    static boolean moveableDown = false;

    boolean jumpright = true;

    static boolean jump = false; // For jump
    private Timer time;

    static boolean pause = false;

    int zombiesDrawn = 0; //For loop testing purposes

    Player player = new Player(300, 615);

    //Collections
    ArrayList<Zombie> zombies = new ArrayList<>();
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Integer> ammo = new ArrayList<>(); //keeps track of ammo left for each gun
    ArrayList<Ammo_Box> boxes = new ArrayList<>();
    
    public GamePanel()
    {
        spitTimer = 0;
        spitMax = 100;
        setLayout(null);
        time = new Timer(30, this); // starting a timer and passing the
        // actionlistener for the running animation
        time.start(); // starting

        initZombies();
        initGuns();
        initBoxes();

        addKeyListener(new KeyAdapter() // Movement
        {
           @Override
            public void keyPressed(KeyEvent kp)
            {

                if (kp.getKeyCode() == KeyEvent.VK_RIGHT
                        && moveableRight)
                {
                    player.setDirection(2);// right
                }
                if ((kp.getKeyCode() == KeyEvent.VK_LEFT)
                        && moveableLeft)
                {
                    player.setDirection(3); // left
                }
                if (kp.getKeyCode() == KeyEvent.VK_SPACE && !jump && player.getYPos() == 615)
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
                if (kp.getKeyCode() == KeyEvent.VK_SHIFT)
                {
                    shoot();
                }
                if (kp.getKeyCode() == KeyEvent.VK_C)
                {
                    toggle_gun();
                }
            } // end keyPressed

            @Override
            public void keyReleased(KeyEvent kr)
            {
                if (player.getDirection() == 2)
                {
                    player.setImage(stillRight); // if direction is right
                    player.setImageNum(0);
                }
                if (player.getDirection() == 3)
                {
                    player.setImage(stillLeft); // if direction is left
                    player.setImageNum(1);
                }
                player.setDirection(0); // set still image
            }
        });// end anonymous class and KeyListener
    }// end constructor

    // ///////////////////////////// TIMED ACTION LISTENER \\\\\\\\\\\\\\\\\\\\\\\
    @Override
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

        spitTimer++;
        if (spitTimer == spitMax)
        {
            if (!zombies.isEmpty())
            {
                zombieShoot(0);
            }
            spitTimer = 0;
            spitMax = rand.nextInt(30) + 25;
        }

        checkCollisions();
        moveZombies();
        moveBullets();
        // repaint(); //repaint after 30ms
    }

    public void zombieShoot(int ind)
    {
        Zombie shooter = zombies.get(ind);
        Bullet b = new Bullet(shooter.getXPos() + 10, shooter.getYPos() + 25, 4, 1);
        bullets.add(b);
    }

    public void checkCollisions()
    {
        Iterator<Bullet> iter = bullets.iterator();
        while (iter.hasNext())
        {
            Bullet bullet = iter.next();

            if (bullet.getXPos() > backgroundMaxX)
            {
                iter.remove();
            }

            if (player.getHitbox().intersects(bullet.getHitbox()))
            {
                iter.remove();
                player.decrementHealth();
            }

            Iterator<Zombie> zombieIterator = zombies.iterator();
            while (zombieIterator.hasNext())
            {
                Zombie zombie = zombieIterator.next();
                if (zombie.getHitbox().intersects(bullet.getHitbox()) && bullet.getType() != 4)
                {
                    iter.remove();
                    zombie.decrementHealth();
                    if (zombie.getLives() == 0)
                    {
                        zombieIterator.remove();
                        score += 100;
                    }
                }
            }
        }

        for (Zombie zombie : zombies)
        {

            if (player.getHitbox().intersects(zombie.getHitbox()))
            {
                player.decrementHealth();
            }
        }
    }

    // ////////////////////////////////// PAINT FUNCTION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    @Override
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
            if (player.getImage() == jumpImg && player.getImageNum() == 1)
            {
                player.setImage(stillLeft);
                player.setImageNum(1);
            }
            if (player.getImage() == jumpImg && player.getImageNum() == 2)
            {
                player.setImage(stillRight);
                player.setImageNum(0);
            }
        }
        g2d.drawImage(player.getImage(), player.getXPos(), player.getYPos(), 200, 200, this); // Drawing the character image
        drawGun(g2d);
        drawAmmo(g2d);
        drawScore(g2d);
        drawLives(g2d);
        drawBoxes(g2d);

        if (player.getLives() < 1)
        {
            game_over(g2d);
        }

        if (zombies.isEmpty())
        {
            level_cleared(g2d);
        }

        repaint();
    }

    // /////////////////////////////// DIRECTION CONDITIONS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    void jump() // jump mechanism
    {

        if (moveableDown)
        {
            if (jump && player.getYPos() >= 450) // For upward motion during jump
            {
                if (jumpright)
                {
                    player.setImage(jumpImg);
                    player.setImageNum(2);
                }
                else
                {
                    player.setImage(jumpImg);
                    player.setImageNum(1);
                }

                player.move(0, -2);
                if (player.getYPos() <= 450)
                {
                    jump = false;
                }
            }
            if (!jump && player.getYPos() < 615) // For downward motion during jump
            {
                if (jumpright)
                {
                    player.setImage(jumpImg);
                    player.setImageNum(2);
                }
                else
                {
                    player.setImage(jumpImg);
                    player.setImageNum(1);
                }
                player.move(0, 2);
            }
        }

    }

    void left()
    {
        if (moveableLeft && backgroundX > backgroundMinX)
        {
            backgroundX -= 8; // increasing xcoord while moving right
            player.setImage(stillLeft);
            player.setImageNum(1);
            for (Zombie z : zombies)
            {
                z.move(8, 0);
            }
        }
    }

    void right()
    {
        if (moveableRight && backgroundX < backgroundMaxX - 800)
        {
            backgroundX += 8; // increasing xcoord while moving right
            player.setImage(walkRight);
            player.setImageNum(2);
            for (Zombie z : zombies)
            {
                z.move(-8, 0);
            }
        }// end if
    }// end right

    void shoot()
    {
        if (gun == 0) // gun 1
        {
            int ammoGun1 = ammo.get(0);
            if (ammoGun1 > 0) //if there's ammo left
            {
                if (player.getImageNum() == 1)
                {
                    Bullet b = new Bullet(player.getXPos() - 40, player.getYPos() + 75, 1, 0);
                    bullets.add(b);
                }
                else
                {
                    Bullet b = new Bullet(player.getXPos() + 200, player.getYPos() + 75, 1, 1);
                    bullets.add(b);
                }
                ammo.set(0, ammoGun1 - 1);  // update ammo
                score -= 10;
            }

        }
        else if (gun == 1) //shotgun
        {
            int ammoShotgun = ammo.get(1);
            if (ammoShotgun > 0)  // if there's ammo left
            {
                if (player.getImageNum() == 1)
                {
                    Bullet b1 = new Bullet(player.getXPos() - 40, player.getYPos() + 75, 1, 0);
                    bullets.add(b1);
                    Bullet b2 = new Bullet(player.getXPos() - 40, player.getYPos() + 75, 2, 0);
                    bullets.add(b2);
                    Bullet b3 = new Bullet(player.getXPos() - 40, player.getYPos() + 75, 3, 0);
                    bullets.add(b3);
                }
                else
                {
                    Bullet b1 = new Bullet(player.getXPos() + 200, player.getYPos() + 75, 1, 1);
                    bullets.add(b1);
                    Bullet b2 = new Bullet(player.getXPos() + 200, player.getYPos() + 75, 2, 1);
                    bullets.add(b2);
                    Bullet b3 = new Bullet(player.getXPos() + 200, player.getYPos() + 75, 3, 1);
                    bullets.add(b3);
                }

                ammo.set(1, ammoShotgun - 1);  // update ammo
                score -= 10;
            }

        }

    }

    // ////////////////////////////////////// DRAW FUNCTIONS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    void setBackground(Graphics g2d)
    {
        g2d.drawImage(background, 700 - backgroundX, 0, null); // Drawing background relative to character
    }

    void setZombies(Graphics g2d)
    {
        zombiesDrawn = 0;
        for (Zombie z : zombies)
        {
            zombiesDrawn++;
            g2d.drawImage(zombieImage, z.getXPos(), z.getYPos(), 200, 200, null);
        }
        //g2d.drawImage(zombieImage, 1200 - backgroundX, 600, 200, 200, null); // first Zombie
        //g2d.drawImage(zombieImage, 3200 - backgroundX, 600, 200, 200, null); // second Zombie
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
        if (gun == 0) //handgun
        {
            if (player.getImageNum() == 1) //draw left gun
            {
                g2d.drawImage(gun1, player.getXPos() - 20, player.getYPos() + 75, 50, 50, null);
            }
            else // draw right gun
            {
                g2d.drawImage(gun1Right, player.getXPos() + 175, player.getYPos() + 75, 50, 50, null);
            }

        }
        else if (gun == 1) // shotgun
        {
            if (player.getImageNum() == 1) // draw left gun
            {
                g2d.drawImage(shotgun, player.getXPos() - 100, player.getYPos() + 75, 150, 50, null);
            }
            else  //draw right gun
            {
                g2d.drawImage(shotgunRight, player.getXPos() + 175, player.getYPos() + 75, 150, 50, null);
            }
        }
    }

    void drawBullets(Graphics g2d)
    {
        for (Bullet b : bullets)
        {
            if (b.getType() == 4)
            {
            	//System.out.println(b.getXPos());
                g2d.drawImage(spitImage, b.getXPos(), b.getYPos(), 40, 20, null);
            }
            else
            {
                g2d.drawImage(bulletImage, b.getXPos(), b.getYPos(), 40, 20, null);
            }
        }
    }

    void moveBullets()
    {
        for (Bullet b : bullets)
        {
           int type = b.getType();
			  boolean left = (b.getDir() == 0);
           switch(type) {
               case 1:
                if (left)
                {
                    b.move(-30, 0);
                }
                else
                {
                    b.move(30, 0);
                }
                break;
            	case 2:
               if (left)
                {
                    b.move(-30, -2);
                }
                else
                {
                    b.move(30, -2);
                }
                break;
            case 3:
                if (left)
                {
                    b.move(-30, 2);
                }
                else
                {
                    b.move(30, 2);
                }
                break;
            //zombie bullet
            case 4:
               b.move(-30, 0);
               break;
            default:
               break;
			  }
        }
    }

    void toggle_gun()
    {
        gun = (gun + 1) % 2;
    }

    public int getZombiesDrawn()
    {
        return zombiesDrawn;
    }

    void drawAmmo(Graphics g2d)
    {
        String str = "Ammo: " + Integer.toString(ammo.get(gun));
        g2d.setColor(Color.GRAY);
        g2d.drawString(str, 30, 30);
    }

    void drawScore(Graphics g2d)
    {
        String str = "Score: " + Integer.toString(score);
        g2d.setColor(Color.GRAY);
        g2d.drawString(str, 200, 30);
    }

    void drawLives(Graphics g2d)
    {
        String str = "Lives: " + Integer.toString(player.getLives() > 0 ? player.getLives() : 0);
        g2d.setColor(Color.GRAY);
        g2d.drawString(str, 900, 30);
    }

    void drawBoxes(Graphics g2d)
    {
    	for(Ammo_Box b: boxes)
    	{
    		System.out.print(b.getXPos());
    		System.out.println(player.getXPos());
            g2d.drawImage(gun1, b.getXPos(), b.getYPos(), 100, 100, null);
    	}
    }
    
    // ///////////////////////////////////// ZOMBIE FUNCTIONS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    void initZombies()
    {
        Zombie z1 = new Zombie(1200, 600, rand.nextInt(20) + 10);
        zombies.add(z1);
        Zombie z2 = new Zombie(2200, 600, rand.nextInt(20) + 10);
        zombies.add(z2);
        Zombie z3 = new Zombie(3200, 600, rand.nextInt(20) + 10);
        zombies.add(z3);
        Zombie z4 = new Zombie(3800, 600, rand.nextInt(20) + 10);
        zombies.add(z4);
        Zombie z5 = new Zombie(4900, 600, rand.nextInt(20) + 10);
        zombies.add(z5);
        Zombie z6 = new Zombie(6000, 600, rand.nextInt(20) + 10);
        zombies.add(z6);
    }
    
    void initBoxes()
    {
    	Ammo_Box box1 = new Ammo_Box(200, 600);
    	boxes.add(box1);
    	Ammo_Box box2 = new Ammo_Box(3000, 500);
    	boxes.add(box2);
    	Ammo_Box box3 = new Ammo_Box(4500, 500);
    	boxes.add(box3);
    }

    public void setZombieNum(int num)
    {
        if (num < zombies.size())
        {
            int cycles = zombies.size() - num;
            for (int i = 0; i < cycles; i++)
            {
                zombies.remove(0);
            }
        }
        else if (num > zombies.size())
        {
            int cycles = num - zombies.size();
            for (int i = 0; i < cycles; i++)
            {
                spawnRandomZombie();
            }
        }

    }

    public void spawnRandomZombie()
    {
        zombies.add(new Zombie(rand.nextInt(6000), 600, rand.nextInt(20) + 10));
    }

    void moveZombies()
    {
        for (Zombie z : zombies)
        {
            if (z.getUpdown() == 1)
            {
                z.move(0, z.getSpeed() * -1);
                if (z.getYPos() < 15)
                {
                    z.updown = 0;
                }
            }
            else
            {
                z.move(0, z.getSpeed());
                if (z.getYPos() > 585)
                {
                    z.updown = 1;
                }
            }
        }
    }

    //////////////////////////// GAME OVER \\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public void game_over(Graphics g2d)
    {
        g2d.fillRect(200, 200, 600, 500);
        g2d.setColor(Color.WHITE);
        g2d.drawString("GAME OVER!!!", 420, 300);
        g2d.drawString("You are Dead", 420, 400);

    }

    public void level_cleared(Graphics g2d)
    {
        g2d.fillRect(200, 200, 600, 500);
        g2d.setColor(Color.WHITE);
        g2d.drawString("LEVEL CLEARED!!!", 420, 300);
        String str = "Your score: " + Integer.toString(score);
        g2d.drawString(str, 420, 400);
    }
}
