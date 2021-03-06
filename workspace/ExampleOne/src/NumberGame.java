import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class NumberGame extends JFrame{
	JLabel[] number = new JLabel[10];
	int next = 0;
	
	public NumberGame(){
		super("����");
		setSize(400, 400);
		
		Container c = this.getContentPane();
		c.setLayout(null);
		for(int i=0; i<number.length; i++){
			number[i] = new JLabel(i+"");
			number[i].setSize(15, 15);
			number[i].setLocation((int)(Math.random()*300), (int)(Math.random()*300));
			
			number[i].addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					JLabel me  = (JLabel)e.getSource();
					int n = Integer.parseInt(me.getText());
					if(n != next)return;
					next++;
					
					Container parent = me.getParent();
					parent.remove(me);
					parent.repaint();
				}
			});
			
			c.add(number[i]);
		}
		setVisible(true);
	}
	
	public static void main(String[] args){
		new NumberGame();
	}
}