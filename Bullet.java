
import java.awt.Rectangle;

public class Bullet extends Moveable
{
    public Bullet(int x, int y)
    {
        super(x, y);
    }

    public Rectangle getHitbox()
    {
        return new Rectangle(this.getXPos(), this.getYPos(), 20, 5);
    }
}
