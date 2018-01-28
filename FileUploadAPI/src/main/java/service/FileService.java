package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.FileReport;

@SuppressWarnings("unused")
@Service
public class FileService {

	// 10 MB is 1048576 bytes
	private static final long TEN_MEGABYTE = 10485760;

	// Destination Folder for files uploaded
	private static final String UPLOAD_FOLDER = "D://test//";

	public FileService() {
	}

	// Saves uploaded file to disk
	@Async
	public void writeToFile(Path filepath, byte[] filebytes) {
		try {
			Files.write(filepath, filebytes);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// For End point : /upload
	public FileReport processFile(MultipartFile multipartFile, RedirectAttributes redirectAttributes)
			throws Exception, InterruptedException, FileSizeLimitExceededException {

		FileReport fileReport = new FileReport();

		// Validate type
		boolean type = FilenameUtils.isExtension(multipartFile.getOriginalFilename().toLowerCase(), ".txt");
		if (type) {
			System.out.println("Not a valid extension");
			return fileReport;
		}

		// Validate empty
		if (multipartFile.isEmpty()) {
			System.out.println("File is Empty");
		}

		// Validate size
		if (multipartFile.getSize() > TEN_MEGABYTE) {
			System.out.println("File size exceeds");
			return fileReport;
		}

		try {
			Path filePath = Paths.get(UPLOAD_FOLDER + multipartFile.getOriginalFilename());
			writeToFile(filePath, multipartFile.getBytes());
			fileReport = new FileReport(UPLOAD_FOLDER + multipartFile.getOriginalFilename());
			fileReport.save();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fileReport;
	}

	// For End point : /report/filename.txt/
	public FileReport readFileReport(String filename) {
		FileReport fileReport = new FileReport();

		try {
			// Check if meta file exists
			File file = new File(filename + "_meta.json");
			BufferedReader br = new BufferedReader(new FileReader(file));
			ObjectMapper mapper = new ObjectMapper();
			fileReport = mapper.readValue(br, FileReport.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return fileReport;
	}

	// For End point : /report/filename.txt/keyword
	public FileReport readFileReport(String filename, String keyword) {
		FileReport fileReport = new FileReport();

		try {
			// Check if meta file exists
			File file = new File(filename + "_meta.json");
			BufferedReader br = new BufferedReader(new FileReader(file));
			ObjectMapper mapper = new ObjectMapper();
			fileReport = mapper.readValue(br, FileReport.class);

			Iterator<String> keysItr = fileReport.wordOccurence.keySet().iterator();
			while (keysItr.hasNext()) {
				String key = keysItr.next();
				if (key.matches("[0-9A-Za-z]*" + keyword + "[0-9A-Za-z]*")) {
					fileReport.wordCount = fileReport.wordCount - fileReport.wordOccurence.get(key);
					keysItr.remove();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return fileReport;
	}

	// For End point: /list
	public Set<String> listAllUploadedFiles() {
		File folder = new File(UPLOAD_FOLDER);
		File[] listOfFiles = folder.listFiles();
		Set<String> fileListSet = new HashSet<String>();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				fileListSet.add(listOfFiles[i].getName());
				System.out.println("File " + listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				fileListSet.add(listOfFiles[i].getName());
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
		return fileListSet;
	}

	// Only for TEST purposes
	public Set<String> listAllUploadedFiles(String testFolder) {
		File folder = new File(testFolder);
		File[] listOfFiles = folder.listFiles();
		Set<String> fileListSet = new HashSet<String>();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				fileListSet.add(listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				fileListSet.add(listOfFiles[i].getName());
			}
		}
		return fileListSet;
	}
}