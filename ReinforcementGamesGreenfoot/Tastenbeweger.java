import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public abstract class Tastenbeweger extends BreakoutElement
{
    boolean useWrapAround = false;
    int speed = 1;

    public void act() 
    {
        ueberpruefeSteuerung();
    }    

    public void ueberpruefeSteuerung(){
        if (Greenfoot.isKeyDown("space"))
        {
            setLocation((int) (Math.random()*getWorld().getWidth()), getY());
            return;
        }
        if (Greenfoot.isKeyDown("up"))
        {
            return;
        }
        if (Greenfoot.isKeyDown("left"))
        {
            return;
        }
        if (Greenfoot.isKeyDown("down"))
        {
            return;
        }
        if (Greenfoot.isKeyDown("right"))
        {
            return;
        }
        if (Greenfoot.isKeyDown("w"))
        {
            geheNachOben();
        }
        if (Greenfoot.isKeyDown("a"))
        {
            geheNachLinks();
        }
        if (Greenfoot.isKeyDown("s"))
        {
            geheNachUnten();
        }
        if (Greenfoot.isKeyDown("d"))
        {
            geheNachRechts();
        }
    }

    protected void geheNachRechts() {
        bewegeDichUm(speed,0);
    }

    protected void geheNachLinks() {
        bewegeDichUm(-speed,0);
    }

    protected void geheNachOben() {
        // bewegeDichUm(0,-1);
    }

    protected void geheNachUnten() {
        // bewegeDichUm(0,1);
    }

    private void bewegeDichUm(int x, int y) {
        int xNeu = getX()+x;
        int yNeu = getY()+y;
        if (useWrapAround)
        {
            if (xNeu<0) { xNeu = xNeu + getWorld().getWidth();  }
            if (yNeu<0) { yNeu = yNeu + getWorld().getHeight(); }
            if (xNeu>=getWorld().getWidth())  { xNeu = xNeu - getWorld().getWidth();  }
            if (yNeu>=getWorld().getHeight()) { yNeu = yNeu - getWorld().getHeight(); }
        }
        setLocation(xNeu,yNeu);
    }

}
