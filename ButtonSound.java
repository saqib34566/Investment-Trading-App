import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class ButtonSound
{
    Clip clip;
    String clickSound = "click.wav";
    String errorSound = "error.wav";

    private void playSound(String soundFileName) {
        try{
            File file = new File(soundFileName);
            AudioInputStream sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);

            clip.setFramePosition(0);
            clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e){}

    }

    public void playClickSound() {
        playSound(clickSound);
    }

    public void playErrorSound() {
        playSound(errorSound);
    }



}
