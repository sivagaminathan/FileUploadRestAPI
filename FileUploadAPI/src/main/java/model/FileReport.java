package model;

import java.io.File;
import java.util.Hashtable;
import java.util.Scanner;

import org.springframework.scheduling.annotation.Async;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

// Maintains File Meta data Information
public class FileReport {

	public String fileName;
	public long wordCount;
	public Hashtable<String, Integer> wordOccurence = new Hashtable<String, Integer>();
	public long fileSize;

	public FileReport() {
	}

	public FileReport(String filename) {
		File file = new File(filename);
		Hashtable<String, Integer> h = new Hashtable<String, Integer>();
		long count = 0;

		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNext()) {
				String nextword = sc.next();
				if (!h.containsKey(nextword)) {
					h.put(nextword, 1);
				} else {
					h.put(nextword, h.get(nextword) + 1);
				}
				count++;
			}
			sc.close();

		} catch (Exception e) {
			System.out.println("Exception occured a " + e);
		}

		this.fileName = file.getName();
		this.wordCount = count;
		this.wordOccurence = h;
		this.fileSize = file.length();
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String filename) {
		this.fileName = filename;
	}

	public long getWordCount() {
		return wordCount;
	}

	public void setWordCount(long wordcount) {
		this.wordCount = wordcount;
	}

	public Hashtable<String, Integer> getWordOccurence() {
		return wordOccurence;
	}

	public void setWordOccurence(Hashtable<String, Integer> wordOccurence) {
		this.wordOccurence = wordOccurence;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long filesize) {
		this.fileSize = filesize;
	}

	public void create() {
	}

	// Saves file meta data to disk
	@Async
	public void save() {
		ObjectMapper mapper = new ObjectMapper();

		try {
			mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			// uploadedfilename_meta.json
			mapper.writeValue(new File(getFileName() + "_meta" + ".json"), this);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}