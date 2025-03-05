import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Hindernis extends BreakoutElement
{
    private int leben;

    private void reduziereLeben()
    {
        setzeLeben(leben = leben - 1);
    }

    public Hindernis()
    {
        setzeLeben(3);
    }

    public Hindernis(int i)
    {
        setzeLeben(i);
    }

    private void setzeLeben(int i)
    {
        leben = i;
        if (leben==0)      getImage().setColor(Color.YELLOW);
        else if (leben==1) getImage().setColor(Color.RED);
        else if (leben==2) getImage().setColor(Color.ORANGE);
        else if (leben==3) getImage().setColor(Color.GREEN);
        else               getImage().setColor(Color.BLUE);
        getImage().fill();
    }

    public void act()
    {
        if (leben==0) {
            if(Greenfoot.getRandomNumber(2)==0)
            {
                getWorld().addObject(new Belohnung1(), getX(), getY());
            } 
            else
            {
                getWorld().addObject(new Belohnung2(), getX(), getY());                
            }
            getWorld().removeObject(this);   
        }
        else if (isTouching(Kugel.class)) 
        {
            reduziereLeben();
        }
    }
}
