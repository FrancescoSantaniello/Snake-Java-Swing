package fracesco.santaniello.util;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SoundService {
    private Clip clipGameOver;
    private Clip clipEat;
    private Clip clipMove;
    private Clip clipBackGround;

    private final BlockingQueue<Runnable> soundQueue = new LinkedBlockingQueue<>();

    private static class InnerClass {
        private static final SoundService instance = new SoundService();
    }

    private SoundService() {
        try {
            clipGameOver = AudioSystem.getClip();
            clipGameOver.open(AudioSystem.getAudioInputStream(new File("./source/sounds/gameover.wav")));

            clipEat = AudioSystem.getClip();
            clipEat.open(AudioSystem.getAudioInputStream(new File("./source/sounds/food.wav")));

            clipMove = AudioSystem.getClip();
            clipMove.open(AudioSystem.getAudioInputStream(new File("./source/sounds/move.wav")));

            clipBackGround = AudioSystem.getClip();
            if (new Random().nextInt(2) == 1){
                clipBackGround.open(AudioSystem.getAudioInputStream(new File("./source/sounds/background_sound_1.wav")));
            }
            else{
                clipBackGround.open(AudioSystem.getAudioInputStream(new File("./source/sounds/background_sound_2.wav")));
            }
            clipBackGround.loop(Clip.LOOP_CONTINUOUSLY);

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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static SoundService getInstance() {
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

    public void setPlayBackGround(boolean play){
        if (play){
            clipBackGround.start();
        }
        else{
            clipBackGround.stop();
            clipBackGround.setFramePosition(0);
        }
    }
}
