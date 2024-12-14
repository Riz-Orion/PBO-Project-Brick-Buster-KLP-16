import javax.sound.sampled.*;
import java.io.File;

public class AudioManager implements AudioPlayer {
    private static AudioManager instance;
    private Clip bgmClip;
    private Clip soundEffectClip;

    private AudioManager() {}

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    @Override
    public void playBGM(String filePath, boolean loop) {
        try {
            stopBGM(); // Stop previous BGM
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(audioStream);
            if (loop) {
                bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                bgmClip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopBGM() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
            bgmClip.close();
        }
    }

    @Override
    public void playSoundEffect(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            soundEffectClip = AudioSystem.getClip();
            soundEffectClip.open(audioStream);
            soundEffectClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
