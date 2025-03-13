import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

/**
 * Diese Klasse wuerde man verwenden, um automatische Snake-Bots gegeneinander spielen zu lassen.
 * Es geht hier also gar nicht um KI.
 * Die teilnehmenden Bots muessen eine act-Methode haben, in der genau einmal die Methode 
 * macheSchritt() aufgerufen wird.
 */

public class SnakeGameSchule extends SnakeGame
{
    public void setup() {
        setSnakes( new Karol(), new SnakeAuto());
    }
}