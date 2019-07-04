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

import bmbsoft.orderfoodonline.entities.Attribute;
import bmbsoft.orderfoodonline.entities.AttributeGroup;
import bmbsoft.orderfoodonline.entities.ContentEntry;
import bmbsoft.orderfoodonline.entities.Country;
import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.model.AttributeGroupViewModel;
import bmbsoft.orderfoodonline.model.ContentDefModel;
import bmbsoft.orderfoodonline.model.CountryViewModel;
import bmbsoft.orderfoodonline.model.LanguageViewModel;
import bmbsoft.orderfoodonline.model.UserViewModel;
import bmbsoft.orderfoodonline.model.shared.AttributeGroupRequest;
import bmbsoft.orderfoodonline.model.shared.AttributeGroupResponse;
import bmbsoft.orderfoodonline.model.shared.DeleteManyRequest;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.service.AttributeGroupService;
import bmbsoft.orderfoodonline.service.CountryService;
import bmbsoft.orderfoodonline.service.LanguageService;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;

@RestController
@CrossOrigin
public class AttributeGroupController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(AttributeGroupController.class);

	@Autowired
	CountryService countryService;

	@Autowired
	AttributeGroupService attributeGroupService;

	@Autowired
	LanguageService ls;
	
	@RequestMapping(value = "/api/attribute-group/getAll", method = RequestMethod.GET)
	@Transactional
	public ResponseEntity<?> getAll() {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			List<AttributeGroupViewModel> items = attributeGroupService.getAll();
			if (items == null || items.size() <= 0) {
				rs.setStatus(8);
				rs.setMessage("Could not found item.");
				rs.setContent(null);

				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setMessage("Ok");
			rs.setContent(items);

			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
		}

		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}
	
	@RequestMapping(value = "/api/attribute-group/getAll/{languageCode}", method = RequestMethod.GET)
	@Transactional
	public ResponseEntity<?> getAllByLanguageCode(@PathVariable String languageCode) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			List<AttributeGroupResponse> items = attributeGroupService.getAll(languageCode);
			if (items == null || items.size() <= 0) {
				rs.setStatus(8);
				rs.setMessage("Could not found item.");
				rs.setContent(null);

				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setMessage("Ok");
			rs.setContent(items);

			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
		}

		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/apiattribute-group/getById/{id}/{languageCode}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable long id, @PathVariable String languageCode) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			if (id <= 0) {
				rs.setStatus(8);
				rs.setMessage("id is field required");

				return new ResponseEntity<ResponseGet>(rs, httpStatus);
			}

			AttributeGroupViewModel item = attributeGroupService.getById(id, languageCode);
			if (item == null) {
				rs.setStatus(8);
				rs.setMessage("File not found");
				return new ResponseEntity<ResponseGet>(rs, httpStatus);
			}

			rs.setStatus(0);
			rs.setMessage("Ok");
			rs.setContent(item);
			httpStatus = HttpStatus.OK;

			return new ResponseEntity<ResponseGet>(rs, httpStatus);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/admin/attribute-group/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody AttributeGroupRequest vm) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {

			attributeGroupService.save(vm);
			rs.setStatus(0);
			rs.setMessage("Ok");
			httpStatus = HttpStatus.OK;

			return new ResponseEntity<ResponseGet>(rs, httpStatus);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
		}

		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/admin/attribute-group/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody AttributeGroupRequest vm) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			AttributeGroup item = attributeGroupService.getBaseById(id);
			if (item == null) {
				rs.setStatus(8);
				rs.setMessage("Could not found item.");

				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			item.setCode(vm.getCode());

			attributeGroupService.update(vm, item);
			rs.setStatus(0);
			rs.setMessage("Ok");
			httpStatus = HttpStatus.OK;

			return new ResponseEntity<ResponseGet>(rs, httpStatus);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
		}

		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/admin/attribute-group/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable long id) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			AttributeGroup item = attributeGroupService.getBaseById(id);
			if (item == null) {
				rs.setStatus(8);
				rs.setMessage("Could not found item.");

				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			item.setStatus(Constant.Status.Deleted.getValue());
			attributeGroupService.delete(item);

			rs.setStatus(0);
			rs.setMessage("Ok");
			httpStatus = HttpStatus.OK;

			return new ResponseEntity<ResponseGet>(rs, httpStatus);

		} catch (Exception e) {
			logger.info(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
		}
		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/admin/attribute-group/deleteMany", method = RequestMethod.POST)
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
				AttributeGroup c = attributeGroupService.getBaseById(id);
				if (c != null) {
					c.setStatus(Constant.Status.Deleted.getValue());

					attributeGroupService.delete(c);
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
