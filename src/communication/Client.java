package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {
	public static void main(String[] args) {
	}

	@Override
	public void run() {
		Boolean work = true;
		String hostName = "127.0.0.1";
		String portNumbStr = "1867";
		String data = new String("");
		System.out.println("Client Thread Started...");
		BufferedReader inBRstd = new BufferedReader(new InputStreamReader(System.in));

		while (work) {
			System.out.println("type text that you want to send to server");
			try {
				data = inBRstd.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (data.equals("stop")) work = false;
			
			sendData(hostName, portNumbStr, data);
		}
		System.out.println("client. terminate ");

	}

	public void sendData(String hostN, String portN, String data) {
		Socket cSock = null;
		PrintWriter outPWsock = null;
		int portNumber = Integer.parseInt(portN);

		try {
			cSock = new Socket(hostN, portNumber);
			outPWsock = new PrintWriter(cSock.getOutputStream(), true);

			outPWsock.println(data);

			try {
				// Close all the sockets and streams to prevent memory leaks
				cSock.close();
				outPWsock.close();
				// inBRsock.close();
			} catch (IOException e) {
				System.out.println("CLIENT");
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostN);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostN);
			System.exit(1);
		}

	}
}