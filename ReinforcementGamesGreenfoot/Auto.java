import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class Auto extends AutoElement
// public class Auto extends SmoothMover
{
    //
    protected int lastBrightness = 256;

    //
    protected int drehenUm = 30;
    protected int speedVorwaerts = 15;//+10 0 25 wird besser!;
    protected int speedVorwaertsMaximum = 15;//+10 0 25 wird besser!;
    protected int speedVorwaertsMinium = 5;//+10 0 25 wird besser!;
    protected int speedDrehung = 5;

    @Override public void addedToWorld(World w) {
        getImage().scale(20*2, 10*2);
        setNumber(0);
    }

    //to be overwritten
    public void removedFromWorld() {
    }

    public void setNumber(int id) {
        GreenfootImage imgText = new GreenfootImage(""+id, 12*2, Color.MAGENTA, Color.WHITE);
        imgText.rotate(90);
        getImage().drawImage(imgText, getImage().getWidth()/4, -1);
    }

    public String getState() {
        return getX()/20+":"+getY()/20+":"+getRotation();
    }

    public String getType() {
        return "simple_3";
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

    public void accelerate() {
        if (speedVorwaerts<speedVorwaertsMaximum) speedVorwaerts += 3;
        straight();
    }

    public void decelerate() {
        if (speedVorwaerts>speedVorwaertsMinium) speedVorwaerts -= 3;
        straight();
    }

    public void setColor(Color c) {
        getWorld().getBackground().setColor(c);
    }

}