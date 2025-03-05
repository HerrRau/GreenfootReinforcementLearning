import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot und MouseInfo)

public class SnakeAutoSpiraler extends SnakeHead
{
    char last;
    boolean other;

    public void act() 
    {   

        int o = berechnePlatzOsten();
        int w = berechnePlatzWesten();
        int n = berechnePlatzNorden();
        int s = berechnePlatzSueden();

        if (other) {
            other = !other;
            if (last=='S' && istFreiOsten()) {
                blickeNachOsten();
            }
            else if (last=='W' && istFreiSueden()) {
                blickeNachSueden();
            }
            else if (last=='N' && istFreiWesten()) {
                blickeNachWesten();
            }
            else if (last=='O' && istFreiNorden()) {
                blickeNachNorden();
            }
            
            if(!istFreiVorn()) dreheNachLinks();
            if(!istFreiVorn()) dreheNachLinks();
            if(!istFreiVorn()) dreheNachLinks();
            
            return;
        }

        if (s>o && s>w && s> n) {
            blickeNachSueden();
            last = 'S';
        }
        if (n>o && n>w && n> s) {
            blickeNachNorden();
            last = 'N';
        }
        if (o>s && o>w && o> n) {
            blickeNachOsten();
            last = 'O';
        }
        if (w>o && w>s && w> n) {
            blickeNachWesten();
            last = 'W';
        }
        macheSchritt();
    }    

}