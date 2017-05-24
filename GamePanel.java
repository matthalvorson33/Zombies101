
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

class GamePanel extends JPanel implements ActionListener
{
    // Creating images for single objects
    private final Image rz_background = new ImageIcon("images/library.png").getImage(); // Background Image
    private final Image rz_still_right = new ImageIcon("images/player.png").getImage(); // Standing still
    private final Image rz_still_left = new ImageIcon("images/playerleft.png").getImage(); // Walking left
    private final Image rz_walk_left2 = new ImageIcon("images/player.png").getImage(); //
    private final Image rz_walk_right2 = new ImageIcon("images/playerright.png").getImage(); // Walking right
    private final Image rz_jump_right = new ImageIcon("images/playerjump.png").getImage(); // jumping
    private final Image rz_jump_left = new ImageIcon("images/playerjump.png").getImage(); //
    private final Image zombieImage = new ImageIcon("images/zombieleft.png").getImage(); // pipe

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

    Player player = new Player(600, 615);

    //Collections
    ArrayList<Zombie> zombies = new ArrayList<Zombie>();
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    GamePanel()
    {
        setLayout(null);
        time = new Timer(30, this); // starting a timer and passing the
        // actionlistener for the running animation
        time.start(); // starting

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
        // repaint(); //repaint after 30ms
    }

    public void checkCollisions()
    {
        for (Bullet bullet : bullets)
        {
            if (player.getHitbox().intersects(bullet.getHitbox()))
            {
                bullets.remove(bullet); //TODO this might not work..might need to index loop and remove that way
                player.decrementHealth();
            }

            for (Zombie zombie : zombies)
            {
                if (zombie.getHitbox().intersects(bullet.getHitbox()))
                {
                    zombie.decrementHealth();
                    if (zombie.getLives() == 0)
                    {
                        zombies.remove(zombie);
                    }
                }
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
        }
    }

    void right()
    {
        if (moveableRight == true & bk_x < BKMAX_X - 800)
        {
            bk_x += 8; // increasing xcoord while moving right
            player.setImage(rz_walk_right2);
            run++;
        }// end if
    }// end right

    // ////////////////////////////////////// SETTER FUNCTIONS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    void setBackground(Graphics g2d)
    {
        g2d.drawImage(rz_background, 700 - bk_x, 0, null); // Drawing background relative to character
    }

    void setZombies(Graphics g2d)
    {
        g2d.drawImage(zombieImage, 2200 - bk_x, 600, 200, 200, null); // first Zombie
        g2d.drawImage(zombieImage, 3200 - bk_x, 600, 200, 200, null); // second Zombie
    }
}
