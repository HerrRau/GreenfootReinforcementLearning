import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class Pearl extends AutoElement
{
    GreenfootImage background = new GreenfootImage("pearl.png");
    
    public Pearl() {
        setImage(background);
    }
    
    public void addedToWorld(World w) {
        getImage().scale(4,4);
    }
}