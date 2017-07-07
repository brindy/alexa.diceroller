package uk.org.brindy.alexa.diceroller;

import java.util.Random;

class DiceRoller {

    private final Random random;

    DiceRoller(long seed) {
        random = new Random(seed);
    }

    DiceRoller() {
        this(System.currentTimeMillis());
    }

    Result roll(int number, int size) {
        Result result = new Result();
        for (int i = 0; i < number; i++) {
            result.add(random.nextInt(size) + 1);
        }
        return result;
    }

}
