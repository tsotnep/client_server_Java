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
		String hostName = "127.0.0.1";
		String portNumbStr = "1234";
		sendData(hostName, portNumbStr);
		

	}

	public void sendData(String hostN, String portN) {
		System.out.println("Client Thread Started...");
		Socket cSock = null;
		PrintWriter outPWsock = null;
		BufferedReader inBRstd = null;
		BufferedReader inBRsock = null;

		int attempts = 3;
		int portNumber = Integer.parseInt(portN);

		try {
			cSock = new Socket(hostN, portNumber);
			cSock.setSoTimeout(5000);
			outPWsock = new PrintWriter(cSock.getOutputStream(), true);
			inBRsock = new BufferedReader(new InputStreamReader(cSock.getInputStream()));
			inBRstd = new BufferedReader(new InputStreamReader(System.in));

			String receivedData;
			receivedData = inBRsock.readLine();
			System.out.println("received : " + receivedData);
			outPWsock.println("Hello IAG0010Server");

			while (((receivedData = inBRsock.readLine()) != null) && (attempts > 0)) {
				attempts--;
				System.out.println("received::::" + receivedData);
			}
			System.out.println("client. terminate ");

			try {
				// Close all the sockets and streams to prevent memory leaks
				cSock.close();
				outPWsock.close();
				inBRsock.close();
				inBRstd.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostN);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + portN);
			System.exit(1);
		}

	}
}