import javax.sound.sampled.*;
import java.io.File;

public class AudioManager {
    private static AudioManager instance;
    private Clip bgmClip;
    private Clip soundEffectClip;

    private AudioManager() {}

    // Static method to get the single instance of AudioManager
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    // Method untuk memutar BGM
    public void playBGM(String filePath, boolean loop) {
        try {
            stopBGM(); // Stop BGM sebelumnya jika ada
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

    // Method untuk menghentikan BGM
    public void stopBGM() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
            bgmClip.close();
        }
    }

    public void restartBGM(String filePath, boolean loop) {
        stopBGM(); // Hentikan BGM jika ada
        playBGM(filePath, loop); // Putar ulang BGM
    }    

    // Method untuk memutar efek suara
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
