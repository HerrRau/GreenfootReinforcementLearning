import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class SnakeElement extends SnakeGameElement
{
    public final void setImage(Color farbe) {
        if (getWorld()==null) {
            GreenfootImage image = new GreenfootImage(10,10); //## cell size 10, will be overwritten later
            image.setColor(farbe);
            image.fill();
            setImage(image);
        } else {
            GreenfootImage image = new GreenfootImage(getWorld().getCellSize(),getWorld().getCellSize());
            image.setColor(farbe);
            image.fill();
            setImage(image);

        }
    }

    public final Color getColor(int i) {
        switch (i%7) {
            case 0: return Color.RED;
            case 1: return Color.GREEN;
            case 2: return Color.BLUE;
            case 3: return Color.YELLOW;
            case 4: return Color.CYAN;
            case 5: return Color.MAGENTA;
            default: return Color.GRAY;
        }
    }

    protected final SnakeGame gibWelt() {
        return (SnakeGame) getWorld();
    }

    public final int gibWeltBreite() {
        return getWorld().getWidth();
    }

    public final int gibWeltHoehe() {
        return getWorld().getHeight();
    }

    public final boolean istWeltGrenzenlos() {
        if (gibWelt()==null) return false;
        return gibWelt().getBoundlessWorld();
    }

    public final int gibX() {
        return getX();
    }

    public final int gibY() {
        return getY();
    }

}
