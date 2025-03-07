import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class MazeGame extends AbstractGameWorld
{
    MazeFigur player0;
    MazeFigur player1;

    public MazeGame() {
        super(15,11,50);
        GreenfootImage bg = new GreenfootImage(50,40);
        bg.setColor(Color.BLACK);
        bg.fill();
        setBackground(bg);        
        setup();        
    }

    public void setup() {
        player0 = new MazeFigur();
        player1 = new MazeFigurGesteuert();
        addObject(player0, 1,1);
        //addObject(player1, getWidth()-2,getHeight()-2);
        makeSpiral(0, 0, getWidth(), getHeight(), true, false);
        makeSpiral(2, 2, getWidth()-4, getHeight()-4, true, false);
        makeSpiral(4, 4, getWidth()-8, getHeight()-8, false, true);
        
    }

    private void makeSpiral(int startX, int startY, int width, int height, boolean gapsSides, boolean gapsTop) {
        for (int x=startX; x<startX+width; x++) {
            addObject( new MazeBlock(), x, startY); 
            addObject( new MazeBlock(), x, startY+height-1); 
        }
        for (int y=startY; y<startY+height; y++) {
                addObject( new MazeBlock(), startX, y); 
                addObject( new MazeBlock(), startX+width-1, y); 
        }
        if (gapsTop)  {
            removeObjects ( getObjectsAt(startX+width/2, startY, MazeBlock.class));
            removeObjects ( getObjectsAt(startX+width/2, startY+height-1, MazeBlock.class));
        }      
        if (gapsSides)  {
            removeObjects ( getObjectsAt(startX, startY+height/2, MazeBlock.class));
            removeObjects ( getObjectsAt(startX+width-1, startY+height/2, MazeBlock.class));
        }      
 
    }
}