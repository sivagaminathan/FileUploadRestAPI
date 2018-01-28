package exception;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class FileControllerAdvice {

	public static final String DEFAULT_ERROR_VIEW = "welcome";

	private ResponseEntity<VndErrors> error(final Exception exception, final HttpStatus httpStatus,
			final String logRef) {
		final String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
		return new ResponseEntity<>(new VndErrors(logRef, message), httpStatus);
	}

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		// System.out.println(e);

		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e);
		mav.addObject("url", req.getRequestURL());
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<VndErrors> handleError(MaxUploadSizeExceededException e,
			RedirectAttributes redirectAttributes) {
		return error(e, HttpStatus.NOT_FOUND, e.getLocalizedMessage());
	}

	@ExceptionHandler(MultipartException.class)
	public ModelAndView handleError2(HttpServletRequest req, MultipartException e,
			RedirectAttributes redirectAttributes) {
		System.out.println(e);
		return new ModelAndView("welcome");
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<VndErrors> handleError3(IllegalStateException e, RedirectAttributes redirectAttributes) {
		return error(e, HttpStatus.NOT_FOUND, e.getLocalizedMessage());
	}

	@ExceptionHandler(FileSizeLimitExceededException.class)
	public ResponseEntity<VndErrors> uploadedAFileTooLarge(FileSizeLimitExceededException e,
			RedirectAttributes redirectAttributes) {
		return error(e, HttpStatus.NOT_FOUND, e.getLocalizedMessage());
	}
}