import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class SnakeAutoMin extends SnakeHead
{
    public SnakeAutoMin() {
        super();
    }

    public void act() 
    {   
        int o = berechnePlatzOsten();
        int w = berechnePlatzWesten();
        int n = berechnePlatzNorden();
        int s = berechnePlatzSueden();

        if (gibBlickrichtung()=='N') {
            if (o<n && o>0) blickeNachOsten();
            if (w<n && w>0) blickeNachWesten();
        }
        else if (gibBlickrichtung()=='O') {
            if (n<o && n>0) blickeNachNorden();
            if (s<o && s>0) blickeNachSueden();
        }
        else if (gibBlickrichtung()=='S') {
            if (o<s && o>0) blickeNachOsten();
            if (w<s && w>0) blickeNachWesten();
        }
        else if (gibBlickrichtung()=='W') {
            if (n<w && n>0) blickeNachNorden();
            if (s<w && s>0) blickeNachSueden();
        }

        if (!istFreiVorn()) dreheNachRechts();
        if (!istFreiVorn()) dreheNachRechts();
        if (!istFreiVorn()) dreheNachRechts();
        if (!istFreiVorn()) dreheNachRechts();
        macheSchritt();
    }    

}