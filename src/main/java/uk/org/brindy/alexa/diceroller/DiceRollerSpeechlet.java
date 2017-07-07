package uk.org.brindy.alexa.diceroller;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import org.apache.commons.lang3.StringUtils;

public class DiceRollerSpeechlet implements Speechlet {

    private final DiceRoller roller;

    DiceRollerSpeechlet(DiceRoller roller) {
        this.roller = roller;
    }

    public DiceRollerSpeechlet() {
        this(new DiceRoller());
    }

    public void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException {
    }

    public SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException {
        return null;
    }

    public SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException {
        Intent intent = request.getIntent();
        int size = getOptional(intent, "Size", -1);
        if (-1 == size) {
            return errorResponse();
        }

        int number = getOptional(intent, "Number", 1);

        Result result = roller.roll(number, size);

        return createResponse(result, number, size);
    }

    private SpeechletResponse errorResponse() {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText("I'm sorry.  I was not able to understand what you wanted me to roll.");
        return SpeechletResponse.newTellResponse(speech);
    }

    private SpeechletResponse createResponse(Result result, int number, int size) {

        String text = toText(result, number, size);

        SimpleCard card = new SimpleCard();
        card.setTitle("Dice Roll");
        card.setContent(text);

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(text);

        return SpeechletResponse.newTellResponse(speech, card);
    }

    private String toText(Result result, int number, int size) {
        StringBuilder text = new StringBuilder("I rolled ");
        text.append(number).append("d ").append(size).append((number > 1 ? "s" : "")).append(" and got a");

        int i = 0;
        for (; i < result.getRolls().size(); i++) {
            if (i > 0) {
                if (i + 1 == result.getRolls().size()) {
                    text.append(" and a");
                } else {
                    text.append(",");
                }
            }

            text.append(" ").append(result.getRolls().get(i));
        }

        if (i > 1) {
            text.append(" for a total of ").append(result.getTotal());
        }

        text.append(".");
        return text.toString();
    }

    private int getOptional(Intent intent, String slotName, int defaultValue) {
        Slot slot = intent.getSlot(slotName);
        if (slot == null || !StringUtils.isNumeric(slot.getValue())) {
            return defaultValue;
        }
        return Integer.parseInt(slot.getValue());
    }

    public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {

    }

}