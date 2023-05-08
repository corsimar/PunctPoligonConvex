import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
	
	MyFrame frame;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Main();
			}
		});
	}
	
	Main() {
		initialize();
	}
	
	private void initialize() {
		frame = new MyFrame();
		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(frame.panel.step == 1 && frame.panel.xPoints.size() > 2)
						frame.panel.endPolygon();
					if(frame.panel.step == 3)
						frame.panel.drawPolygonCenter();
					if(frame.panel.step == 5)
						frame.panel.translatePolygon();
					if(frame.panel.step == 7)
						frame.panel.sortPoints();
				}
			}
		});
	}
}
