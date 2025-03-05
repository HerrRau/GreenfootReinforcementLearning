import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Wolke extends BreakoutElement
{
    private GreenfootImage image;
    private int verkleinerungUm;
    private int minimalgroesse = 10;
    private int wahrscheinlichkeitVerkleinerung = 2;

    //public Wolke(int groesse,)
    public Wolke()
    {
        getImage().scale(50,50);
        image = getImage();
        verkleinerungUm = Greenfoot.getRandomNumber(4) + 1;  // 1 to 3
        if (verkleinerungUm > 3) {
            verkleinerungUm = verkleinerungUm - 2;
        }
        //verkleinerungUm hat: 1,2,3,2
    }

    public void act() 
    {
        verkleinern(); 
    }    

    private void verkleinern()
    {
        if(getImage().getWidth() < minimalgroesse) 
        {
            getWorld().removeObject(this);
        }
        else
        {
            if (Greenfoot.getRandomNumber(wahrscheinlichkeitVerkleinerung)==0)
            {
                GreenfootImage img = new GreenfootImage(image);
                img.scale ( getImage().getWidth()-verkleinerungUm, getImage().getHeight()-verkleinerungUm );
                setImage (img);
            }
        }
    }
}
