package uk.org.brindy.alexa.diceroller;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class DiceRollerTest {

    @Test
    public void roll1d6() throws Exception {
        DiceRoller roller = new DiceRoller(0);
        Result result = roller.roll(1, 6);
        assertThat(result.getTotal(), equalTo(1));
        assertThat(result.getRolls().size(), equalTo(1));
    }

    @Test
    public void roll2d6() throws Exception {
        DiceRoller roller = new DiceRoller(0);
        Result result = roller.roll(2, 6);
        assertThat(result.getTotal(), equalTo(6));
        assertThat(result.getRolls().size(), equalTo(2));
    }

    @Test
    public void roll3d8() throws Exception {
        DiceRoller roller = new DiceRoller(0);
        Result result = roller.roll(3, 8);
        assertThat(result.getTotal(), equalTo(15));
        assertThat(result.getRolls().size(), equalTo(3));
    }

}