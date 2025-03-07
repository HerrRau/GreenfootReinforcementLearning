import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class MazeGame extends AbstractGameWorld
{

    public MazeGame() {
        super(50,40,20);
        GreenfootImage bg = new GreenfootImage(50,40);
        bg.setColor(Color.BLACK);
        bg.fill();
        setBackground(bg);        
        setup();        
    }
    
    public void setup() {
        addObject( new MazeFigur(), 1,1);
        setLevel();
    }
    
    protected void setLevel() {
        
    }

}