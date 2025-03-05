import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class SnakeBodyPart extends SnakeElement
{
    int besitzer;
    int lebenszeit;

    public SnakeBodyPart(int lebenszeit)
    {
        this.lebenszeit = lebenszeit;
    }

    public SnakeBodyPart(int lebenszeit, Color c, int besitzer)
    {
        this.lebenszeit = lebenszeit;
        setImage(c);        
        this.besitzer = besitzer;
    }

    public void act()
    {        
        reduziereLebenszeit();
        if (lebenszeit==0) 
        {
            loescheDich();            
        }
    }

    public void reduziereLebenszeit()
    {
        lebenszeit = lebenszeit - 1;
    }

    public void loescheDich()
    {
        getWorld().removeObject(this);   
    }

}
