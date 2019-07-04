package bmbsoft.orderfoodonline.controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bmbsoft.orderfoodonline.entities.Media;
import bmbsoft.orderfoodonline.model.UploadModel;
import bmbsoft.orderfoodonline.model.sModel;
import bmbsoft.orderfoodonline.service.MediaService;
import bmbsoft.orderfoodonline.util.CommonHelper;

@RestController
@CrossOrigin
public class UploadController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@Autowired
	MediaService mediaService;
 

	@PostMapping("/admin/testUploadImage")
	public ResponseEntity<?> testUploadImage(MultipartFile file) {
		logger.debug("Single file upload!");
		try {

			String u = CommonHelper.doUpload("", file);
			return new ResponseEntity<>(u, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/admin/editUploadImage")
	public ResponseEntity<?> editUploadImage(String oldFile, MultipartFile file) {
		logger.debug("Single file upload!");
		try {

			String u = CommonHelper.doUpload(oldFile, file);
			return new ResponseEntity<>(u, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
		}
	}
 
	private static BufferedImage resize(BufferedImage img, int height, int width) {
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}

	@GetMapping(value = "/api/upload/image/{mediaId}")
	@Async
	public ResponseEntity<byte[]> getImage(@PathVariable @Validated Long mediaId) throws IOException {
		Media m = mediaService.getById(mediaId);
		if (m == null)
			return null;
		String urlPath = environment.getProperty("path.url.upload");
		String imgUrl = urlPath + "/" + m.getImageUrl();
		File imgPath = new File(imgUrl);

		byte[] image = Files.readAllBytes(imgPath.toPath());
		// HttpHeaders headers = new HttpHeaders();
		// headers.setContentType(MediaType.IMAGE_PNG);
		// headers.setContentLength(image.length);

		return new ResponseEntity<>(image, HttpStatus.OK);
	}

}
