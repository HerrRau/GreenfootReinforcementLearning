import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class SnakeGameElement extends Actor
{
   public void addedToWorld(World w)
    {
        GreenfootImage img = new GreenfootImage( getImage() );
        img.scale(w.getCellSize(), w.getCellSize());
        setImage(img);
    }
}
