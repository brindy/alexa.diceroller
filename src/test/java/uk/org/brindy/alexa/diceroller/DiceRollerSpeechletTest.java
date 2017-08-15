package uk.org.brindy.alexa.diceroller;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.when;

public class DiceRollerSpeechletTest {

    @Before
    public void setup() {

    }

    @Test
    public void onIntent_sizeUnknown() throws Exception {
        DiceRoller roller = new DiceRoller(0);
        DiceRollerSpeechlet function = new DiceRollerSpeechlet(roller);

        Slot sizeSlot = Slot.builder().withName("Size").withValue("?").build();
        Intent intent = Intent.builder().withName("Roll").withSlot(sizeSlot).build();

        IntentRequest request = mock(IntentRequest.class);
        when(request.getIntent()).thenReturn(intent);

        Session session = mock(Session.class);
        SpeechletResponse response = function.onIntent(request, session);

        PlainTextOutputSpeech outputSpeech = (PlainTextOutputSpeech) response.getOutputSpeech();
        assertThat(outputSpeech.getText(), equalTo("I'm sorry.  I was not able to understand what dice you wanted me to roll."));
    }

    @Test
    public void onIntent_number3_size8() throws Exception {
        DiceRoller roller = new DiceRoller(0);
        DiceRollerSpeechlet function = new DiceRollerSpeechlet(roller);

        Slot numberSlot = Slot.builder().withName("Number").withValue("3").build();
        Slot sizeSlot = Slot.builder().withName("Size").withValue("8").build();
        Intent intent = Intent.builder().withName("Roll").withSlot(sizeSlot).withSlot(numberSlot).build();

        IntentRequest request = mock(IntentRequest.class);
        when(request.getIntent()).thenReturn(intent);

        Session session = mock(Session.class);
        SpeechletResponse response = function.onIntent(request, session);

        PlainTextOutputSpeech outputSpeech = (PlainTextOutputSpeech) response.getOutputSpeech();
        assertThat(outputSpeech.getText(), equalTo("I rolled 3d 8s and got a 6, 7 and a 2 for a total of 15."));
    }


    @Test
    public void onIntent_numberUnkonwn_size20() throws Exception {
        DiceRoller roller = new DiceRoller(3);
        DiceRollerSpeechlet function = new DiceRollerSpeechlet(roller);

        Slot numberSlot = Slot.builder().withName("Number").withValue("?").build();
        Slot sizeSlot = Slot.builder().withName("Size").withValue("20").build();
        Intent intent = Intent.builder().withName("Roll").withSlot(sizeSlot).withSlot(numberSlot).build();

        IntentRequest request = mock(IntentRequest.class);
        when(request.getIntent()).thenReturn(intent);

        Session session = mock(Session.class);
        SpeechletResponse response = function.onIntent(request, session);

        PlainTextOutputSpeech outputSpeech = (PlainTextOutputSpeech) response.getOutputSpeech();
        assertThat(outputSpeech.getText(), equalTo("I rolled 1d 20 and got a 15."));
    }

    @Test
    public void onIntent_number2_size6() throws Exception {
        DiceRoller roller = new DiceRoller(0);
        DiceRollerSpeechlet function = new DiceRollerSpeechlet(roller);

        Slot numberSlot = Slot.builder().withName("Number").withValue("2").build();
        Slot sizeSlot = Slot.builder().withName("Size").withValue("6").build();
        Intent intent = Intent.builder().withName("Roll").withSlot(sizeSlot).withSlot(numberSlot).build();

        IntentRequest request = mock(IntentRequest.class);
        when(request.getIntent()).thenReturn(intent);

        Session session = mock(Session.class);
        SpeechletResponse response = function.onIntent(request, session);

        PlainTextOutputSpeech outputSpeech = (PlainTextOutputSpeech) response.getOutputSpeech();
        assertThat(outputSpeech.getText(), equalTo("I rolled 2d 6s and got a 1 and a 5 for a total of 6."));
    }

    @Test
    public void onIntent_noNumber_size6() throws Exception {
        DiceRoller roller = new DiceRoller(0);
        DiceRollerSpeechlet function = new DiceRollerSpeechlet(roller);

        Slot sizeSlot = Slot.builder().withName("Size").withValue("6").build();
        Intent intent = Intent.builder().withName("Roll").withSlot(sizeSlot).build();

        IntentRequest request = mock(IntentRequest.class);
        when(request.getIntent()).thenReturn(intent);

        Session session = mock(Session.class);
        SpeechletResponse response = function.onIntent(request, session);

        PlainTextOutputSpeech outputSpeech = (PlainTextOutputSpeech) response.getOutputSpeech();
        assertThat(outputSpeech.getText(), equalTo("I rolled 1d 6 and got a 1."));
    }

}