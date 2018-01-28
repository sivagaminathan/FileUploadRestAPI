package controller;

import java.util.Set;

import javax.inject.Inject;

import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import model.FileReport;
import service.FileService;

@Controller
@RestController
public class FileUploadController {

	@Autowired
	@Inject
	FileService fileService;

	@GetMapping("/")
	public ModelAndView howUploadPage() {
		return new ModelAndView("welcome");
	}

	@GetMapping(value = "/upload")
	public @ResponseBody ModelAndView showUpload() {
		return new ModelAndView("upload");
	}

	// Returns JSON response of parsed information removing all words that have
	// "blue"
	@PostMapping(value = "/upload")
	public FileReport fileUpload(@RequestParam("file") MultipartFile multipartFile,
			RedirectAttributes redirectAttributes)
			throws Exception, InterruptedException, FileSizeLimitExceededException {
		return fileService.processFile(multipartFile, redirectAttributes);
	}

	// Correct Example: /read/x.txt/
	// Incorrect Example: /read/x.txt
	@RequestMapping(value = "/report/{filename:.+}/", produces = "application/json")
	public FileReport getFileReport(@PathVariable String filename) {
		return fileService.readFileReport(filename.trim());
	}

	// Correct Example: /read/x.txt/blue
	// Incorrect Example: /read/x.txt
	// Displays file details after removing keyword: "blue" (case sensitive)
	@RequestMapping(value = "/report/{filename:.+}/{keyword}", produces = "application/json")
	public FileReport getFileReport(@PathVariable String filename, @PathVariable String keyword) {
		return fileService.readFileReport(filename.trim(), keyword);
	}

	@RequestMapping(value = "/list", produces = "application/json")
	public Set<String> listAllUploadedFiles() {
		return fileService.listAllUploadedFiles();
	}
}