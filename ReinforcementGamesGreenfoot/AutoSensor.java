import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class AutoSensor extends Auto
{
    private Color green;
    private int sensorLength = 25-10+10;
    private int numberOfSensors = 3+2;
    
    private Pearl pearls[][];
    
    public AutoSensor() {
        super();        
    }
    
    public AutoSensor(int i) {
        super();        
        numberOfSensors = i;
    }
    
    public String getType() {
        return "sensor_"+numberOfSensors;
    }

    @Override public void addedToWorld(World w) {
        super.addedToWorld(w);
        pearls = new Pearl [numberOfSensors][sensorLength];
        green = getWorld().getBackground().getColorAt(0,0);
        for (int i=0; i<numberOfSensors; i++) {
            for (int j=0; j<sensorLength; j++) {
                pearls[i][j] = new Pearl();
                w.addObject(pearls[i][j], getX(), getY());
            }
        }
        clearPearls();
    }
    
    private String getStateSimple() {
        return super.getState();
    }

    @Override public String getState() {
        return getStatePearls();
    }

    private String getStatePearls() {
        clearPearls();
        String temp = "";
        int degree = -numberOfSensors/2*60;
        for (int i=0; i<numberOfSensors; i++) {
            temp = temp + sensor(degree, i) + ":";
            degree = degree + 60;
        }
        return temp;
    }

    private void clearPearls() {
        for (int i=0; i< pearls.length; i++) {
            for (Pearl p : pearls[i]) {
                p.getImage().setTransparency(0);
            }
        }
    }

    public void removedFromWorld() {
        clearPearls();
    }

    private int sensor(int degree, int sensor) {
        int x = getX();
        int y = getY();
        int r = getRotation() + degree;
        int newX;
        int newY;
        for (int i=0; i<sensorLength; i++) {
            newX = x + (int) (i*4 * Math.cos(Math.toRadians(r)));
            newY = y + (int) (i*4 * Math.sin(Math.toRadians(r)));
            if (newX<0 || newY <0 || newX>=getWorld().getWidth() || newY>=getWorld().getHeight()) {
                return i;
            }
            if (getWorld().getBackground().getColorAt(newX, newY).equals(green)) {
                return i;
            }    
            //getWorld().getBackground().drawOval(newX, newY, 1, 1);
            pearls[sensor][i].setLocation(newX, newY);
            pearls[sensor][i].getImage().setTransparency(255);
        }
        //return -1;
        return sensorLength;
    }
    
    
}