import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class KugelMove extends Kugel
{
    private int maxSpeed = 3;
    private int speed = 1;


    public KugelMove()    {        
        setRotation(45);
    }

    //fuer KI
    public int getSpeed() 
    {
        return speed;
    }

    // fuer KI
    public boolean beruehrtSchlaeger() {
        return isTouching(Schlaeger.class);
    }

    public void bewege()
    {
        move(speed);
    }

    public void kollidiereVertikal()
    {
        setRotation(180-getRotation());
    }

    public void kollidiereHorizontal()
    {
        setRotation(360-getRotation());
    }

    public void kollidiereSpitz()
    {
        setRotation(180+getRotation());
    }

    public void kollidiereMitSchlaegerEXPERIMENTELL() {
        int x = gibBeruehrtenSchlaeger().getX();
        int y = gibBeruehrtenSchlaeger().getY();
        turnTowards(x, y);
        turn(180);
        move(10);
        setLocation(getX(), getY()-20);
    }

    public void kollidiereMitSchlaeger()
    {        
        int abstandX = Math.abs(getX()-gibBeruehrtenSchlaeger().getX());        
        int abstandY = Math.abs(getX()-gibBeruehrtenSchlaeger().getY());        
        if (abstandX >= (getImage().getWidth()+gibBeruehrtenSchlaeger().getImage().getWidth())/2)
        {
            kollidiereSpitz();
            move(1+2-2);
        } 
        // else if (abstandX >= (getImage().getWidth()+gibBeruehrtenSchlaeger().getImage().getWidth())/2)
        // {
        // kollidiereVertikal();
        // move(1+2);
        // } 
        else
        {
            kollidiereHorizontal();
            move(1+2+3-5+1);
        }

        

        // TEST KI
        setLocation(getX(), getY()-20);
    }

    public void kollidiereMitHindernis()
    {
        int abstand = Math.abs(getX()-gibBeruehrtesHindernis().getX());        
        if (abstand >= (getImage().getWidth()+gibBeruehrtesHindernis().getImage().getWidth())/2)
        {
            kollidiereVertikal();
            move(1);
        } 
        else
        {
            kollidiereHorizontal();
            move(1);
        }

    }

}
