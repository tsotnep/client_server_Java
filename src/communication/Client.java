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
		System.out.println("Client Thread Started...");
		Socket cSock = null;
		PrintWriter outPWsock = null;
		BufferedReader inBRstd = null;
		BufferedReader inBRsock = null;

		String hostName = "127.0.0.1";
		String portNumbStr = "1865";
		int attempts = 3;
		int portNumber = Integer.parseInt(portNumbStr);

		try {
			cSock = new Socket(hostName, portNumber);
			outPWsock = new PrintWriter(cSock.getOutputStream(), true);
			inBRsock = new BufferedReader(new InputStreamReader(cSock.getInputStream()));
			inBRstd = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("type any command to start, note: after that, each side can reply 3 times");
			outPWsock.println(inBRstd.readLine());// start_conversation

			String receivedData, sendingData;
			while (((receivedData = inBRsock.readLine()) != null) && (attempts > 0)) {
				attempts--;
				System.out.println(" // client. received from server: > " + receivedData);
				System.out.println(" // client. input text to send");
				sendingData = inBRstd.readLine();
				outPWsock.println(sendingData);
			}

			System.out.println("client. terminate ");
			try {
				// Close all the sockets and streams to prevent memory leaks
				cSock.close();
				outPWsock.close();
				// inBRsock.close();
				inBRstd.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}

	}
}