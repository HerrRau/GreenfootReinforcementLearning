import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class AutoGame extends AbstractGameWorld
{
    private Auto [] autos;
    AutoBackground [] backgrounds;

    int startX = 550;//-5; //macht Probleme bei Kurs 2
    int startY = 200;//+15;
    int startR = -90;

    protected int currentBackgroundNumber = 0;

    public AutoGame()
    {
        super(600, 400, 1); 

        backgrounds = new AutoBackground[4];
        backgrounds[0] = new AutoBackground("auto600x400composite.png", 16, 59, new Color(67,254,16)); // zweite Zahl ist Starthelligkeit mit maximaler Brightness
        backgrounds[1] = new AutoBackground("auto600x400composite_2TWEAKED2.png", 0, 134, new Color(67,254,16));
        backgrounds[2] = new AutoBackground("auto600x400composite_2NEU2.png", 0, (int)((254+72+72)/3), new Color(64,255,0));
        backgrounds[3] = new AutoBackground("grautest.png", 0, 255, new Color(64,255,0));

        chooseBackground(0);        
        setup();        
    }
    
    
    public void setup() {
        int bg = 0;
        Auto a = new AutoManuell();
        setAuto(a);
    
    }
    

    public Auto getAuto(int id) {
        return autos[id];
    }

    // convenience
    public void setAuto (Auto a) {        
        setAutos( new Auto[] { a } );
    }

    public void setAutos (Auto [] a) {
        if (autos!=null) {
            for (Auto au: autos) { 
                au.removedFromWorld();
                removeObject(au); 
            }
        }
        autos = a;
        for (int i=0; i<autos.length; i++) {
            addObject(autos[i], 0, 0);
            respawn(i);
            autos[i].setNumber(i);
        }
    }

    public void chooseBackground(int i) {
        currentBackgroundNumber = i;
        setBackground( new GreenfootImage( backgrounds[i].name ) );
        getBackground().setColor(Color.WHITE);
    }

    protected String getBackgroundString() {
        return backgrounds[currentBackgroundNumber].name;
    }

    protected int getMinimumBrightness() {
        return backgrounds[currentBackgroundNumber].minBrightness;
    }

    protected int getMaximumBrightness() {
        return backgrounds[currentBackgroundNumber].maxBrightness;
    }

    public Color getColorGrass() {
        return backgrounds[currentBackgroundNumber].grass;
    }

    public boolean isPossible(int x, int y) {
        // is off screen
        if (x<0 || y<0 || x>getWidth()-1 || y>getHeight()-1) return false;
        // is off track
        if (getBackground().getColorAt(x, y).equals(getColorGrass())) return false;
        // another car - experimental, makes it much harder
        if (false) {     
            for (Auto a: autos) {
                int x2 = a.getX();
                int y2 = a.getY();
                double mindestabstand = 3;
                if (Math.sqrt((x-x2)*(x-x2)+(y-y2)*(y-y2)) < mindestabstand) return false;
            }
        }
        return true;
    }

    public void respawn(int id) {
        autos[id].setRotation(startR);
        autos[id].setLocation(startX-4*2*id, startY-4*2*id);                
    }
}
