package game;

import java.awt.Rectangle;

public class Ammo_Box extends Moveable {

    public Ammo_Box(int x, int y)
    {
        super(x, y);
    }

	@Override
	public Rectangle getHitbox() 
	{
		return new Rectangle(xPos, yPos, 20, 20);
	}

	
}
