import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class SnakeAuto2 extends SnakeHead
{
    boolean links;
    boolean gekurvt;
    public SnakeAuto2() {
        super();
    }

    public void act() 
    {   
        if (!istFreiVorn()) kurve();            
        if (!istFreiVorn()) kurve();            
        if (!istFreiVorn()) kurve();            

        if (gekurvt) {
            gekurvt = false;
            links = !links;
        }
        macheSchritt();
    }    

    void kurve() {
        gekurvt = true;
        if (links) dreheNachLinks();
        else dreheNachRechts();
    }
}