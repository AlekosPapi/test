package com.example;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MusicPlayerWithVolumeControl extends JFrame {
    private Clip clip;
    private FloatControl volumeControl;
    private JSlider volumeSlider;
    //pezw mpala san ton ramos
    //mes to benrabeu
    //diakopes tis lew vamos
    // sto montevideo 
    // mou zitane tin fanela 
    // mes to benrabeu
    //exw kanei tin favela ena me to koloseo
    public MusicPlayerWithVolumeControl(String filePath) {
        try {
            // Set up audio clip
            clip = AudioSystem.getClip();
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            clip.open(stream);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(0); // No gain
            clip.loop(Integer.MAX_VALUE);
            clip.start();

            // Set up GUI
            initUI();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            System.err.println("Could not start audio stream: " + e.getMessage());
        }
    }

    private void initUI() {
        setTitle("Music Player with Volume Control");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        volumeSlider.addChangeListener(e -> adjustVolume(volumeSlider.getValue()));
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setMinorTickSpacing(1);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(volumeSlider, BorderLayout.CENTER);
        add(panel);
    }

    private void adjustVolume(int value) {
        if (volumeControl != null) {
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float volume = min + (max - min) * (value / 100.0f);
            volumeControl.setValue(volume);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MusicPlayerWithVolumeControl player = new MusicPlayerWithVolumeControl("./lol.wav");
            player.setVisible(true);
        });
    }
}
