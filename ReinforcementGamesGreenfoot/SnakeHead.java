import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class SnakeHead extends SnakeElement
{
    private static int anzahl;
    int laenge = 10;
    private Color farbe;
    private int nummer;
    private boolean showName = true;
    protected boolean verloren;         //um danach respawned zu werden
    protected boolean beStrict = true;    //Moeglichkeit, direkt nach hinten in die eigene Mauer zu fahren
    private boolean lasseSpur = true;
    private boolean ewigeSpur = false;
    private String name;

    public SnakeHead() {
        nummer = anzahl;
        farbe = getColor(nummer);
        setImage(farbe.darker());
        anzahl = anzahl + 1;
        name = getClass().getSimpleName();
    }

    public final void setzeNummer(int i) {
        nummer = i;
        farbe = getColor(i);
        setImage(farbe.darker());
    }

    public final int gibNummer() {
        return nummer;
    }

    public final int gibLaenge() {
        return laenge;
    }

    public final Color gibFarbe() {
        return farbe;
    }

    public final String gibName() { 
        return name;
    }

    public final void setzeName(String s) {
        name = s;
    }

    public final void setzeVerloren(boolean b) {
        verloren = b;
    }

    public final boolean gibVerloren() {
        return verloren;
    }

    protected final void setzeLasseSpur(boolean b) {
        lasseSpur = b;
    }

    protected final void setzeEwigeSpur(boolean b) {
        ewigeSpur = b;
    }

    //## muss ueberschrieben werden
    public void act()
    {        
    }

    //## Raender-Abfrage

    private final boolean stoesstRandWestenAn()
    {
        return getX()==0 && getRotation()==180;
    }

    private final boolean stoesstRandOstenAn()
    {
        return getX()==getWorld().getWidth()-1 && getRotation()==0;
    }

    private final boolean stoesstRandNordenAn()
    {
        return getY()==0 && getRotation()==270;
    }

    private final boolean stoesstRandSuedenAn()
    {
        return getY()==getWorld().getHeight()-1 && getRotation()==90;
    }

    private final void springeNachOsten()
    {
        setLocation(getWorld().getWidth()-1, getY());
    }

    private final void springeNachWesten()
    {
        setLocation(0, getY());
    }

    private final void springeNachNorden()
    {
        setLocation(getX(), getWorld().getHeight()-1 );
    }

    private final void springeNachSueden()
    {
        setLocation(getX(), 0);
    }        

    //## setze Richtung

    public final void blickeNachOsten()
    {
        if(beStrict || getRotation()!=180) { 
            setRotation(0);
        }        
    }

    public final void blickeNachWesten()
    {
        if(beStrict || getRotation()!=0) {
            setRotation(180);
        }
    }

    public final void blickeNachNorden()
    {
        if(beStrict || getRotation()!=90) {
            setRotation(270);
        }
    }

    public final void blickeNachSueden()
    {
        if(beStrict || getRotation()!=270) {
            setRotation(90);
        }
    }

    public final void dreheNachRechts() {
        setRotation( (getRotation()+90)%360);
    }

    public final void dreheNachLinks() {
        setRotation( (getRotation()+360-90)%360);
    }

    //## Sensor

    public final char gibBlickrichtung() {
        if (getRotation()==0) return 'O';
        else if (getRotation()==90) return 'S';
        else if (getRotation()==180) return 'W';
        else return 'N';
    }

    //##
    int gibBelegung(int x, int y) {
        if (!istWeltGrenzenlos()) {
            if (x<0 || x>= gibWeltBreite() || y<0 || y>= gibWeltHoehe()) {
                return Integer.MAX_VALUE; //# OUTSIDE  
            }
        }
        java.util.List actors = getWorld().getObjectsAt(x%gibWeltBreite(), y%gibWeltHoehe(), Actor.class);
        if (actors==null) return Integer.MIN_VALUE; //# EMPTY
        if (actors.get(0) instanceof SnakeBodyPart) {
            return ( (SnakeBodyPart) actors.get(0)).besitzer; //# BODYPART
        }
        if (actors.get(0) instanceof SnakeHead) {
            return ( (SnakeHead) actors.get(0)).nummer; //# HEAD
        }
        return -1; //# default, impossible to reach

    }

    public final boolean istFrei(int x, int y) {
        if (!istWeltGrenzenlos()) {
            if (x<0 || x>= gibWeltBreite() || y<0 || y>= gibWeltHoehe()) {
                return false;     
            }
        }
        return getWorld().getObjectsAt(x%gibWeltBreite(), y%gibWeltHoehe(), SnakeElement.class).isEmpty();
    }

    boolean istFreiVorn() {
        if (getRotation()==0) return istFreiOsten();
        if (getRotation()==180) return istFreiWesten();
        if (getRotation()==90) return istFreiSueden();
        if (getRotation()==270) return istFreiNorden();
        return true;
    }

    boolean istFreiOsten() {
        return istFrei(getX()+1, getY());
    }

    boolean istFreiWesten() {
        return istFrei(getX()-1, getY());
    }

    boolean istFreiNorden() {
        return istFrei(getX(), getY()-1);
    }

    boolean istFreiSueden() {
        return istFrei(getX(), getY()+1);
    }

    int berechnePlatzWesten() { 
        int erg = 0;
        if (!istWeltGrenzenlos()) {
            for (int i=getX()-1; i>=0; i--) {
                if (!istFrei(i, getY())) {
                    return erg;
                } else {
                    erg++;
                }
            }  
            return erg;
        } else {
            for (int i=getX()-1; i>=0; i--) {
                if (!istFrei(i, getY())) {
                    return erg;
                } else {
                    erg++;
                }
            }            
            for (int i=gibWeltBreite()-1; i>getX(); i--) {
                if (!istFrei(i, getY())) {
                    return erg;
                } else {
                    erg++;
                }
            }     
            return erg;
        }
    }

    int berechnePlatzNorden() { 
        int erg = 0;
        if (!istWeltGrenzenlos()) {
            for (int i=getY()-1; i>=0; i--) {
                if (!istFrei(getX(), i)) {
                    return erg;
                } else {
                    erg++;
                }
            }  
            return erg;
        } else {
            for (int i=getY()-1; i>=0; i--) {
                if (!istFrei(getX(), i)) {
                    return erg;
                } else {
                    erg++;
                }
            }            
            for (int i=gibWeltHoehe()-1; i>getY(); i--) {
                if (!istFrei(getX(), i)) {
                    return erg;
                } else {
                    erg++;
                }
            }     
            return erg;
        }
    }

    int berechnePlatzOsten() { 
        int erg = 0;
        if (!istWeltGrenzenlos()) {
            for (int i=getX()+1; i<gibWeltBreite(); i++) {
                if (!istFrei(i, getY())) {
                    return erg;
                } else {
                    erg++;
                }
            }  
            return erg;
        } else {
            for (int i=getX()+1; i<gibWeltBreite(); i++) {
                if (!istFrei(i, getY())) {
                    return erg;
                } else {
                    erg++;
                }
            }            
            for (int i=0; i<getX()-1; i++) {
                if (!istFrei(i, getY())) {
                    return erg;
                } else {
                    erg++;
                }
            }     
            return erg;
        }
    }

    int berechnePlatzSueden() { 
        int erg = 0;
        if (!istWeltGrenzenlos()) {
            for (int i=getY()+1; i<gibWeltHoehe(); i++) {
                if (!istFrei(getX(), i)) {
                    return erg;
                } else {
                    erg++;
                }
            }  
            return erg;
        } else {
            for (int i=getY()+1; i<gibWeltHoehe(); i++) {
                if (!istFrei(getX(), i)) {
                    return erg;
                } else {
                    erg++;
                }
            }            
            for (int i=0; i<getY()-1; i++) {
                if (!istFrei(getX(), i)) {
                    return erg;
                } else {
                    erg++;
                }
            }     
            return erg;
        }
    }

    //## Wachstum
    protected void erzeugeBodyPart()
    {
        if (!lasseSpur) return;
        if (ewigeSpur) getWorld().addObject(new SnakeBodyPart(Integer.MAX_VALUE, farbe.brighter(), nummer), getX(),getY());
        else getWorld().addObject(new SnakeBodyPart(laenge, farbe.brighter(), nummer), getX(),getY());
    }

    private final void erhoeheLaenge()
    {
        laenge = laenge + 1;
    }

    //##
    public void entferneText() {
        gibWelt().showText("", getX()+1, getY()+1);
    }

    public void zeigeText(String s) {
        gibWelt().showText(s+" "+this.gibName(), getX()+1, getY()+1);
    }

    protected final void verliereSpiel()
    {
        verloren = true;
        if (showName) entferneText();
        gibWelt().verliereSpiel(this);
    }

    //## 
    private void ueberpruefeKollision() 
    {
        // 1. Wenn Futter beruehrt, dann: loesche Futter, erhoehe Laenge, erzeuge zufaellig irgendwo neues Futterobjekt
        // 2. Wenn SnakeElement berueht, dann: Greenfoot.setWorld(new GameOver("Verloren!"));

        if (isTouching(Futter.class))
        {
            removeTouching(Futter.class);
            erhoeheLaenge();
            erzeugeZufaelligFutter();
        }
        if (isTouching(SnakeElement.class))
        {
            verliereSpiel();
        }

    }

    //## 

    private final void erzeugeZufaelligFutter()
    {
        getWorld().addObject( new Apfel(), Greenfoot.getRandomNumber(getWorld().getWidth()), Greenfoot.getRandomNumber(getWorld().getHeight()) );
    }

    //##

    public final void macheSchritt()
    {        
        entferneText();//if (showName) gibWelt().showText("", getX()+1, getY()+1);
        erzeugeBodyPart();
        if (istWeltGrenzenlos()) {
            if      (stoesstRandWestenAn())  { springeNachOsten();  }
            else if (stoesstRandOstenAn())   { springeNachWesten(); }
            else if (stoesstRandNordenAn())  { springeNachNorden(); }
            else if (stoesstRandSuedenAn())  { springeNachSueden(); }
            else                         { 
                move(1); 
            }
        } else {
            if      (stoesstRandWestenAn())  { verliereSpiel(); }
            else if (stoesstRandOstenAn())   { verliereSpiel(); }
            else if (stoesstRandNordenAn())  { verliereSpiel(); }
            else if (stoesstRandSuedenAn())  { verliereSpiel(); }
            else                         { 
                move(1); 
            }            
        }
        if (getWorld()==null) return;
        zeigeText(""+nummer);//if (showName) gibWelt().showText(""+nummer, getX()+1, getY()+1);
        ueberpruefeKollision();
        erhoeheLaenge();
    }

}
