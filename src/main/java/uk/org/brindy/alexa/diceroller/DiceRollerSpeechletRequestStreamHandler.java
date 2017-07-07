package uk.org.brindy.alexa.diceroller;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

public class DiceRollerSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {

    private static final Set<String> supportedApplicationIds = new HashSet<String>();
    static {
        supportedApplicationIds.add("amzn1.ask.skill.65f0f300-b7c5-49ce-92cd-e23e1b1df0c0");
    }

    public DiceRollerSpeechletRequestStreamHandler() {
        super(new DiceRollerSpeechlet(), supportedApplicationIds);
    }

}
