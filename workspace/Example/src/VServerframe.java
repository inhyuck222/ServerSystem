import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.*;

public class VServerframe extends JFrame{
	JLabel engText = new JLabel("영어");
	JTextField tfEng = new JTextField(10);
	JLabel korText = new JLabel("한글");
	JTextField tfKor = new JTextField(10);
	JButton storeBtn = new JButton("저장");
	JButton searchBtn = new JButton("검색");
	JButton viewBtn = new JButton("모두 검색");
	JTextArea viewArea = new JTextArea(10,7);
	
	HashMap<String, String> dic = new HashMap<String, String>();
	
	public VServerframe(){
		super("어휘서버");
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
				if(kor == null)kor = "없음";
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
		setVisible(true); //event dispatcher 스레드 생성
	}
	
	public static void main(String args[]){
		new VServerframe();
		
	}
}