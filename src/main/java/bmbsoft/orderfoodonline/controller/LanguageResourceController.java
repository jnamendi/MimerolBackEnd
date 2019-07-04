package bmbsoft.orderfoodonline.controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.entities.LanguageResource;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.model.shared.DeleteManyRequest;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.service.LanguageResourceService;
import bmbsoft.orderfoodonline.service.LanguageService;
import bmbsoft.orderfoodonline.util.Constant;

@RestController
@CrossOrigin
public class LanguageResourceController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(LanguageResourceController.class);

	@Autowired
	LanguageResourceService languageResourceService;

	@Autowired
	LanguageService languageSerivce;

	@RequestMapping(value = "/api/language-resource/getAll", method = RequestMethod.GET)
	public ResponseEntity<List<LanguageResource>> getAll() {
		try {
			List<LanguageResource> c = this.languageResourceService.getAll();

			if (c != null && c.size() > 0) {
				return new ResponseEntity<List<LanguageResource>>(c, HttpStatus.OK);
			}
			return new ResponseEntity<List<LanguageResource>>(new ArrayList<LanguageResource>(), HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/api/language-resource/getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<LanguageResource> getById(@PathVariable long id) {
		try {
			LanguageResource c = languageResourceService.getById(id);
			if (c != null) {
				LanguageResource vm = new LanguageResource();
				vm.setName(c.getName());
				vm.setCode(c.getCode());
				return new ResponseEntity<LanguageResource>(vm, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/admin/language-resource/save", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody LanguageResource model) {
		try {
			logger.info(model.getName());
			if (model != null) {
				LanguageResource c = new LanguageResource();
				c.setName(model.getName());
				c.setCode(model.getCode());
				c.setDesc(model.getDesc());
				c.setLanguage(model.getLanguage());
				languageResourceService.create(c);
				return new ResponseEntity<Void>(HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/admin/language-resource/save/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable long id, @RequestBody LanguageResource model) {
		try {
			if (id > 0) {
				LanguageResource c = languageResourceService.getById(id);
				if (c != null) {
					c.setName(model.getName());
					c.setCode(model.getCode());
					c.setStatus(model.getStatus());
					c.setLanguage(model.getLanguage());
					languageResourceService.update(c);
					return new ResponseEntity<Void>(HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/admin/language-resource/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable long id) {
		try {
			LanguageResource c = languageResourceService.getById(id);
			if (c != null) {
				languageResourceService.delete(c);
				return new ResponseEntity<>(HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/admin/language-resource/deleteMany", method = RequestMethod.POST)
	public ResponseEntity<?> deleteMany(@RequestBody DeleteManyRequest cr) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			if (cr == null || cr.getIds().length <= 0) {
				rs.setStatus(8);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, httpStatus);
			}
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < cr.getIds().length; i++) {
				long id = Long.valueOf(cr.getIds()[i]);

				LanguageResource ct = languageResourceService.getById(id);
				if (ct != null) {
					languageResourceService.delete(ct);
				} else {
					s.append("Could not found item: " + id);
				}
			}
			if (s.toString().isEmpty()) {
				rs.setStatus(0);
				rs.setMessage("Delete success.");
				rs.setErrorType(Constant.ErrorTypeCommon.OK);
				httpStatus = HttpStatus.OK;

				return new ResponseEntity<ResponseGet>(rs, httpStatus);
			}
			rs.setStatus(6);
			rs.setMessage(s.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.DELETE_MANY);
			httpStatus = HttpStatus.OK;

			return new ResponseEntity<ResponseGet>(rs, httpStatus);
		} catch (Exception e) {
			logger.info(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
		}
		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

}
