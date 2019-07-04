package bmbsoft.orderfoodonline.model;

import org.springframework.web.multipart.MultipartFile;

public class UploadModel {

    private MultipartFile[] files;

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}
}
