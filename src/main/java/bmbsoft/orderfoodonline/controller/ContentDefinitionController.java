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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Dynamic;

import bmbsoft.orderfoodonline.entities.ContentDefinition;
import bmbsoft.orderfoodonline.entities.Country;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.model.CountryViewModel;
import bmbsoft.orderfoodonline.service.ContentDefinitionService;
import bmbsoft.orderfoodonline.service.CountryService;

@RestController 
public class ContentDefinitionController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(ContentDefinitionController.class);

	@Autowired
	ContentDefinitionService contentDefinitionService;

	@RequestMapping(value = "/api/content-definition/getAll", method = RequestMethod.GET)
	@Transactional
	public ResponseEntity<List<ContentDefinition>> getAll() {
		try {
			List<ContentDefinition> c = this.contentDefinitionService.getAll();

			return new ResponseEntity<List<ContentDefinition>>(c, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/api/content-definition/getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<ContentDefinition> getById(@PathVariable long id) {
		try {
			ContentDefinition c = contentDefinitionService.getById(id);
			if (c != null) {
				ContentDefinition vm = new ContentDefinition();
				vm.setName(c.getName());
				return new ResponseEntity<ContentDefinition>(vm, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/admin/content-definition/create", method = RequestMethod.POST)
	public ResponseEntity<Long> create(@RequestBody ContentDefinition model) {
		try {
			Long id = contentDefinitionService.save(model);
			return new ResponseEntity<Long>(id, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/admin/content-definition/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody ContentDefinition model) {
		try {

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/admin/content-definition/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable long id) {
		try {
			ContentDefinition c = this.contentDefinitionService.getById(id);
			if (c != null) {

				return new ResponseEntity<Void>(HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
