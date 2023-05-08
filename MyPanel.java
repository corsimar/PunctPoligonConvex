import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyPanel extends JPanel implements MouseListener, ActionListener {
	
	int windowW, windowH;
	int pointSize = 8;
	
	ArrayList<Integer> xPoints = new ArrayList<Integer>();
	ArrayList<Integer> yPoints = new ArrayList<Integer>();
	
	ArrayList<Integer> xAux = new ArrayList<Integer>();
	ArrayList<Integer> yAux = new ArrayList<Integer>();
	
	int xO, yO, xS, yS, xM, yM;
	
	int step = 0;
	
	MyPanel(int windowW, int windowH) {
		this.windowW = windowW; this.windowH = windowH;
		this.setLayout(null);
		addMouseListener(this);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		if(step == 0) {
			g2d.setPaint(Color.white);
			g2d.fillRect(0, 0, windowW, windowH);
			clearTitle(g2d);
			g2d.setPaint(Color.black);
			drawCenteredString(g2d, "Construiti un poligon convex", new Rectangle(0, 0, windowW, 100), new Font("Sans-Serif", Font.BOLD, 21), Color.black);
			xO = windowW / 2; yO = windowH / 2;
			drawAxis(g2d);
		}
		else if(step == 1 && xPoints.size() == 1) {
			g2d.setPaint(Color.black);
			g2d.fillOval(xPoints.get(xPoints.size() - 1) - pointSize / 2, yPoints.get(yPoints.size() - 1) - pointSize / 2, pointSize, pointSize);
		}
		else if(step == 1 && xPoints.size() > 1) {
			g2d.setPaint(Color.black);
			g2d.fillOval(xPoints.get(xPoints.size() - 1) - pointSize / 2, yPoints.get(yPoints.size() - 1) - pointSize / 2, pointSize, pointSize);
			g2d.setStroke(new BasicStroke(2));
			g2d.drawLine(xPoints.get(xPoints.size() - 2), yPoints.get(yPoints.size() - 2), xPoints.get(xPoints.size() - 1), yPoints.get(yPoints.size() - 1));
			if(xPoints.size() > 2) {
				clearTitle(g2d);
				g2d.setPaint(Color.black);
				drawCenteredString(g2d, "Apasati ENTER pentru a finaliza poligonul", new Rectangle(0, 0, windowW, 100), new Font("Sans-Serif", Font.BOLD, 21), Color.black);
			}
		}
		else if(step == 2) {
			g2d.setPaint(Color.black);
			g2d.setStroke(new BasicStroke(2));
			g2d.drawLine(xPoints.get(xPoints.size() - 1), yPoints.get(yPoints.size() - 1), xPoints.get(0), yPoints.get(0));
			clearTitle(g2d);
			g2d.setPaint(Color.black);
			drawCenteredString(g2d, "Apasati ENTER pentru a crea punctul S", new Rectangle(0, 0, windowW, 100), new Font("Sans-Serif", Font.BOLD, 21), Color.black);
			step = 3;
		}
		else if(step == 4) {
			xS = (xPoints.get(0) + xPoints.get(1) + xPoints.get(2)) / 3;
			yS = (yPoints.get(0) + yPoints.get(1) + yPoints.get(2)) / 3;
			
			g2d.setPaint(Color.red);
			g2d.fillOval(xS, yS, pointSize, pointSize);
			
			clearTitle(g2d);
			g2d.setPaint(Color.black);
			drawCenteredString(g2d, "Apasati ENTER pentru a translata poligonul", new Rectangle(0, 0, windowW, 100), new Font("Sans-Serif", Font.BOLD, 21), Color.black);
			
			step = 5;
		}
		else if(step == 6) {
			int dx = xO - xS; int dy = yO - yS;
			xS = xO; yS = yO;
			
			g2d.setPaint(Color.white);
			g2d.fillRect(0, 100, windowW, windowH);
			drawAxis(g2d);
			
			g2d.setPaint(Color.black);
			g2d.setStroke(new BasicStroke(2));
			for(int i = 0; i < xPoints.size(); i++) {
				xPoints.set(i, xPoints.get(i) + dx);
				yPoints.set(i, yPoints.get(i) + dy);
				g2d.fillOval(xPoints.get(i) - pointSize / 2, yPoints.get(i) - pointSize / 2, pointSize, pointSize);
				if(i > 0)
					g2d.drawLine(xPoints.get(i - 1), yPoints.get(i - 1), xPoints.get(i), yPoints.get(i));
			}
			g2d.drawLine(xPoints.get(xPoints.size() - 1), yPoints.get(yPoints.size() - 1), xPoints.get(0), yPoints.get(0));
			
			g2d.setPaint(Color.red);
			
			g2d.fillOval(xS - pointSize / 2, yS - pointSize / 2, pointSize, pointSize);
			
			clearTitle(g2d);
			g2d.setPaint(Color.black);
			drawCenteredString(g2d, "Apasati ENTER pentru a ordona varfurile poligonului", new Rectangle(0, 0, windowW, 100), new Font("Sans-Serif", Font.BOLD, 21), Color.black);
			step = 7;
		}
		else if(step == 8) {
			boolean ok = false;
			do {
				ok = false;
				for(int i = 0; i < xPoints.size() - 1; i++) {
					if(comparePoints(i, i + 1) == 1) {
						Point p = new Point(xPoints.get(i + 1), yPoints.get(i + 1));
						xPoints.set(i + 1, xPoints.get(i)); yPoints.set(i + 1, yPoints.get(i));
						xPoints.set(i, p.x); yPoints.set(i, p.y);
						ok = true;
					}
				}
			} while(ok);
			
			for(int i = 0; i < xPoints.size(); i++)
				drawCenteredString(g2d, "A" + (i + 1), new Rectangle(xPoints.get(i) - 20, yPoints.get(i) - 20, 40, 20), new Font("Sans-Serif", Font.BOLD, 11), Color.gray);
			
			clearTitle(g2d);
			drawCenteredString(g2d, "Apasati oriunde pentru a crea punctul M", new Rectangle(0, 0, windowW, 100), new Font("Sans-Serif", Font.BOLD, 21), Color.black);
			step = 9;
		}
		else if(step == 10) {
			g2d.setPaint(new Color(44, 163, 80));
			g2d.fillOval(xM - pointSize / 2, yM - pointSize / 2, pointSize, pointSize);
			
			xAux.clear(); yAux.clear();
			xAux.addAll(xPoints); yAux.addAll(yPoints);
			
			xAux.add(xM); yAux.add(yM);
			sortPoints(xAux, yAux);
			
			int index = cautareBinara(0, xAux.size() - 1, new Point(xM, yM));
			if(index == 0 || index == xAux.size() - 1) {
				clearTitle(g2d);
				if(index == xAux.size() - 1)
					drawCenteredString(g2d, "Punctul M se afla intre punctele " + (xAux.size() - 1) + " si " + 1 + " - " + getPointMLocation(xAux.size() - 2, 0), new Rectangle(0, 0, windowW, 100), new Font("Sans-Serif", Font.BOLD, 21), Color.black);
				else
					drawCenteredString(g2d, "Punctul M se afla intre punctele " + (xAux.size() - 1) + " si " + 1 + " - " + getPointMLocation(xAux.size() - 1, 1), new Rectangle(0, 0, windowW, 100), new Font("Sans-Serif", Font.BOLD, 21), Color.black);
			}
			else {
				clearTitle(g2d);
				drawCenteredString(g2d, "Punctul M se afla intre punctele " + index + " si " + (index + 1) + " - " + getPointMLocation(index - 1, index + 1), new Rectangle(0, 0, windowW, 100), new Font("Sans-Serif", Font.BOLD, 21), Color.black);
			}
		}
	}
	
	void drawAxis(Graphics2D g2d) {
		g2d.setPaint(Color.black);
		g2d.setStroke(new BasicStroke(1));
		g2d.drawLine(0, yO, windowW, yO);
		g2d.drawLine(xO, 100, xO, windowH);
	}
	
	void endPolygon() {
		step = 2;
		repaint();
	}
	
	void drawPolygonCenter() {
		step = 4;
		repaint();
	}
	
	void translatePolygon() {
		step = 6;
		repaint();
	}
	
	int determinant(Point A, Point B, Point C) {
		return A.x * B.y + B.x * C.y + C.x * A.y - B.y * C.x - C.y * A.x - A.y * B.x;
	}
	
	int getPointZone(Point p) {
		if(p.x > xS && p.y <= yS) return 1;
		else if(p.x <= xS && p.y < yS) return 2;
		else if(p.x < xS && p.y >= yS) return 3;
		else if(p.x >= xS && p.y > yS) return 4;
		else return 0;
	}
	
	void sortPoints() {
		step = 8;
		repaint();
	}
	
	void sortPoints(ArrayList<Integer> x, ArrayList<Integer> y) {
		boolean ok = false;
		do {
			ok = false;
			for(int i = 0; i < x.size() - 1; i++) {
				if(comparePoints(i, i + 1, 0) == 1) {
					Point p = new Point(x.get(i + 1), y.get(i + 1));
					x.set(i + 1, x.get(i)); y.set(i + 1, y.get(i));
					x.set(i, p.x); y.set(i, p.y);
					ok = true;
				}
			}
		} while(ok);
	}
	
	int comparePoints(int i1, int i2) {
		if(getPointZone(new Point(xPoints.get(i2), yPoints.get(i2))) < getPointZone(new Point(xPoints.get(i1), yPoints.get(i1)))) {
			return 1;
		}
		else if(getPointZone(new Point(xPoints.get(i2), yPoints.get(i2))) == getPointZone(new Point(xPoints.get(i1), yPoints.get(i1)))) {
			if(determinant(new Point(xPoints.get(i2), yPoints.get(i2)), new Point(xS, yS), new Point(xPoints.get(i1), yPoints.get(i1))) > 0)
				return 1;
			else if(determinant(new Point(xPoints.get(i2), yPoints.get(i2)), new Point(xS, yS), new Point(xPoints.get(i1), yPoints.get(i1))) < 0)
				return 2;
			else
				return 0;
		}
		else return 2;
	}
	
	int comparePoints(int i1, int i2, int i) {
		if(getPointZone(new Point(xAux.get(i2), yAux.get(i2))) < getPointZone(new Point(xAux.get(i1), yAux.get(i1)))) {
			return 1;
		}
		else if(getPointZone(new Point(xAux.get(i2), yAux.get(i2))) == getPointZone(new Point(xAux.get(i1), yAux.get(i1)))) {
			if(determinant(new Point(xAux.get(i2), yAux.get(i2)), new Point(xS, yS), new Point(xAux.get(i1), yAux.get(i1))) > 0)
				return 1;
			else if(determinant(new Point(xAux.get(i2), yAux.get(i2)), new Point(xS, yS), new Point(xAux.get(i1), yAux.get(i1))) < 0)
				return 2;
			else
				return 0;
		}
		else return 2;
	}
	
	int comparePoints(Point p1, Point p2) {
		if(getPointZone(p2) < getPointZone(p1)) {
			return 1;
		}
		else if(getPointZone(p2) == getPointZone(p1)) {
			if(determinant(p2, new Point(xS, yS), p1) > 0)
				return 1;
			else if(determinant(p2, new Point(xS, yS), p1) < 0)
				return 2;
			else
				return 0;
		}
		else return 2;
	}
	
	int cautareBinara(int l, int r, Point p) {
		if(l < r) {
			int m = (l + r) / 2;
			
			if(comparePoints(new Point(xAux.get(m), yAux.get(m)), p) == 1)
				return cautareBinara(l, m - 1, p);
			else if(comparePoints(new Point(xAux.get(m), yAux.get(m)), p) == 2)
				return cautareBinara(m + 1, r, p);
			else return m;
		}
		else if(l == r) return l;
		else return -1;
	}
	
	String getPointMLocation(int a1, int a2) {
		int r = determinant(new Point(xM, yM), new Point(xAux.get(a1), yAux.get(a1)), new Point(xAux.get(a2), yAux.get(a2)));
		if(r > 0)
			return "exterior";
		else if(r < 0)
			return "interior";
		else
			return "frontiera";
	}
	
	void clearTitle(Graphics2D g2d) {
		g2d.setPaint(Color.white);
		g2d.fillRect(0, 0, windowW, 100);
	}
	
	void drawCenteredString(Graphics2D g2d, String message, Rectangle r, Font font, Color color) {
		FontMetrics metrics = g2d.getFontMetrics(font);
		
		int x = r.x + (r.width - metrics.stringWidth(message)) / 2;
		int y = r.y + (r.height - metrics.getHeight() + metrics.getAscent()) / 2;
		
		g2d.setPaint(color);
		g2d.setFont(font);
		g2d.drawString(message, x, y);
	}
	
	void print(String message) {
		System.out.println(message);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(step == 0 || step == 1) {
			if(step == 0) step = 1;
			xPoints.add(e.getX());
			yPoints.add(e.getY());
			repaint();
		}
		if(step == 9 || step == 10) {
			if(step == 9) step = 10;
			xM = e.getX();
			yM = e.getY();
			repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {}
}
