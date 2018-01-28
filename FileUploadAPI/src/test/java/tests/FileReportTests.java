package tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.util.Hashtable;

import org.junit.Test;

import model.FileReport;

public class FileReportTests {

	// PASS SCENARIOS
	@Test
	public void testFileReport() {
		String filename = "test.txt";
		Hashtable<String, Integer> actualHashTable = new Hashtable<String, Integer>();
		actualHashTable.put("Hello", 1);
		actualHashTable.put("World", 1);
		FileReport f = new FileReport(filename);
		assertEquals("test.txt", f.getFileName());
		assertEquals(2, f.getWordCount());
		assertEquals(12, f.getFileSize());
		assertThat(actualHashTable, is(f.getWordOccurence()));
	}

	@Test
	public void testFileFileNamePositive() {
		FileReport f = new FileReport();
		f.setFileName("test.txt");
		assertEquals("test.txt", f.getFileName());
	}

	@Test
	public void testFileSizePositive() {
		FileReport f = new FileReport();
		f.setFileSize(40);
		assertEquals(40, f.getFileSize());
	}

	@Test
	public void testFileWordCountPositive() {
		FileReport f = new FileReport();
		f.setWordCount(20);
		assertEquals(20, f.getWordCount());
	}

	@Test
	public void testWordOccurencePositive() {
		FileReport f = new FileReport();
		Hashtable<String, Integer> actualHashTable = new Hashtable<String, Integer>();
		Hashtable<String, Integer> expectedHashTable = new Hashtable<String, Integer>();
		actualHashTable.put("hello", 2);
		actualHashTable.put("world", 2);
		actualHashTable.put("fun", 1);
		actualHashTable.put("5", 4);

		expectedHashTable.put("world", 2);
		expectedHashTable.put("fun", 1);
		expectedHashTable.put("5", 4);
		expectedHashTable.put("hello", 2);

		f.setWordOccurence(actualHashTable);

		assertThat(actualHashTable.size(), is(4));
		assertThat(actualHashTable, is(expectedHashTable));
		assertEquals(actualHashTable.containsKey("5"), true);
		assertEquals(actualHashTable.containsValue(4), true);
		assertEquals(actualHashTable.containsKey("worlds"), false);
		assertEquals(actualHashTable.contains(0), false);
	}

	// TEST FOR GETFILE INFO
	@Test
	public void testFileFileNameNegative() {
		FileReport f = new FileReport();
		f.setFileName("test.txt");
		assertNotEquals("FileName Negative", "testt.txt", f.getFileName());
	}

	@Test
	public void testFileSizeNegative() {
		FileReport f = new FileReport();
		f.setFileSize(40);
		assertNotEquals("FileSize Negative", 1, f.getFileSize());
	}

	@Test
	public void testFileWordCountNegative() {
		FileReport f = new FileReport();
		f.setWordCount(20);
		assertNotEquals("WordCount Negative", 21, f.getWordCount());
	}

	@Test
	public void testWordOccurenceNegative() {
		FileReport f = new FileReport();
		Hashtable<String, Integer> actualHashTable = new Hashtable<String, Integer>();
		Hashtable<String, Integer> expectedHashTable = new Hashtable<String, Integer>();
		actualHashTable.put("hello", 2);
		actualHashTable.put("b", 2);
		actualHashTable.put("fun", 1);
		actualHashTable.put("5", 4);

		expectedHashTable.put("world", 2);
		expectedHashTable.put("fun", 1);
		expectedHashTable.put("5", 4);
		expectedHashTable.put("hello", 2);

		f.setWordOccurence(actualHashTable);

		assertThat(actualHashTable.size(), is(4));
		assertNotEquals("Not Equals", actualHashTable, expectedHashTable);
		assertEquals(actualHashTable.containsKey("5"), true);
		assertEquals(actualHashTable.containsValue(4), true);
		assertEquals(actualHashTable.containsKey("worlds"), false);
		assertEquals(actualHashTable.contains(0), false);
	}
}