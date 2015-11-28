package communication;

import java.net.*;
import java.io.*;

public class Server implements Runnable {
	public static void main(String[] args) {
	}

	@Override
	public void run() {
		System.out.println("Server Thread Started...");
		ServerSocket sSock = null;
		Socket cSock = null;
		PrintWriter outPWsock = null;
		BufferedReader inBRstd = null;
		BufferedReader inBRsock = null;

		String portNumStr = "1865";
		int attempts = 3;
		int portNumber = Integer.parseInt(portNumStr);

		try {
			sSock = new ServerSocket(Integer.parseInt(portNumStr));
			cSock = sSock.accept();
			outPWsock = new PrintWriter(cSock.getOutputStream(), true);
			inBRsock = new BufferedReader(new InputStreamReader(cSock.getInputStream()));
			inBRstd = new BufferedReader(new InputStreamReader(System.in));

			String receivedData, sendingData;
			while (((receivedData = inBRsock.readLine()) != null) && (attempts > 0)) {
				attempts--;
				System.out.println(" // server. received from client: > " + receivedData);
				System.out.println(" // server. input text to send");
				sendingData = inBRstd.readLine();
				outPWsock.println(sendingData);
			}
			System.out.println("server. terminate ");
			try {
				// Close all the sockets and streams to prevent memory leaks
				sSock.close();
				cSock.close();
				outPWsock.close();
				inBRsock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port " + portNumber
					+ " or listening for a connection");
			System.out.println(e.getMessage());
			// System.exit(1);
		}

	}
}