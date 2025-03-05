import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class SnakeCalculator extends SnakeHead
{
    int dichteNord, dichteOst, dichteSued, dichteWest;
    
    public void act() 
    {
        dichteNord = berechneDichteNord();
        
        if (gibBlickrichtung()=='N') {
            if (dichteNord>dichteOst) {
                if (dichteNord<dichteWest) {
                    blickeNachWesten();
                }
            } else if (dichteOst>dichteWest) {
                if (dichteOst>dichteNord) {
                    blickeNachOsten();
                }
            }
        }
        else if (gibBlickrichtung()=='S') {
            if (dichteSued>dichteOst) {
                if (dichteSued<dichteWest) {
                    blickeNachWesten();
                }
            } else if (dichteOst>dichteWest) {
                if (dichteOst>dichteSued) {
                    blickeNachOsten();
                }
            }
        }        
        else if (gibBlickrichtung()=='O') {
            if (dichteOst>dichteSued) {
                if (dichteOst<dichteNord) {
                    blickeNachNorden();
                }
            } else if (dichteSued>dichteNord) {
                if (dichteSued>dichteOst) {
                    blickeNachSueden();
                }
            }
        }        
        else if (gibBlickrichtung()=='W') {
            if (dichteWest>dichteSued) {
                if (dichteWest<dichteNord) {
                    blickeNachNorden();
                }
            } else if (dichteSued>dichteNord) {
                if (dichteSued>dichteWest) {
                    blickeNachSueden();
                }
            }
        }        
        
        if (!istFreiVorn()) dreheNachLinks();
        if (!istFreiVorn()) dreheNachLinks();
        if (!istFreiVorn()) dreheNachLinks();
        
    }
    
    
    int berechneDichteNord() {
        return -1;
    }
    
    
}