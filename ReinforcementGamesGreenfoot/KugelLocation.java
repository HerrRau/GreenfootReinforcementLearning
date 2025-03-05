import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class KugelLocation extends Kugel
{
    private int deltaX;
    private int deltaY;
    private int maxSpeed = 3;
    
    public KugelLocation()
    {
        deltaX = 1;
        deltaY = 1;
    }

    public void bewege()
    {
        setLocation(getX()+deltaX, getY()+deltaY);
    }

    public void kollidiereVertikal()
    {
        deltaX = -deltaX;
    }

    public void kollidiereHorizontal()
    {
        deltaY = -deltaY;
    }

    public void kollidiereMitSchlaeger()
    {
        if (true)
        {
            int abstand = getX()-getOneIntersectingObject(Schlaeger.class).getX();
            if (abstand >10) abstand = 10;
            if (abstand <-10) abstand = -10;
            deltaX = deltaX + abstand/10;
            if (deltaX>2) deltaX=2;
            if (deltaX<-2) deltaX=-2;
            //deltaX = deltaX + abstand/(10/2)/2;
        }
        kollidiereHorizontal();
        setLocation( getX(), getY() + deltaY );
    }

    public void kollidiereMitSchlaeger2()
    {
        if (true)
        {
            int abstand = getX() - gibBeruehrtenSchlaeger().getX();
            int grenze = gibBeruehrtenSchlaeger().getImage().getWidth()/4;
            getWorld().showText(""+abstand+"/"+grenze, 100, 100);
            if (Math.abs(abstand)>grenze) {
                deltaX = deltaX + abstand/grenze;
                if (deltaX>maxSpeed) deltaX=maxSpeed;
                if (deltaX<-maxSpeed) deltaX=-maxSpeed;
            }
        }
        kollidiereHorizontal();
        setLocation(getX(), getY() + deltaY);

    }

    public void kollidiereMitHindernis()
    {
        int abstand = Math.abs(getX()-gibBeruehrtesHindernis().getX());        
        if (abstand >= (getImage().getWidth()+gibBeruehrtesHindernis().getImage().getWidth())/2)
        {
            kollidiereVertikal();
            setLocation(getX() + 2*deltaX, getY());
        } 
        else
        {
            kollidiereHorizontal();
            setLocation(getX(), getY() + 2*deltaY);
        }

    }

}
