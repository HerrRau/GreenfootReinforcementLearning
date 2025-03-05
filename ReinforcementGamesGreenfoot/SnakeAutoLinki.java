import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class SnakeAutoLinki extends SnakeHead
{
    public void act()     
    {
        dreheNachLinks();
        if (istFreiVorn()) macheSchritt();
        else {
            dreheNachRechts();
            if (istFreiVorn()) macheSchritt();
            else {
                dreheNachRechts();
                macheSchritt();
            }
        }
    }    
}