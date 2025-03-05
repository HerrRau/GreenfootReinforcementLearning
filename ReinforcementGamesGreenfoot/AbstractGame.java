public abstract class AbstractGame  implements Game
{
    protected double reward;

    //used for continuous reward
    public double getRewardForPlayer(int playerID) {
        int winner = getWinner();
        if (winner==-1) return 0;
        else if (winner==0 && playerID == 0) return getRewardWin();
        else if (winner==0 && playerID == 1) return getRewardLose();
        else if (winner==1 && playerID == 1) return getRewardWin();
        else if (winner==1 && playerID == 0) return getRewardLose();
        else return 0; // shouldn't be possible
    }

    public double getInitialValue() { 
        return 0; 
    }

    //used for win/lose reward only?
    @Override public double getRewardWin() { 
        return 1; 
    }

    @Override public double getRewardLose() { 
        return -1; 
    }


}
