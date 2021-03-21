import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class MainClass {
	public static void main(String[] args) {
		MainBresenham meuBresenham = new MainBresenham();
		
		JFrame f = new JFrame();
		f.setSize(640, 480);
		f.setVisible(true);
		f.getContentPane().add(meuBresenham);
	
		f.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		        System.exit(0);
		    }
		});
		
		meuBresenham.start();
	}
}
