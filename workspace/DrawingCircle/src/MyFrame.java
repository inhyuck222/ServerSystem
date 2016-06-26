import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyFrame extends JFrame {
	DrawingPanel d;
	ControlPanel c;
	JLabel numText = new JLabel("0");
	
	public MyFrame() {
		JPanel content = new JPanel(new BorderLayout());
		d = new DrawingPanel(numText);
		c = new ControlPanel(d,numText);
		content.add(c, BorderLayout.NORTH);
		content.add(d, BorderLayout.CENTER);
		setContentPane(content);
		setSize(300, 300);

		setVisible(true);
	}

	class MyRectangle extends Rectangle {
		Color c;

		public MyRectangle(int x, int y, int w, int h, Color c) {
			super(x, y, w, h);
			this.c = c;
		}

		public Color getColor() {
			return c;
		}
	}
	
	class ControlPanel extends JPanel{
		DrawingPanel d;
		
		JButton upProducerBtn = new JButton("+");
		JButton downProducerBtn = new JButton("-");
		JButton upConsumerBtn = new JButton("+");
		JButton downConsumerBtn = new JButton("-");
		
		
		public ControlPanel(DrawingPanel d, JLabel numText){
			this.d = d;
			this.add(upProducerBtn);
			this.add(downProducerBtn);
			this.add(numText);
			this.add(upConsumerBtn);
			this.add(downConsumerBtn);
			
			upProducerBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(d.pThread.speed <= 0)return;
					d.pThread.speed -= 50;
				}
			});
			
			downProducerBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					d.pThread.speed += 50;
				}
			});
			
			upConsumerBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(d.cThread.speed <= 0)return;
					d.cThread.speed -= 50;
				}
			});
			
			downConsumerBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(d.cThread.speed <= 0)return;
					d.cThread.speed += 50;
				}
			});
		}
	}

	class DrawingPanel extends JPanel {
		Vector<MyRectangle> v = new Vector<MyRectangle>();
		ProducerThread pThread;
		ConsumerThread cThread;
		JLabel numText;

		public DrawingPanel(JLabel numText) {
			this.numText = numText; 
			pThread = new ProducerThread(this);
			cThread = new ConsumerThread(this);
			pThread.start();
			cThread.start();
		}

		public synchronized void addRectangle(MyRectangle r) {
			v.addElement(r);
			numText.setText(v.size()+"");
			repaint();
			this.notify();
		}

		public synchronized void removeRectangle() {
			if (v.size() == 0) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					return;
				}
			}
			v.remove(0);
			numText.setText(v.size()+"");
			repaint();
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			for (int i = 0; i < v.size(); i++) {
				MyRectangle r = v.get(i);
				if (r.width <= 0 || r.height <= 0)
					continue;
				g.setColor(r.getColor());
				g.drawOval(r.x, r.y, r.width, r.height);
			}
		}
	}

	class ProducerThread extends Thread {
		DrawingPanel p;
		int speed = 300;

		public ProducerThread(DrawingPanel p) {
			this.p = p;
		}

		public void run() {
			while (true) {
				try {
					sleep((int)(Math.random()*50) + speed);

					int x = (int) (Math.random() * getWidth());
					int y = (int) (Math.random() * getHeight());
					int w = (int) (Math.random() * 100) + 1;
					int h = (int) (Math.random() * 100) + 1;

					MyRectangle r = new MyRectangle(x, y, w, h, new Color((int) (Math.random() * 256),
							(int) (Math.random() * 256), (int) (Math.random() * 256)));
					p.addRectangle(r);

				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}

	class ConsumerThread extends Thread {
		DrawingPanel p;
		int speed = 300;

		public ConsumerThread(DrawingPanel p) {
			this.p = p;
		}

		public void run() {
			while (true) {
				try {
					sleep((int)(Math.random()*50) + speed);
					p.removeRectangle();
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}

	public static void main(String[] args) {
		new MyFrame();
	}
}