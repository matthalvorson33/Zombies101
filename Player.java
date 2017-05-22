
import java.awt.Rectangle;

/**
 *
 * @author mchalvor
 */
public class Player extends Moveable
{
    int lives;

    int direction = 0; // 0=still 1=up , 2=right , 3=left , 4=down

    public Player(int x, int y)
    {
        super(x, y);
        lives = 5;
    }

    @Override
    public Rectangle getHitbox()
    {
        //TODO replace with actual dimensions of picture
        return new Rectangle(xPos, yPos, 200, 200);
    }

    public void setDirection(int dir)
    {
        this.direction = dir;
    }

    public int getDirection()
    {
        return direction;
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
