package Main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class MainClass {

    public static JFrame frame = null;

    public static void main(String[] args) {

        JFrame f = new JFrame();
        frame = f;

        MainCanvas meuCanvas = new MainCanvas();

        f.setSize(640, 480);
        f.setVisible(true);
        f.getContentPane().add(meuCanvas);

        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        meuCanvas.start();
    }
}
