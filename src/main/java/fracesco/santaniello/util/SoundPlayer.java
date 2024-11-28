package fracesco.santaniello.util;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SoundPlayer {
    private Clip clipGameOver;
    private Clip clipEat;
    private Clip clipMove;

    private final BlockingQueue<Runnable> soundQueue = new LinkedBlockingQueue<>();

    private static class InnerClass {
        private static final SoundPlayer instance = new SoundPlayer();
    }

    private SoundPlayer() {
        try {
            clipGameOver = AudioSystem.getClip();
            clipGameOver.open(AudioSystem.getAudioInputStream(new File("./source/sounds/gameover.wav")));

            clipEat = AudioSystem.getClip();
            clipEat.open(AudioSystem.getAudioInputStream(new File("./source/sounds/food.wav")));

            clipMove = AudioSystem.getClip();
            clipMove.open(AudioSystem.getAudioInputStream(new File("./source/sounds/move.wav")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Thread soundThread = new Thread(() -> {
            try {
                while (true) {
                    soundQueue.take().run();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        soundThread.setDaemon(true);
        soundThread.start();
    }

    public static SoundPlayer getInstance() {
        return InnerClass.instance;
    }

    private void playSound(Clip clip) {
        soundQueue.offer(() -> {
            synchronized (clip) {
                clip.stop();
                clip.setFramePosition(0);
                clip.start();
            }
        });
    }

    public void playSoundMove() {
        playSound(clipMove);
    }

    public void playSoundEat() {
        playSound(clipEat);
    }

    public void playSoundGameOver() {
        playSound(clipGameOver);
    }
}
