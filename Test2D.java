package neilsucks;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;


public class Test2D extends JPanel implements ActionListener, KeyListener{
	static int WIDTH = 1000, HEIGHT = 800;
	double velX = 0, velY = 0;
	int x = 0, y = 0, sWidth = 30, sHeight = 30;;
	Timer tm = new Timer(5, this);
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.RED);
		g2d.fillRect(x, y, sWidth, sHeight);
		g2d.setFont(new Font("Comic Sans", Font.BOLD, 48));
		g2d.drawString("Hello world", 40, 40);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if (x < 0 || x >= WIDTH - sWidth)
			velX = -velX;
		
		if (y >= HEIGHT - 2 * sHeight) {			//touching the ground
			velY = 0;
			if (velX > 0)
				velX -= .05;
			if (velX < 0)
				velX += .05;
			y = HEIGHT - 2 * sHeight;
		}
		if (y < 0)
			velY = -velY;
		
		if (y <= HEIGHT - 3 * sHeight)
			velY += .1;
		
		x += velX;
		y += velY;
		
		repaint();
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Let's goooo");
		frame.add(new Test2D());
		frame.setResizable(false);
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public Test2D() {
		tm.start();
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode();
		
		if (c == KeyEvent.VK_LEFT)
			velX -= 1;
		if (c == KeyEvent.VK_RIGHT)
			velX += 1;
		if (c == KeyEvent.VK_UP)
			velY -= 1;
		if (c == KeyEvent.VK_DOWN)
			velY += 1;
		if (c == KeyEvent.VK_SPACE) {
			y -= 1;
			velY -= 10;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
