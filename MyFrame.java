import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MyFrame extends JFrame {
	
	MyPanel panel;
	
	MyFrame() {
		this.setMinimumSize(new Dimension(1200, 800));
		this.pack();
		panel = new MyPanel(this.getContentPane().getBounds().width, this.getContentPane().getBounds().height);
		panel.setBounds(this.getContentPane().getBounds());
		this.add(panel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
