import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class MazeFigur extends MazeElement
{
<<<<<<< HEAD
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

=======

    private int geschwindigkeitX = 1;
    private int geschwindigkeitY = 1;
    private boolean grenzenlos = true;

    private String rechts;
    private String links;
    private String oben;
    private String unten;

    public MazeFigur()
    {
        setzeTastenASDW();
        grenzenlos = false;
    }

    public void MazeFigur(String r, String u, String l, String o)
    {
        setzeTasten(r, u, l, o);
    }

    public void setzeTasten(String r, String u, String l, String o)
    {
        rechts = r;
        links = l;
        oben = o;
        unten = u;
    }

    public void setzeTastenASDW()
    {
        rechts = "d";
        links = "a";
        oben = "w";
        unten = "s";
    }

    public void setzeTastenPfeile()
    {
        rechts = "right";
        links = "left";
        oben = "up";
        unten = "down";
    }    

>>>>>>> ea449d7397bd89cfcff4238967d09b82bbb4cbcd
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

<<<<<<< HEAD
    public void act() 
    {
    }    
=======
    
    public void ueberpruefeSteuerung(){

        if (Greenfoot.isKeyDown(oben))
        {
            bewegeDichUm(0,-geschwindigkeitY);
        }
        if (Greenfoot.isKeyDown(links))
        {
            bewegeDichUm(-geschwindigkeitX,0);
        }
        if (Greenfoot.isKeyDown(unten))
        {
            bewegeDichUm(0,geschwindigkeitY);
        }
        if (Greenfoot.isKeyDown(rechts))
        {
            bewegeDichUm(geschwindigkeitX,0);
        }
    }

    private void bewegeDichUm(int x, int y) {
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

    public void act() 
    {
        ueberpruefeSteuerung();
    }    

>>>>>>> ea449d7397bd89cfcff4238967d09b82bbb4cbcd
}