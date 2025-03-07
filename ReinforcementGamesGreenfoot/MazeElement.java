import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class MazeElement extends Actor
{
        public void addedToWorld(World w) {
        GreenfootImage img = getImage();
        img.scale(w.getCellSize(), w.getCellSize());
        setImage(img);
    }

}