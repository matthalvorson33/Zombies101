
import java.awt.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mchalvor
 */
public class Zombie extends Moveable
{
    int lives = 3;

    public Zombie(int x, int y)
    {
        super(x, y);
    }

    @Override
    public Rectangle getHitbox()
    {
        //TODO update this with actual dimensions of the picture
        return new Rectangle(xPos, yPos, 20, 100);
    }

    public void decrementHealth()
    {
        lives--;
    }

    public int getLives()
    {
        return lives;
    }

}
