import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class SnakeAuto extends SnakeHead
{
    public SnakeAuto() {
        super();
    }

    public void act() 
    {   
        if (!istFreiVorn()) dreheNachRechts();            
        if (!istFreiVorn()) dreheNachRechts();            
        if (!istFreiVorn()) dreheNachRechts();            
        macheSchritt();
    }    



}