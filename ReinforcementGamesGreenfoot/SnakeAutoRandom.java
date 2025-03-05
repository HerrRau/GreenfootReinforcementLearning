import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

/**
 * Ergänzen Sie hier eine Beschreibung für die Klasse SnakeAutoRandom.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class SnakeAutoRandom extends SnakeHead
{
    public void act() 
    {

        boolean okay = false;
        for (int i=0; i<4; i++) {
            int r=Greenfoot.getRandomNumber(4);
            if (r==0) dreheNachRechts();
            else if (r==1) dreheNachLinks();
            //if (istFreiVorn()) break;
        }
        macheSchritt();

    
    
    
    
    
    }    
}