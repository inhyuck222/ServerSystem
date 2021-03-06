import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SyncTestFrame extends JFrame {

	public SyncTestFrame() {
		setContentPane(new DrawingPanel());
		setSize(300, 300);

		setVisible(true);
	}

	class DrawingPanel extends JPanel {
		Rectangle r = new Rectangle(0, 0, 0, 0);
		DrawinThread th;

		public DrawingPanel() {
			this.addMouseListener(new MyMouseLinstener());
			th = new DrawinThread();
			th.start();
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (r.width <= 0 || r.height <= 0)
				return;
			g.setColor(Color.GREEN);
			g.drawOval(r.x, r.y, r.width, r.height);
		}

		public void set(int x, int y, int z, int h) {
			r.x = x;
			r.y = y;
			r.width = z;
			r.height = h;
		}

		public synchronized void wakeup() {
			th.setRunning();
			this.notify();
		}

		public void pause() {
			th.pause();
		}

		class MyMouseLinstener extends MouseAdapter {
			public void mouseEntered(MouseEvent e) {
				wakeup();
			}

			public void mouseExited(MouseEvent e) {
				pause();
			}
		}

		public synchronized void waitForWakeup() {
			try {
				this.wait();
			} catch (InterruptedException e) {
				System.exit(0);
			}
		}

		class DrawinThread extends Thread {
			boolean running = false;

			public void pause() {
				running = false;
			}

			public void setRunning() {
				running = true;
			}

			public void run() {
				while (true) {
					if (running == false)
						waitForWakeup();
					try {
						sleep(300);
					} catch (InterruptedException e) {
						return;
					}

					int x = (int) (Math.random() * getWidth());
					int y = (int) (Math.random() * getHeight());
					int z = (int) (Math.random() * 100) + 1;
					int h = (int) (Math.random() * 100) + 1;

					set(x, y, z, h);
					repaint();
				}
			}
		}
	}

	public static void main(String[] args) {
		new SyncTestFrame();
	}

}