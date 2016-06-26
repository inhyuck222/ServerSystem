import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ClientFrame extends JFrame  implements ActionListener {
	JTextField serverMessage = new JTextField();
	BufferedReader in;
	BufferedReader stdIn;
	BufferedWriter out;
	Socket socket = null;
	Receiver receiver = new Receiver();
	
	public ClientFrame() {
		super("client");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		c.add(serverMessage, BorderLayout.SOUTH);
		c.add(new JScrollPane(receiver), BorderLayout.CENTER);
		serverMessage.addActionListener(this);
		
		setSize(300, 300);
		setVisible(true);
		
		setupConnection();	
		
		Thread th = new Thread(receiver);
		th.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		JTextField tf = (JTextField)e.getSource();
		try {
			out.write(tf.getText()+"\n");
			receiver.append(tf.getText()+"\n");
			out.flush();
			tf.setText("");
		} catch (IOException e1) {
			
		}
	}
	
	public void setupConnection() {
		try {
			System.out.println("Welcome");
			socket = new Socket("localhost", 9998);
			System.out.println("Connected");
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			return;
		}
	}
	
	class Receiver extends JTextArea implements Runnable {

		public Receiver() {
			this.setEditable(false);
		}

		public void run() {
			while(true){
				try {
					String inputMessage = in.readLine();
					this.append(inputMessage+"\n");
				} catch (IOException e) {
					System.out.println("���� ����");
					return;
				}
			}
		}

	}

	public static void main(String[] args) {
		new ClientFrame();
	}
}