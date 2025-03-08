import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class MazeFigur extends MazeElement
{

    protected int geschwindigkeitX = 1;
    protected int geschwindigkeitY = 1;
    protected boolean grenzenlos = true;

    public MazeFigur() {
        grenzenlos = true;
    }

    protected void geheNord() { bewegeDichUm(0, -1);    }

    protected void geheOst() { bewegeDichUm(1, 0);    }

    protected void geheSued() { bewegeDichUm(0, 1);    }

    protected void geheWest() { bewegeDichUm(-1, 0);    }

    protected boolean istFreiNord() { return istFrei( getX(), getY()-1);    }

    protected boolean istFreiOst() { return istFrei(getX()+1, getY());    }

    protected boolean istFreiSued() { return istFrei(getX(), getY()+1);    }

    protected boolean istFreiWest() { return istFrei(getX()-1,getY());    }

    int count = 0;

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
        } else {
            count++;
            if (count==100) {
                count = 0;
                // Greenfoot.playSound("ping.mp3");
            }
        }
    }

    public void setzeGeschwindigkeitX(int x)
    {
        geschwindigkeitX = x;
    }

    public void setzeGeschwindigkeitY(int y)
    {
        geschwindigkeitX = y;
    }

    public boolean istFrei(int x, int y) {
        return getWorld().getObjectsAt(x,y,MazeBlock.class).size()==0;
    }

}