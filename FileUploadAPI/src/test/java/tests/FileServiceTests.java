package tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import model.FileReport;
import service.FileService;

public class FileServiceTests {
	// POSITIVE CASES
	@Test
	public void writeToFileTestPositive() {
		@SuppressWarnings("unused")
		String filecontents = "";
		try {
			FileService fileService = new FileService();
			Path filepath = Paths.get(System.getProperty("user.dir") + "test.txt");
			byte[] bytes = "Hello World".getBytes();
			fileService.writeToFile(filepath, bytes);
			String line;
			File file = new File(filepath.toString());
			Scanner sc = new Scanner(file);

			Pattern pattern = Pattern.compile("Hello World");
			while ((line = sc.nextLine()) != null) {
				Matcher m = pattern.matcher(line);
				if (m.matches()) {
					assertEquals(1, 1);
				} else {
					assertEquals(1, 0);
				}
			}
			sc.close();
		} catch (Exception e) {

		}
	}

	@Test
	public void readFileReportTestPositive() {
		String filename = "test.txt";
		Hashtable<String, Integer> actualHashTable = new Hashtable<String, Integer>();
		actualHashTable.put("Hello", 1);
		actualHashTable.put("World", 1);
		FileService fileService = new FileService();

		FileReport f = fileService.readFileReport(filename);

		assertEquals("test.txt", f.getFileName());
		assertEquals(2, f.getWordCount());
		assertEquals(12, f.getFileSize());
		assertThat(actualHashTable, is(f.getWordOccurence()));
	}

	@Test
	public void readFileReportKeywordTestPositive() {
		String filename = "test_blue.txt";
		String keyword = "blue";

		Hashtable<String, Integer> actualHashTable = new Hashtable<String, Integer>();
		actualHashTable.put("Hello", 1);
		actualHashTable.put("blu", 1);
		actualHashTable.put("World", 1);
		FileService fileService = new FileService();

		FileReport f = fileService.readFileReport(filename, keyword);

		assertEquals("test_blue.txt", f.getFileName());
		assertEquals(3, f.getWordCount());
		assertEquals(35, f.getFileSize());
		assertThat(actualHashTable, is(f.getWordOccurence()));

	}

	@Test
	public void listAllUploadedFilesTestPositive() {
		Set<String> fileSet = new HashSet<String>();
		// These are Files in my Local user directory
		fileSet.add("1.txt");
		fileSet.add("2.txt");
		fileSet.add("test.txt");
		fileSet.add("test_blue.txt");
		fileSet.add("java");
		fileSet.add("test.txt_meta.json");
		fileSet.add("test_blue.txt_meta.json");

		String folder = System.getProperty("user.dir") + "\\src\\test\\";
		FileService fileService = new FileService();
		Set<String> listOfFiles = fileService.listAllUploadedFiles(folder);

		assertThat(fileSet, is(listOfFiles));
	}

	// NEGATIVE CASES
	@Test
	public void writeToFileTestNegative() {
		try {
			FileService fileService = new FileService();
			Path filepath = Paths.get(System.getProperty("user.dir") + "/src/test/test.txt");
			byte[] bytes = "Hello World".getBytes();
			fileService.writeToFile(filepath, bytes);
			String line;
			File file = new File(filepath.toString());
			Scanner sc = new Scanner(file);

			Pattern pattern = Pattern.compile(" Hello World ");
			while ((line = sc.nextLine()) != null) {
				Matcher m = pattern.matcher(line);
				if (m.matches()) {
					assertEquals(1, 0);
				} else {
					assertEquals(1, 1);
				}
			}
			sc.close();
		} catch (Exception e) {

		}
	}

	@Test
	public void readFileReportTestNegative() {
		String filename = "test.txt";
		Hashtable<String, Integer> testHashTable = new Hashtable<String, Integer>();
		testHashTable.put("Hello", 1);
		testHashTable.put("Worlds", 1);
		testHashTable.put("Not", 1);
		FileService fileService = new FileService();

		FileReport f = fileService.readFileReport(filename);
		// System.out.println(f.getFileName());

		assertNotEquals("Not Equals", "test.txt_meta.json", f.getFileName());
		assertNotEquals("Not Equals", 3, f.getWordCount());
		assertNotEquals("Not Equals", 1000, f.getFileSize());
		assertNotEquals("Not Equals", testHashTable, f.getWordOccurence());
	}

	@Test
	public void listAllUploadedFilesTestNegative() {
		Set<String> fileSet = new HashSet<String>();
		// These are Files in my Local user directory
		fileSet.add("1.txt");
		fileSet.add("2.txt");
		fileSet.add("test.txt");
		fileSet.add("test.txt_meta.json");

		String folder = System.getProperty("user.dir") + "\\src\\test\\";
		FileService fileService = new FileService();
		Set<String> listOfFiles = fileService.listAllUploadedFiles(folder);

		assertNotEquals("Folder Contents dont match ", fileSet, listOfFiles);
	}

}