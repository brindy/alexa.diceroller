package uk.org.brindy.alexa.diceroller;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by brindy on 06/07/2017.
 */
public class Result {

    private List<Integer> rolls = new LinkedList<Integer>();
    private int total;

    public void add(int roll) {
        total += roll;
        rolls.add(roll);
    }

    public int getTotal() {
        return total;
    }

    public List<Integer> getRolls() {
        return rolls;
    }
}
