import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class SmoothAutoSensor extends SmoothAuto
{
    private int sensorLength = 25;
    private int numberOfSensors = 5;    
    private Pearl pearls[][];
    private boolean addSpeedToState = false;
    
    public SmoothAutoSensor() {
        super();        
    }
    
    public SmoothAutoSensor(int sensors) {
        super();        
        numberOfSensors = sensors;
    }
    
    public SmoothAutoSensor(int sensors, boolean withSpeed) {
        super();        
        numberOfSensors = sensors;
        addSpeedToState = withSpeed;
    }
    
    public String getType() {
        return "smooth_sensor_"+numberOfSensors;
    }

    @Override public void addedToWorld(World w) {
        super.addedToWorld(w);
        pearls = new Pearl [numberOfSensors][sensorLength];
        for (int i=0; i<numberOfSensors; i++) {
            for (int j=0; j<sensorLength; j++) {
                pearls[i][j] = new Pearl();
                w.addObject(pearls[i][j], getX(), getY());
            }
        }
        clearPearls();
    }
    
    @Override public String getState() {
        if (addSpeedToState) return getStatePearlsWithSpeed();
        else return getStatePearls();
    }

    private String getStateSimple() {
        return super.getState();
    }

    private String getStatePearlsWithSpeed() {
        return getStatePearls()+speedVorwaerts+":"; //####################
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
    
    public void removedFromWorld() {
        clearPearls();
    }

    private void clearPearls() {
        for (int i=0; i< pearls.length; i++) {
            for (Pearl p : pearls[i]) {
                p.getImage().setTransparency(0);
            }
        }
    }

    private int sensor(int degree, int sensor) {
        int pearlSpacing = 4;
        int x = getX();
        int y = getY();
        int r = getRotation() + degree;
        int newX;
        int newY;
        // for (int i=0; i<sensorLength; i++) {
        for (int i=1; i<sensorLength; i++) {
            newX = x + (int) (i*pearlSpacing * Math.cos(Math.toRadians(r)));
            newY = y + (int) (i*pearlSpacing * Math.sin(Math.toRadians(r)));
            //let world do checking
            if (!((AutoGame)getWorld()).isPossible(newX, newY)) return i;
            // //end of world
            // if (newX<0 || newY <0 || newX>=getWorld().getWidth() || newY>=getWorld().getHeight()) {
                // return i;
            // }
            // // color green
            // if (getWorld().getBackground().getColorAt(newX, newY).equals(green)) {
                // return i;
            // }    
            pearls[sensor][i].setLocation(newX, newY);
            pearls[sensor][i].getImage().setTransparency(255);
        }
        return sensorLength;
    }
    
    
    
    
}