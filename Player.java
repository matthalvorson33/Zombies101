
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author mchalvor
 */
public class Player extends Moveable
{
    int lives;

    Image curImage;

    int direction = 0; // 0=still 1=up , 2=right , 3=left , 4=down

    public Player(int x, int y)
    {
        super(x, y);
        lives = 5;
        curImage = new ImageIcon("images/player.png").getImage(); //init pic to standing facing right
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

    public Image getImage()
    {
        return curImage;
    }

    public void setImage(Image image)
    {
        curImage = image;
    }

}
