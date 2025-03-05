import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class SnakeAutoMax extends SnakeHead
{
    public SnakeAutoMax() {
        super();
    }

    public void act() 
    {   

        int o = berechnePlatzOsten();
        int w = berechnePlatzWesten();
        int n = berechnePlatzNorden();
        int s = berechnePlatzSueden();

        if (s>=o && s>=w && s>=n) blickeNachSueden();
        else if (n>=o && n>=w && n>=s) blickeNachNorden();
        else if (o>=s && o>=w && o>=n) blickeNachOsten();
        else if (w>=o && w>=s && w>=n) blickeNachWesten();
        
        macheSchritt();
    }    

}