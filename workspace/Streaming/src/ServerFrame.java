import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerFrame extends JFrame{
	JTextField msgField = new JTextField(10);
	JTextArea statusArea = new JTextArea(7, 40);
	
	public ServerFrame(){
		super("server");
		setSize(600,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		c.setLayout(new FlowLayout());
		
		c.add(new JScrollPane(statusArea), BorderLayout.CENTER);
		c.add(msgField, BorderLayout.NORTH);
		statusArea.setEditable(false);
		
		setVisible(true);
		
		msgField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				wakeupForSend();
				
			}
		});
		
		startService();
	}
	
	private void startService(){
		new ServerThread().start();
	}
	
	public synchronized void wakeupForSend(){
		statusArea.append("befor notify\n");
		this.notifyAll();
		statusArea.append("after notify\n");
	}
	
	public synchronized void waitForWakeup(){
		try {
			statusArea.append("b wait\n");
			this.wait();
			statusArea.append("a wait\n");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	class ServiceThread extends Thread {
		Socket socket;

		public ServiceThread(Socket socket) {
			this.socket = socket;
		}
		
		public void run() {
			try {
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

				while (true) {
					waitForWakeup();
					statusArea.append("b write\n");
					out.write(msgField.getText()+"\n");
					out.flush();
					statusArea.append("a write\n");
				}
				
			} catch (IOException e) {
				System.out.println("Ŭ���̾�Ʈ ����");
				e.printStackTrace();
			}
		}
	}
	
	class ServerThread extends Thread {

		public void run() {
			ServerSocket listner = null;
			Socket socket = null;
			try {
				listner = new ServerSocket(9911);
			} catch (IOException e) {
				e.printStackTrace();
			}

			while (true) {
				try {
					socket = listner.accept();
					statusArea.append("Ŭ���̾�Ʈ ����\n");
					new ServiceThread(socket).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args){
		new ServerFrame();
	}

}
