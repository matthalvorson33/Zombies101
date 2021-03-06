package game;
import java.awt.Rectangle;

//This interface is for all objects that can move
public abstract class Moveable
{
    int xPos;
    int yPos;

    public Moveable(int x, int y)
    {
        this.xPos = x;
        this.yPos = y;
    }

    /**
     * Allows for movement
     *
     * @param x Delta for x position
     * @param y Delta for y position
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
