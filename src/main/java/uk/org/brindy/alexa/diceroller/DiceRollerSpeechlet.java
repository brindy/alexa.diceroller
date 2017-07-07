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
        System.out.print("onIntent requestId: " + request.getRequestId() + ", sessionId: " + session.getSessionId());

        Intent intent = request.getIntent();
        int size = Integer.parseInt(intent.getSlot("Size").getValue());
        int number = getOptional(intent, "Number", 1);

        Result result = roller.roll(number, size);

        return createResponse(result, number, size);
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
        String text = "I rolled " + number + "d " + size + (number > 1 ? "s" : "") + " and got a";

        int i = 0;
        for (; i < result.getRolls().size(); i++) {
            if (i > 0) {
                if (i + 1 == result.getRolls().size()) {
                    text += " and a";
                } else {
                    text += ",";
                }
            }

            text += " " + result.getRolls().get(i);
        }

        if (i > 1) {
            text += " for a total of " + result.getTotal();
        }

        text += ".";
        return text;
    }

    private int getOptional(Intent intent, String slotName, int defaultValue) {
        Slot slot = intent.getSlot(slotName);
        if (slot != null) {
            try {
                return Integer.parseInt(slot.getValue());
            } catch(NumberFormatException ex) {
            }
        }
        return defaultValue;
    }

    public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException {

    }

}