import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class Karol extends SnakeHead
{
    String imageN = "robot2.gif";
    String imageO = "robot3.gif";
    String imageS = "robot0.gif";
    String imageW = "robot1.gif";
    GreenfootImage imgN, imgS, imgO, imgW;

    @Override public final void addedToWorld(World w) {
        super.addedToWorld(w);
        int sizeX = getWorld().getCellSize();
        int sizeY = getWorld().getCellSize();
        imgN = new GreenfootImage(imageN);
        imgO = new GreenfootImage(imageO);
        imgS = new GreenfootImage(imageS);
        imgW = new GreenfootImage(imageW);
        imgN.scale(sizeX, sizeY);
        imgO.scale(sizeX, sizeY);
        imgS.scale(sizeX, sizeY);
        imgW.scale(sizeX, sizeY);
        imgN.rotate(90);
        imgS.rotate(270);
        imgW.rotate(180);
        imgO.rotate(0);
        setRotation(0);
        setzeLasseSpur(false);
    }

    @Override public final void setRotation(int degree) {
        super.setRotation(degree);
        if (gibBlickrichtung()=='N') setImage(imgN);
        else if (gibBlickrichtung()=='O') setImage(imgO);
        else if (gibBlickrichtung()=='S') setImage(imgS);
        else if (gibBlickrichtung()=='W') setImage(imgW);
    }
    
    protected void legeHin() {
        getWorld().addObject(new SnakeBodyPart(Integer.MAX_VALUE, gibFarbe().brighter(), gibNummer()), getX(),getY());        
    }

    @Override public void act() 
    {
        setzeLasseSpur(true); 
        if (!istFreiVorn()) {
            dreheNachRechts();            
        }        
        macheSchritt();
    }
}    
