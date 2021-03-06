import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ClientFrame extends JFrame{
	BufferedReader in;
	BufferedWriter out;
	Socket socket = null;
	
	JTextArea statusArea = new JTextArea(7, 40);
	
	public ClientFrame() {
		super("client");
		setSize(600,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		c.setLayout(new FlowLayout());
		
		c.add(new JScrollPane(statusArea), BorderLayout.CENTER);
		statusArea.setEditable(false);
		
		setVisible(true);
		
		setupConnection();
	}
	
	private void setupConnection() {
		try {
			socket = new Socket("localhost", 9911);
			statusArea.append("����");
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			new MsgThread().start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class MsgThread extends Thread{
		public void run(){
			while(true){
				try {
					String msg = in.readLine();
					statusArea.append(msg+"\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args){
		new ClientFrame();		
	}
}
