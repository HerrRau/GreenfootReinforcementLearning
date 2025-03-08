import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class MazeGame extends AbstractGameWorld
{
    MazeFigur player0;
    MazeFigur player1;

    public MazeGame() {
        this(15,11,50);
    }

    public MazeGame(int width, int height, int cellSize) {
        super(width,height,cellSize);
        GreenfootImage bg = new GreenfootImage(getWidth(),getHeight());
        bg.setColor(Color.BLACK);
        bg.fill();
        setBackground(bg);        
        setup();        
    }

    public void setup() {
        player0 = new MazeFigurLenkbar();
        player1 = new MazeFigurAuto();
        addObject(player0, 1,1);
        addObject(player1, getWidth()-2,getHeight()-2); // optional
        makeSpiral(0, 0, getWidth(), getHeight(), true, false);
        makeSpiral(2, 2, getWidth()-4, getHeight()-4, true, false);
        makeSpiral(4, 4, getWidth()-8, getHeight()-8, false, true);
    }

    protected void makeSpiral(int startX, int startY, int width, int height, boolean gapsSides, boolean gapsTop) {
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