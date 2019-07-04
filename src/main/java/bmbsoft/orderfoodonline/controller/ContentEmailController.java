package bmbsoft.orderfoodonline.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.jboss.logging.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bmbsoft.orderfoodonline.entities.ContentEmail;
import bmbsoft.orderfoodonline.model.ContentEmaiLViewModel;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.service.ContentEmailService;
import bmbsoft.orderfoodonline.service.RestaurantService;
import bmbsoft.orderfoodonline.service.UserService;
import bmbsoft.orderfoodonline.util.Constant;

@RestController
@CrossOrigin
public class ContentEmailController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(ContentEmailController.class);

	@Autowired
	ContentEmailService fs;

	@RequestMapping(value = "/api/content-email-template/getByType/{type}/{langCode}", method = RequestMethod.GET)
	@Transactional
	public ResponseEntity<?> getAll(@RequestParam("type") int type, @RequestParam("langCode") String langCode) {
		ResponseGet rs = new ResponseGet();
		try {
			ContentEmaiLViewModel f = fs.getByType(type, langCode);
			if (null == f) {
				rs.setStatus(8);
				rs.setMessage("File not found");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setMessage("Ok");
			rs.setContent(f);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(8);
			rs.setMessage("File not found");
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

}
