package game;

import java.awt.Rectangle;

public class AmmoBox extends Moveable {

    public AmmoBox(int x, int y)
    {
        super(x, y);
    }

	@Override
	public Rectangle getHitbox() 
	{
		return new Rectangle(xPos, yPos, 20, 20);
	}

	
}
