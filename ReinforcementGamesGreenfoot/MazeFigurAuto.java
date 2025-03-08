import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class MazeFigurAuto extends MazeFigur
{
    public char direction = 'N';
    public void act() 
    {     
        if (direction=='N') {
            if (istFreiNord()) geheNord();
            else direction='W';
        }
        else if (direction=='O') {
            if (istFreiOst()) geheOst();
            else direction='N';
        }
        else if (direction=='S') {
            if (istFreiSued()) geheSued();
            else direction='O';
        }
        else if (direction=='W') {
            if (istFreiWest()) geheWest();
            else direction='S';
        }
    }    

    public void bewegeDichUm(int x, int y) {
        int xNeu = getX()+x;
        int yNeu = getY()+y;
        if (grenzenlos)
        {
            if (xNeu<0) { xNeu = xNeu + getWorld().getWidth();  }
            if (yNeu<0) { yNeu = yNeu + getWorld().getHeight(); }
            if (xNeu>=getWorld().getWidth())  { xNeu = xNeu - getWorld().getWidth();  }
            if (yNeu>=getWorld().getHeight()) { yNeu = yNeu - getWorld().getHeight(); }
        }
        if (istFrei(xNeu, yNeu)) {
            setLocation(xNeu,yNeu);            
        // } else {
            // count++;
            // if (count==100) {
                // count = 0;
                // Greenfoot.playSound("ping.mp3");
            // }
        }
    }
    
}
