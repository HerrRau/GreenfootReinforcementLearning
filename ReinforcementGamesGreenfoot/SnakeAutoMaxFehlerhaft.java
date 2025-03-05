import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class SnakeAutoMaxFehlerhaft extends SnakeHead
{

    public void act() 
    {   

        int o = berechnePlatzOsten();
        int w = berechnePlatzWesten();
        int n = berechnePlatzNorden();
        int s = berechnePlatzSueden();

        if (s>o && s>w && s> n) blickeNachSueden();
        if (n>o && n>w && n> s) blickeNachNorden();
        if (o>s && o>w && o> n) blickeNachOsten();
        if (w>o && w>s && w> n) blickeNachWesten();

        macheSchritt();
    }    

}