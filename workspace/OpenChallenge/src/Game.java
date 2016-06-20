import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Game extends JFrame{
	JLabel firstLabel  = new JLabel("1");
	JLabel secondLabel  = new JLabel("2");
	JLabel thirdLabel  = new JLabel("3");
	
	JTextField tf = new JTextField(10);

	public Game(){
		super("야미");
		setSize(300,300);
		
		Container c = this.getContentPane();
		c.setLayout(new FlowLayout());
		c.add(firstLabel);
		c.add(secondLabel);
		c.add(thirdLabel);
		c.add(tf);
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				int a, b, c;
				a = 0; b = 1; c = 2;
				for(int i=0; i<3; i++){
					int x = (int)(Math.random()*5);
					if(i==0){
						firstLabel.setText(x+"");
						a = x;
					}
					else if(i == 1){
						secondLabel.setText(x+"");
						b = x;
					}
					else {
						thirdLabel.setText(x+"");
						c = x;
					}
				}
				if(a == b && a ==c){
					tf.setText("축하합니다");
				} else {
					tf.setText("떙입니다.");
				}
			}
		});
		setVisible(true);
	}
	
	public static void main(String args[]){	
		new Game();
	}
}
