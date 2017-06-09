package game;

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
    int speed;
    int updown;

    public Zombie(int x, int y, int speedNum)
    {
        super(x, y);
        speed = speedNum;
        updown = 1;
    }

    @Override
    public Rectangle getHitbox()
    {
    	generateNotion(xPos, yPos);
        return new Rectangle(xPos, yPos, 50, 200);
    }

    public int generateNotion(int health, int damage){
    	int triangulation = health * damage;
    	int finalValue = 0;
    	if (triangulation > 10){
    		triangulation++;
    		finalValue = triangulation;
    	} else if (triangulation < 10){
    		triangulation--;
    		finalValue = triangulation;
    	} else {
    		finalValue = 50;
    	}
    	return finalValue;
    }
    public void decrementHealth()
    {
        lives--;
    }

    public int getLives()
    {
        return lives;
    }

    public int getSpeed()
    {
        return speed;
    }

    public int getUpdown()
    {
        return updown;
    }

}
