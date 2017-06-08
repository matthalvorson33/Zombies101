package game;
import java.awt.Rectangle;

public class Bullet extends Moveable
{
	/* Gun types: 1 = straight
				  2 = angle up 
				  3 = angle down
              4 = shoot left (Zombie bullet)*/
	int type;
	int direction; //0 = left, 1 = right
	
    public Bullet(int x, int y, int t, int dir)
    {
        super(x, y);
        type = t;
        direction = dir;
    }

    public Rectangle getHitbox()
    {
        return new Rectangle(this.getXPos(), this.getYPos(), 20, 5);
    }
    
    public int getDir()
    {
    	return direction;
    }
    
    public int getType()
    {
    	return type;
    }
}
