import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class FilesCounter extends Thread {

	private int filesNumber = 0;
	private String inputPath;
	private FileWriter outputFile;
	private CountDownLatch doneSignal;

	public FilesCounter(String inputPath, FileWriter outputFile, CountDownLatch doneSignal) {
		this.inputPath = inputPath;
		this.outputFile = outputFile;
		this.doneSignal = doneSignal;
	}

	public void findFiles(String path) {
		File folder = new File(path);
		File[] folderHierarchy = folder.listFiles();
		for (File file : folderHierarchy) {
			if (Util.interrupted == true) {
				break;
			}
			if (file.isDirectory()) {
				findFiles(file.getPath());
			} else {
				filesNumber++;
			}
		}
	}

	@Override
	public void run() {
		findFiles(inputPath);
		Util.showResults(filesNumber, inputPath);
		try {
			Util.writeResultsToFile(outputFile, inputPath, filesNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}
		doneSignal.countDown();
	}
}