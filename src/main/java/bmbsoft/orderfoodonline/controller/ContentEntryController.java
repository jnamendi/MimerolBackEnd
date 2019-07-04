package bmbsoft.orderfoodonline.controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

import org.apache.catalina.connector.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Dynamic;

import bmbsoft.orderfoodonline.entities.ContentEntry;
import bmbsoft.orderfoodonline.entities.Country;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.model.CountryViewModel;
import bmbsoft.orderfoodonline.service.ContentEntryService;
import bmbsoft.orderfoodonline.service.CountryService;

@RestController 
@CrossOrigin
public class ContentEntryController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(ContentEntryController.class);

	@Autowired
	ContentEntryService ContentEntryService;

	@RequestMapping(value = "/api/content-entry/getAll", method = RequestMethod.GET)
	@Transactional
	public ResponseEntity<List<ContentEntry>> getAll() {
		try {
			List<ContentEntry> c = this.ContentEntryService.getAll();

			return new ResponseEntity<List<ContentEntry>>(c, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/api/content-entry/getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<ContentEntry> getById(@PathVariable long id) {
		try {
			ContentEntry c = ContentEntryService.getById(id);
			if (c != null) {
				ContentEntry vm = new ContentEntry();
				// vm.setName(c.getName());
				return new ResponseEntity<ContentEntry>(vm, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/api/content-entry/getByCondefIdAndLang/{condefId}/{langId}/{code}", method = RequestMethod.GET)
	public ResponseEntity<ContentEntry> getByDeAndLang(@PathVariable long condefId, @PathVariable long langId,
			@PathVariable String code) {
		try {
			ContentEntry c = ContentEntryService.getByContentDefAndLang(condefId, langId, code);
			if (c != null) {
				ContentEntry vm = new ContentEntry();
				// vm.setName(c.getName());
				vm.setContentEntryId(c.getContentEntryId());
				// vm.setContentDefinition(c.getContentDefinition());
				// vm.setLanguage(c.getLanguage());
				return new ResponseEntity<ContentEntry>(vm, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/admin/content-entry/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody ContentEntry model) {
		try {

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/admin/content-entry/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable long id) {
		try {
			ContentEntry c = this.ContentEntryService.getById(id);
			if (c != null) {

				return new ResponseEntity<Void>(HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
