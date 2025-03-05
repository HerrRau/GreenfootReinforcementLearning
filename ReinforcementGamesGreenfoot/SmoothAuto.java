import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class SmoothAuto extends SmoothMoverSpecial
{

    public void act() {
        //part two
        try {
            ((AbstractGameWorld)getWorld()).showNextMoves(0); //###################
        }
        catch (Exception e) {
        }
    }
    
    public String getType() {
        return "smooth_simple_3";
    }

    public String getState() {
        return getX()/20+":"+getY()/20+":"+getRotation();
    }

    private void straight(int distance) {
        int startX =  getX();
        int startY = getY();
        for (int i=0; i<distance; i++) {
            move(1);
        }
        getWorld().getBackground().drawLine(startX, startY, getX(), getY());        
        getWorld().getBackground().fillOval(getX(), getY(),2,2);
    }
    
    public void straight() {
        straight(speedVorwaerts);
    }

    public void straightLeft() {
        turn(-drehenUm);
        straight(speedDrehung);
    }

    public void straightRight() {
        turn(drehenUm);
        straight(speedDrehung);
    }


    public void setColor(Color c) {
        getWorld().getBackground().setColor(c);
    }

}