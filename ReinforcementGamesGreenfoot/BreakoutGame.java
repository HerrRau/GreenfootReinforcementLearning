import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

//public class MyWorld extends World
public class BreakoutGame extends AbstractGameWorld
{
    private int level = 0;
    KugelMove kugel;
    Schlaeger schlaeger;

    public BreakoutGame()
    {    
        //super(600, 400, 1); 
        super(360, 480, 1); 
        kugel = new KugelMove();
        addObject(kugel, 150, 150);
        setup();
    }
    
    public void setup() {
        schlaeger = new Schlaeger();
        addObject(schlaeger, 360/2, 480-20);        
    }
    
    public void erhoeheLevel()
    {
        level = level + 1;
        if (level==1)
        {
            addObject(new Hindernis(), 350, 50);
        }
        else if (level==2)
        {
            addObject(new Hindernis(), 350, 50);
            addObject(new Hindernis(), 150, 50);
            addObject(new Hindernis(), 550, 50);
        }
        else
        {
            addObject(new Hindernis(), Greenfoot.getRandomNumber(getWidth()), 50);
            addObject(new Hindernis(), Greenfoot.getRandomNumber(getWidth()), 50);
            addObject(new Hindernis(), Greenfoot.getRandomNumber(getWidth()), 50);
            addObject(new Hindernis(), Greenfoot.getRandomNumber(getWidth()), 50);
            addObject(new Hindernis(), Greenfoot.getRandomNumber(getWidth()), 50);
            addObject(new Hindernis(), Greenfoot.getRandomNumber(getWidth()), 50);
            addObject(new Hindernis(), Greenfoot.getRandomNumber(getWidth()), 50);
            addObject(new Hindernis(), Greenfoot.getRandomNumber(getWidth()), 50);
        }

    }
    
    public boolean istImAus(){
        return (kugel.getY()>this.getHeight()-3);
    }


    
}
