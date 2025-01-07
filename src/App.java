package src;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class App {

    public Watch watch;

    JLabel time;

    public ExecutorService manager;

    public void GUI(){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        JButton pauseButton = new JButton("Pause");
        time = new JLabel("Time: 00:00:00");


        startButton.addActionListener(e -> {
            watch.Start();

            manager.submit(() -> {
                while (watch.active) {
                    try {
                        Thread.sleep(100); // Sleep for 100ms
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    String formatted = watch.formattedTime();
                    // Update the UI on the Event Dispatch Thread
                    SwingUtilities.invokeLater(() -> time.setText("Time: " + formatted));
                }
            });
        });

        stopButton.addActionListener(e -> {
            long elapsed = watch.Stop();
            String formatted = watch.formattedTime();
            time.setText("Time: " + formatted);
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

    public App(ExecutorService executor){
        watch = new Watch();
        manager = executor;
        GUI();
    }
}
