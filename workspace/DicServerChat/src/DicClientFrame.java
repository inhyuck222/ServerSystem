import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class DicClientFrame extends JFrame {
	BufferedReader in;
	BufferedWriter out;
	Socket socket = null;
	JTextField eng = new JTextField(10);
	JTextField kor = new JTextField(10);
	JButton storeBtn = new JButton("저장");
	JButton searchBtn = new JButton("검색");

	public DicClientFrame() {
		super("클라이언트");
		setSize(600, 300);
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		c.add(new JLabel("영어"));
		c.add(eng);
		c.add(new JLabel("한글"));
		c.add(kor);
		kor.setEditable(false);
		c.add(searchBtn);
		searchBtn.addActionListener(new MyActionListner());

		setVisible(true);
		
		setupConnection();
	}

	private void setupConnection() {
		try {
			socket = new Socket("localhost", 9998);
			System.out.println("연결");
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class MyActionListner implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String eText = eng.getText();
			try {
				out.write(eText);
				out.flush();
				String kText = in.readLine();
				kor.setText(kText);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}	
	}

	public static void main(String[] args) {
		new DicClientFrame();
	}

	

}
