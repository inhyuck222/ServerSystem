import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.*;

public class VServerframe extends JFrame{
	JLabel engText = new JLabel("����");
	JTextField tfEng = new JTextField(10);
	JLabel korText = new JLabel("�ѱ�");
	JTextField tfKor = new JTextField(10);
	JButton storeBtn = new JButton("����");
	JButton searchBtn = new JButton("�˻�");
	JButton viewBtn = new JButton("��� �˻�");
	JTextArea viewArea = new JTextArea(10,7);
	
	HashMap<String, String> dic = new HashMap<String, String>();
	
	public VServerframe(){
		super("���ּ���");
		setSize(300, 300);
		
		Container c = this.getContentPane();
		c.setLayout(new FlowLayout());
		c.add(engText);
		c.add(tfEng); 
		c.add(korText);
		c.add(tfKor);
		c.add(storeBtn);
		c.add(searchBtn);
		c.add(viewArea);
		c.add(viewBtn);
		c.add(new JScrollPane(viewArea));
		
		storeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String eng = tfEng.getText();
				String kor = tfKor.getText();
				dic.put(eng, kor);
			}
		});
		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String eng = tfEng.getText();
				String kor = dic.get(eng);
				if(kor == null)kor = "����";
				tfKor.setText(kor);
			}
		});
		viewBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				viewArea.setText("");
				Set<String> keys = dic.keySet();
				Iterator<String> it = keys.iterator();
				while(it.hasNext()){
					String eng = it.next();
					String kor = dic.get(eng);
					viewArea.setText(viewArea.getText() + eng + " : " + kor + "\n");				
				}
			}
		});
		setVisible(true); //event dispatcher ������ ����
	}
	
	public static void main(String args[]){
		new VServerframe();
		
	}
}