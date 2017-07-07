package uk.org.brindy.alexa.diceroller;

import java.util.LinkedList;
import java.util.List;

class Result {

    private List<Integer> rolls = new LinkedList<Integer>();
    private int total;

    void add(int roll) {
        total += roll;
        rolls.add(roll);
    }

    int getTotal() {
        return total;
    }

    List<Integer> getRolls() {
        return rolls;
    }

}
