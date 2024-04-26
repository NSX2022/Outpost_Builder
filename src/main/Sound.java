package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    Clip clip;
    URL[] soundURL = new URL[40];

    public Sound() {

        soundURL[0] = getClass().getResource("/sound/music/hundred_year_war_orchestra_wav.wav");
        soundURL[1] = getClass().getResource("/sound/music/main_ambience_wav.wav");
        soundURL[2] = getClass().getResource("/sound/se/war_cry_wav.wav");
        soundURL[3] = getClass().getResource("/sound/se/build_wood_sfx2.wav");
        soundURL[4] = getClass().getResource("/sound/se/build_sfx_2.wav");

    }

    public void setFile(int i) {
        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void play() {

        clip.start();

    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        if(clip != null){
            clip.stop();
        }
    }
}
