import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class MazeFigur extends MazeElement
{
    protected int geschwindigkeitX = 1;
    protected int geschwindigkeitY = 1;
    protected boolean grenzenlos = true;

    public MazeFigur() {
        grenzenlos = true;
    }

    public void bewegeDichUm(int x, int y) {
        int xNeu = getX()+x;
        int yNeu = getY()+y;
        if (grenzenlos)
        {
            if (xNeu<0) { xNeu = xNeu + getWorld().getWidth();  }
            if (yNeu<0) { yNeu = yNeu + getWorld().getHeight(); }
            if (xNeu>=getWorld().getWidth())  { xNeu = xNeu - getWorld().getWidth();  }
            if (yNeu>=getWorld().getHeight()) { yNeu = yNeu - getWorld().getHeight(); }
        }
        if (istFrei(xNeu, yNeu)) {
            setLocation(xNeu,yNeu);            
        }
    }

    public void setzeGeschwindigkeitX(int x)
    {
        geschwindigkeitX = x;
    }

    public void setzeGeschwindigkeitY(int y)
    {
        geschwindigkeitX = y;
    }

    public boolean istFrei(int x, int y) {
        return getWorld().getObjectsAt(x,y,MazeElement.class).size()==0;
    }

    public void act() 
    {
    }    
}