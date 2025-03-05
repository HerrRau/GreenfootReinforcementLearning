import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class AnzeigeNextMoves extends Actor
{
    public void act() 
    {
        ((AbstractGameWorld)getWorld()).showNextMoves(0);
    }    

    @Override
    public void addedToWorld(World w) {
        GreenfootImage img = new GreenfootImage(1,1);
        setImage(img);
    }
}