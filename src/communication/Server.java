package communication;

import java.net.*;
import java.io.*;

public class Server implements Runnable {
	public static void main(String[] args) {
	}

	@Override
	public void run() {
		Boolean work = true;
		System.out.println("Server Thread Started...");
		ServerSocket sSock = null;
		Socket cSock = null;
		PrintWriter outPWsock = null;
		BufferedReader inBRsock = null;

		String portNumStr = "1867";
		int portNumber = Integer.parseInt(portNumStr);

		try {
			sSock = new ServerSocket(Integer.parseInt(portNumStr));
			cSock = sSock.accept();
			outPWsock = new PrintWriter(cSock.getOutputStream(), true);
			inBRsock = new BufferedReader(new InputStreamReader(cSock.getInputStream()));

			while (work) {
				String receivedData = inBRsock.readLine();
				if (receivedData != null) {
					System.out.println(" // server. received> " + receivedData);
					if (receivedData.equals("stop")) work = false;
				}
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