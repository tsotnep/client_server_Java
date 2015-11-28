package communication;

public class Main {
	public static void main(String[] args) {
		Thread t1 = (new Thread(new Server()));
		Thread t2 = (new Thread(new Client()));

//		t1.start();
		t2.start();

		try {
			t2.join();
			System.out.println("Client Thread Have Ended");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			t1.join();
			System.out.println("Server Thread Have Ended");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
