import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Util {

	public static boolean interrupted = false;
	private static int recordsCounter = 0;
	private static String column1 = "Номер записи";
	private static String column2 = "Количество найденых файлов";
	private static String column3 = "Исходный путь для поиска количества файлов";
	private static String lineFormat = String.format(
			"%-" + (column1.length() + 1) + "s%-" + (column2.length() + 1) + "s%-" + (column3.length() + 1) + "s%s",
			"+", "+", "+", "+").replace(' ', '-');

	public static ArrayList<String> getPathsFromFile(String fileName) throws FileNotFoundException {
		ArrayList<String> paths = new ArrayList<>();
		File input = new File(fileName);
		Scanner scanner = new Scanner(input, "utf8");
		while (scanner.hasNextLine()) {
			paths.add(scanner.nextLine());
		}
		scanner.close();
		return paths;
	}

	public static void setDisplay() {
		System.out.println("Press <Esc> to stop:");
		System.out.println(lineFormat);
		System.out.printf(
				"%4$s%" + column1.length() + "s%4$s%" + column2.length() + "s%4$s%" + column3.length() + "s%4$s%n",
				column1, column2, column3, "|");
		System.out.println(lineFormat);
	}

	public static void showResults(int filesNumber, String path) {
		System.out.printf(
				"%4$s%" + column1.length() + "s%4$s%" + column2.length() + "s%4$s%" + column3.length() + "s%4$s%n",
				++recordsCounter, filesNumber, path, "|");
		System.out.println(lineFormat);
	}

	public static void writeResultsToFile(FileWriter outputFile, String inputPath, int filesNumber) throws IOException {
		outputFile.write(inputPath + ";" + filesNumber + System.lineSeparator());
		outputFile.flush();
	}

	public static void checkThreadisAlive(Thread thread) {
		if (thread.isAlive()) {
			checkThreadisAlive(thread);
		} else if (!thread.isAlive()) {
			System.exit(0);
		}
	}
}