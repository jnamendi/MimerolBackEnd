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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Dynamic;

import bmbsoft.orderfoodonline.entities.Favouries;
import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.model.FavouriesViewModel;
import bmbsoft.orderfoodonline.model.LanguageViewModel;
import bmbsoft.orderfoodonline.model.UserViewModel;
import bmbsoft.orderfoodonline.model.shared.DeleteManyRequest;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.service.LanguageService;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;

@RestController
@CrossOrigin
public class LanguageController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(LanguageController.class);

	@Autowired
	LanguageService languageService;

	@RequestMapping(value = "/api/language/getAll", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> getAll() {
		ResponseGetPaging rs = new ResponseGetPaging();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			List<LanguageViewModel> c = this.languageService.getAll();
			if (c == null || c.size() <= 0) {
				rs.setStatus(8);
				rs.setMessage("File not found ");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				rs.setContent(null);

				return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setMessage("Ok");

			Data rsm = new Data();
			rsm.setTotalCount(c.size());
			rsm.setPageIndex(0);
			rsm.setPageSize(Constant.PageNumber);
			rsm.setData(c);

			rs.setContent(rsm);
			return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
		}

		return new ResponseEntity<ResponseGetPaging>(rs, httpStatus);

	}

	@RequestMapping(value = "/api/language/getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable long id) {
		ResponseGet rs = new ResponseGet();
		try {
			Language c = languageService.getById(id);
			if (c == null) {
				rs.setStatus(6);
				rs.setMessage("city not exists");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setContent(MappingToViewModel(c));
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	private LanguageViewModel MappingToViewModel(Language ff) {
		LanguageViewModel fv = new LanguageViewModel();
		fv.setName(ff.getName());
		fv.setCode(ff.getCode());
		fv.setLanguageId(ff.getLanguageId());
		fv.setStatus(Constant.Status.valueOf(ff.getStatus()));

		return fv;
	}

	@RequestMapping(value = "/admin/language/save", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Language model) {
		ResponseGet rs = new ResponseGet();
		try {

			Boolean e = languageService.checkExistLang(model.getCode());
			if (e) {
				rs.setStatus(1);
				rs.setMessage("Exist email.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}

			Language f = new Language();
			f.setName(model.getName());
			f.setCode(model.getCode());
			f.setStatus(Constant.Status.Publish.getValue());
			f.setModifiedDate(new Date());

			if (languageService.save(f) == true) {
				rs.setStatus(0);
				rs.setMessage("save success");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(5);
			rs.setMessage("Error when process the data.");
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/language/save/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody LanguageViewModel model) {

		ResponseGet rs = new ResponseGet();
		try {

			Boolean e = languageService.checkExistLang(model.getCode());
			if (e) {
				rs.setStatus(1);
				rs.setMessage("Exist email.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}

			Language f = languageService.getById(id);
			f.setName(model.getName());
			f.setCode(model.getCode());
			f.setStatus(model.getStatus().getValue());
			f.setModifiedDate(new Date());

			if (languageService.save(f) == true) {
				rs.setStatus(0);
				rs.setMessage("save success");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(5);
			rs.setMessage("Error when process the data.");
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/admin/language/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable long id) {
		ResponseGet responseGet = new ResponseGet();
		try {
			Language c = languageService.getById(id);
			if (null == c) {
				responseGet.setStatus(6);
				responseGet.setMessage("city not exists");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			if (languageService.delete(c)) {
				responseGet.setStatus(0);
				responseGet.setMessage("delete success");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(5);
			responseGet.setMessage("Error when process the data");
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error(e.toString());
			responseGet.setStatus(1);
			responseGet.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/admin/language/deleteMany", method = RequestMethod.POST)
	public ResponseEntity<?> deleteMany(@RequestBody DeleteManyRequest cr) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			if (cr == null || cr.getIds().length <= 0) {
				rs.setStatus(8);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < cr.getIds().length; i++) {
				long id = Long.valueOf(cr.getIds()[i]);
				Language ct = languageService.getById(id);
				if (ct != null) {  
					languageService.delete(ct);
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
