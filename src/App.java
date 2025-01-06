package src;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class App {

    public Watch watch;

    JLabel time;

    public void GUI(){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        JButton pauseButton = new JButton("Pause");
        time = new JLabel("Time: 00:00:00");


        startButton.addActionListener(e -> {
            watch.Start();
        });

        stopButton.addActionListener(e -> {
            long elapsed = watch.Stop();
            time.setText("Time: " + elapsed);
        });


        panel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(startButton);
        panel.add(stopButton);
        panel.add(pauseButton);
        panel.add(time);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

    public App(){
        watch = new Watch();
        GUI();
    }
}
