import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.util.*;

class GamePanel extends JPanel implements ActionListener {

	// Creating images for single objects
	protected Image rz_background = new ImageIcon("images\\library.png").getImage(); // Background Image
	protected Image rz_still_right = new ImageIcon("images\\player.png").getImage(); // Standing still
	protected Image rz_still_left = new ImageIcon("images\\playerleft.png").getImage(); // Walking left
	protected Image rz_walk_left2 = new ImageIcon("images\\player.png").getImage(); //
	protected Image rz_walk_right2 = new ImageIcon("images\\playerright.png").getImage(); // Walking right
	protected Image rz_jump_right = new ImageIcon("images\\playerjump.png").getImage(); // jumping
	protected Image rz_jump_left = new ImageIcon("images\\playerjump.png").getImage(); //
	protected Image zombies = new ImageIcon("images\\zombieleft.png").getImage(); // pipe

	Image obj = rz_still_right; // Temporary Image reference

	final private int BKMIN_X = 900, BKMAX_X = 3600; // Min and Max of background
	public int bk_x = 695; // background x and y coordinates
	public int bk_y = 800;
	public int rz_x = 600; // character x and y coordinates
	public int rz_y = 615;

	static int direction = 0; // 0=still 1=up , 2=right , 3=left , 4=down

	static boolean moveableRight = true; // variable for collision detection
	static boolean moveableLeft = true;
	static boolean moveableDown = false;
	
	boolean jumpright = true;

	static boolean jump = false; // For jump
	private Timer time;

	static boolean pause = false;
	int run = 0;

   //Collections
   ArrayList<Zombie> zombies = new ArrayList<Zombie>();
   ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	GamePanel() {
		setLayout(null);
		time = new Timer(30, this); // starting a timer and passing the
									// actionlistener for the running animation
		time.start(); // starting

		addKeyListener(new KeyAdapter() // Movement
		{
			public void keyPressed(KeyEvent kp) {

				if (kp.getKeyCode() == KeyEvent.VK_RIGHT
						& moveableRight == true) {
					direction = 2; // right
				}
				if ((kp.getKeyCode() == KeyEvent.VK_LEFT)
						& moveableLeft == true) {
					direction = 3; // left
				}
				if (kp.getKeyCode() == KeyEvent.VK_SPACE) {
					if (jump == false & rz_y == 615) {
						jump = true;
						moveableDown = true;
						if (direction == 2)
							jumpright = true;
						if (direction == 3)
							jumpright = false;
					}
				}
			} // end keyPressed

			public void keyReleased(KeyEvent kr) {
				if (direction == 2)
					obj = rz_still_right; // if direction is right
				if (direction == 3)
					obj = rz_still_left; // if direction is left

				direction = 0; // set still image
			}
		});// end anonymous class and KeyListener
	}// end constructor

	// ///////////////////////////// TIMED ACTION LISTENER \\\\\\\\\\\\\\\\\\\\\\\
	public void actionPerformed(ActionEvent e) {
		if (direction == 2)
			right();
		if (direction == 3)
			left();

      checkCollisions();
		// repaint(); //repaint after 30ms
	}

   public void checkCollisions() {
      for (Bullet bullet : bullets) {
         if (player.getHitbox().intersects(bullet.getHitbox())) {
            bullets.remove(bullet); //this might not work..might need to index loop and remove that way
            player.decrementHealth(); //this needs to be added too
         }
         
         for (Zombie zombie : zombies) {
            if (zombie.getHitbox().intersects(bullet.getHitbox())) {
               zombie.decrementHealth(); //add this too
               if (zombie.getHealth() == 0) {
                  zombies.remove(zombie);
               }
            }
         }

      }
   }

	// ////////////////////////////////// PAINT FUNCTION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public void paintComponent(Graphics g) {
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
		if (rz_y == 615 & direction != 3 & direction != 2) {
			if (obj == rz_jump_left)
				obj = rz_still_left;
			if (obj == rz_jump_right)
				obj = rz_still_right;
		}
		g2d.drawImage(obj, rz_x, rz_y, 200, 200, this); // Drawing the character image

		repaint();
	}

	// /////////////////////////////// DIRECTION CONDITIONS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
	void jump() // jump mechanism
	{

		if (moveableDown == true) {
			if (jump == true & rz_y >= 450) // For upward motion during jump
			{
				if (jumpright == true)
					obj = rz_jump_right;
				else
					obj = rz_jump_left;

				rz_y--;
				if (rz_y <= 450)
					jump = false;
			}
			if (jump == false & rz_y < 615) // For downward motion during jump
			{
				if (jumpright == true)
					obj = rz_jump_right;
				else
					obj = rz_jump_left;
				rz_y++;
			}
		}

	}

	void left() {
		if (moveableLeft == true & bk_x > BKMIN_X) {
			bk_x -= 8; // decrease xcoord while moving left

			if (run % 3 == 0 | run % 5 == 0)
				obj = rz_still_left; // set image
			else
				obj = rz_walk_left2;
			run++;
		}
	}

	void right() {
		if (moveableRight == true & bk_x < BKMAX_X - 800) {
			bk_x += 8; // increasing xcoord while moving right

			if (run % 3 == 0 | run % 5 == 0)
				obj = rz_still_right;
			else
				obj = rz_walk_right2;
			run++;
		}// end if
	}// end right

	// ////////////////////////////////////// SETTER FUNCTIONS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	void setBackground(Graphics g2d) {
		g2d.drawImage(rz_background, 700 - bk_x, 0, null); // Drawing background relative to character
	}

	void setZombies(Graphics g2d) {
		g2d.drawImage(zombies, 2200 - bk_x, 600, 200, 200, null); // first Zombie
		g2d.drawImage(zombies, 3200 - bk_x, 600, 200, 200, null); // second Zombie
	}
}
