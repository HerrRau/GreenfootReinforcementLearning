import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

public class SnakeGame extends AbstractGameWorld
{
    private SnakeHead [] snakes;
    private boolean boundlessWorld = false;
    boolean removeDeadBoyPartsInstantly = true;

    /**
     * The constructor calls setup, which initializes the snakes array, and adds all snakes in array 
     * to the game, at random locations and facing in random directions.
     */
    public SnakeGame()
    {    
        super(60/2, 40/2, 15*2); 
        setPaintOrder( SnakeHead.class, SnakeBodyPart.class); //# for Karol        
        setup();
    }

    /**
     * Fills the snakes array from the method createSnakes. This method is meant to be overridden by subclasses,
     * which will *not* add these snakes, but snakes of their own.
     */
    protected void setup() {
        setSnakes( createSnakes() );
    }

    /**
     * Sets the snakes which will take part in the game.
     * @param s The participating snakes, can be a single SnakeHead, several SnakeHeads, or an array of SnakeHeads.
     */
    protected final void setSnakes(SnakeHead... s) {
        removeObjects(getObjects(SnakeHead.class));
        removeObjects(getObjects(SnakeBodyPart.class));
        snakes = s;
        for(int i = 0; i<snakes.length; i++) {
            addObject(snakes[i], Greenfoot.getRandomNumber(getWidth()), Greenfoot.getRandomNumber(getHeight()));
            snakes[i].setRotation(90*Greenfoot.getRandomNumber(4));
            snakes[i].setzeNummer(i);
        }
    }

    /**
     * Getter method for participating snakes.
     * @return An array of SnakeHeads.
     */
    protected final SnakeHead[] getSnakes() {
        return snakes;
    }

    /**
     * Creates an array of preset snake participants.
     * @return An array of SnakeHeads.
     */
    private SnakeHead [] createSnakes() {
        ArrayList<SnakeHead> snakes = new ArrayList();
        snakes.add ( new SnakeManuell() );
        snakes.add ( new SnakeAutoSpiraler() );
        snakes.add ( new SnakeAutoMax() );
        snakes.add ( new SnakeAutoLinki() );
        snakes.add ( new SnakeAuto() );
        snakes.add ( new SnakeAuto2() );
        SnakeHead [] snakes2 = new SnakeHead[snakes.size()];
        snakes.toArray(snakes2);
        return snakes2;
    }

    /**
     * Removes all body parts from a specific snake. The snake head remains in the game. 
     * This does not set the length of the snake back or places it in a new position.
     * @param The number of the snake in question.
     */
    protected void removeBodyParts(int nummer) {
        for (SnakeBodyPart p : getObjects(SnakeBodyPart.class)) {
            if (p.besitzer==nummer) removeObject(p);
        }
    }

    /**
     * Is called when a snake head decides it has lost a game, usually by hitting an occupied square.
     * Depending on the settings, all body parts of the snake will be removed instantly or left to
     * decay in their own time; if the last snake has left the arena, and end game screen is shown. 
     * @param s The snake in question.
     */
    public void verliereSpiel(SnakeHead s) {
        if (removeDeadBoyPartsInstantly) removeBodyParts(s.gibNummer());
        removeObject(s);
        if (getObjects(SnakeHead.class).isEmpty()) {
            Greenfoot.setWorld(new GameOver(s.gibNummer()+": Letzter! "+s.getClass().getSimpleName()));   
        }
    }

    /**
     * Getter method to ask whether the game world has borders or not.
     * @return Returns true if the world is limitless, false is the world has borders.
     */
    public boolean getBoundlessWorld() {
        return boundlessWorld;
    }

    /**
     * Setter method to set whether the game world has borders or not.
     * @param b Sets the corresponding attribute and makes the world limitless or not.
     */
    public void setBoundlessWorld(boolean b) {
        boundlessWorld =b;
    }

}
