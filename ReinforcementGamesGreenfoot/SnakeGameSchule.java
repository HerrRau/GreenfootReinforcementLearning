import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class SnakeGameSchule extends SnakeGame
{
    public void setup() {
        // setSnakes( new Karol() );
        setSnakes( new Karol(), new SnakeAuto());
    }
}