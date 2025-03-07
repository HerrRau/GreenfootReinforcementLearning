import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class MazeFigurGesteuert extends MazeFigur
{

    private String rechts;
    private String links;
    private String oben;
    private String unten;

    public MazeFigurGesteuert()
    {
        setzeTastenASDW();
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


    public void act() 
    {
        ueberpruefeSteuerung();
    }    

}