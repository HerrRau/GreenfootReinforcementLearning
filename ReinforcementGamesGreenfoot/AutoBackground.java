import greenfoot.Color;

public class AutoBackground {
    String name;
    int minBrightness;
    int maxBrightness;
    Color grass;

    // public AutoBackground (String name, int min, int max)
    // {
        // this.name=name;
        // this.minBrightness = min;
        // this.maxBrightness = max;        
    // }

    public AutoBackground (String name, int min, int max, Color c)
    {
        this.name=name;
        this.minBrightness = min;
        this.maxBrightness = max;   
        grass = c;
    }


}