package text_to_speech;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class text_to_speech {
    private Voice voice;

    /**
     * Converts the given text to speech.
     *
     * @param words The text to be converted to speech.
     */
    public void TextToSpeech(String words) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        voice = VoiceManager.getInstance().getVoice("kevin");
        if (voice != null) {
            voice.allocate(); // Allocating Voice
            try {
                voice.setRate(190); // Setting the rate of the voice
                voice.setPitch(150); // Setting the Pitch of the voice
                voice.setVolume(3); // Setting the volume of the voice
                SpeakText(words); // Calling speak() method

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalStateException("Cannot find voice: kevin");
        }
    }

    /**
     * Speaks the given text.
     *
     * @param words The text to be spoken.
     */
    public void SpeakText(String words) {
        voice.speak(words);
    }
}
