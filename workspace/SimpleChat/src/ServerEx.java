import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerEx {
	public static void main(String[] args) {
		BufferedReader in;
		BufferedReader stdIn;
		BufferedWriter out;
		ServerSocket listner = null;
		Socket socket = null;
		try {
			System.out.println("welcome");
			listner = new ServerSocket(9998);
			socket = listner.accept();
			System.out.println("Conneted");
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			while(true){
				String inputMessage = in.readLine();
				if(inputMessage.equalsIgnoreCase("bye"))break;
				System.out.print(inputMessage);
				String outputMessage = stdIn.readLine();
				out.write("����>"+outputMessage+"\n");
				out.flush();
			}
		} catch (IOException e) {
			return;
		}
		finally {
			try {
				listner.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}