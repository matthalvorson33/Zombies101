
import java.awt.Rectangle;

//This interface is for all objects that can move
public abstract class Moveable
{
    int xPos, yPos;

    public Moveable(int x, int y)
    {
        this.xPos = x;
        this.yPos = y;
    }

    /**
     * Allows for movement
     *
     * @Param x Delta for x position
     * @Param y Delta for y position
     */
    public void move(int x, int y)
    {
        this.xPos += x;
        this.yPos += y;
    }

    public abstract Rectangle getHitbox();

    public int getXPos()
    {
        return xPos;
    }

    public int getYPos()
    {
        return yPos;
    }
}
