package exceptions;

import model.Player;

public class CycleConditionException extends Exception {
    public CycleConditionException(Player p1, Player p2) {
        super(String.format("Please check config: %s and %s can beat ane another",  p1, p2));
    }
}
