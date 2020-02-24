import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {

		ArrayList<String> paths = new ArrayList<>();
		paths = Util.getPathsFromFile(args[0]);
		CountDownLatch doneSignal = new CountDownLatch(paths.size());
		FileWriter writer = new FileWriter(args[1]);
		Thread keyListener = new Thread(new KeyListener());

		keyListener.start();
		Util.setDisplay();

		for (String path : paths) {
			(new FilesCounter(path, writer, doneSignal)).start();
		}
		doneSignal.await(); //block until the current count reaches zero due to invocations of the
							// countDown() method
		if (Util.interrupted) {
			System.out.println("Stoped!");
		}
		System.exit(0);
	}
}
