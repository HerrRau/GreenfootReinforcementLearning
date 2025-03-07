import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class BreakoutScenarios extends BreakoutGameKI
{
    @Override
    public void setup() {
        schlaeger = new SchlaegerKI();
        addObject(schlaeger, 360/2, 480-20);   
        // 0: Agent mit Q-Table
        // 1: Agent mit Q-Table und wraparound
        // 2: Agent mit Q-Net
        setupScenario(0);        
    }

    public void setupScenario(int option) {
        verbose = true;
        switch (option)  {
            case 0: {
                    setPlayers( new Agent []{ new Agent(0.0) } );    
                    setRewardType(0);
                    setStateType(0);
                    break;
                }
            case 1: {
                    setPlayers( new Agent []{ new Agent(0.0) } );    
                    schlaeger.useWrapAround = true;
                    setRewardType(0);
                    setStateType(0);
                         break;
                }
            case 2: {
                    setRewardType(0); //#mit 1: nicht besonders gut
                    setStateType(0);
                    NeuralAgent a = new NeuralAgent(1,10,3,0.0);
                    a.setVerbose(false);
                    setPlayers( new Agent []{ a } );    
                    break;
                }
        }
    }

}
