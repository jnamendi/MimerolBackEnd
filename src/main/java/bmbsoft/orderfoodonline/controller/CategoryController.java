package bmbsoft.orderfoodonline.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import bmbsoft.orderfoodonline.entities.Category;
import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.model.CategoryViewModel;
import bmbsoft.orderfoodonline.model.shared.CategoryReq;
import bmbsoft.orderfoodonline.model.shared.CategoryRequest;
import bmbsoft.orderfoodonline.model.shared.CategoryResponse;
import bmbsoft.orderfoodonline.response.ResponseGet;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.service.CategoryService;
import bmbsoft.orderfoodonline.service.DistrictService;
import bmbsoft.orderfoodonline.service.LanguageService;
import bmbsoft.orderfoodonline.util.Constant;

@RestController
@CrossOrigin
public class CategoryController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private LanguageService languageService;

	@Autowired
	DistrictService districtService;

	@Autowired
	LanguageService langService;

	Gson gson = new Gson();

	/**
	 * 
	 * @param codeLang
	 * @return list Category not paging
	 */
	@RequestMapping(value = "/api/category/getAll/{languageCode}", method = RequestMethod.GET)
	public ResponseEntity<?> getAll(@PathVariable String languageCode) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// permission
			if (!permission(Constant.Module.Category, Constant.Action.getAll)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			if (null == languageService.getLanguageByCode(languageCode)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				rs.setMessage("language code not exist");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			List<CategoryViewModel> c = this.categoryService.getAll(languageCode, Constant.Status.Publish.getValue());
			if (c == null || c.size() <= 0) {
				rs.setStatus(0);
				rs.setMessage("Could not found item. ");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setMessage("Ok");
			rs.setContent(c);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(rs, httpStatus);
		}
	}

	@RequestMapping(value = "/api/category/get-all-sort-by-name/{languageCode}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCategorySortByName(@PathVariable String languageCode) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// permission
			if (!permission(Constant.Module.Category, Constant.Action.getAllSortByName)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (null == languageService.getLanguageByCode(languageCode)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				rs.setMessage("language code not exist");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			Language l = languageService.getLanguageByCode(languageCode);
			if (l == null) {
				rs.setStatus(7);
				rs.setMessage("language invalid.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			List<CategoryViewModel> c = this.categoryService.getAllSortBy(l, Constant.Status.Publish.getValue());
			if (c == null || c.size() <= 0) {
				rs.setStatus(0);
				rs.setMessage("Could not found item. ");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
			}
			rs.setStatus(0);
			rs.setMessage("Ok");
			rs.setContent(c);
			return new ResponseEntity<ResponseGet>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			return new ResponseEntity<ResponseGet>(rs, httpStatus);
		}
	}

	@RequestMapping(value = "/api/category/getById/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable Long id) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// permission
			if (!permission(Constant.Module.Category, Constant.Action.getById)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (id == null || id <= 0) {
				rs.setStatus(7);
				rs.setMessage("Input invalid.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			CategoryViewModel c = categoryService.getDetail(id);
			if (null == c) {
				rs.setStatus(8);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			rs.setStatus(0);
			rs.setMessage("Ok");
			rs.setContent(c);
			httpStatus = HttpStatus.OK;
			return new ResponseEntity<ResponseGet>(rs, httpStatus);

		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(rs, httpStatus);
		}
	}

	@RequestMapping(value = "/admin/category/save", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody CategoryReq req, BindingResult result) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// permission
			if (!permission(Constant.Module.Category, Constant.Action.save)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (result.hasErrors()) {
				rs.setStatus(7);
				rs.setMessage(result.getFieldError().getDefaultMessage());
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			String s = categoryService.save(req, null, null);
			if (s.isEmpty()) {
				rs.setStatus(0);
				rs.setMessage("Ok");
				httpStatus = HttpStatus.OK;

				return new ResponseEntity<ResponseGet>(rs, httpStatus);
			}
			rs.setStatus(1);
			rs.setMessage("Error when process the data. ");
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(rs, httpStatus);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}

		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/admin/category/save/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CategoryReq req, BindingResult result) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// permission
			if (!permission(Constant.Module.Category, Constant.Action.update)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (result.hasErrors()) {
				rs.setStatus(7);
				rs.setMessage(result.getFieldError().getDefaultMessage());
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			if (id == null || id <= 0) {
				rs.setStatus(7);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			String s = categoryService.save(req, id, null);
			if (s.isEmpty()) {
				rs.setStatus(0);
				rs.setMessage("Ok");
				httpStatus = HttpStatus.OK;

				return new ResponseEntity<ResponseGet>(rs, httpStatus);
			}
			rs.setStatus(1);
			rs.setMessage("Error when process the data. ");
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}
		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/admin/category/insert", method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam String categoryAdminModel) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// permission
			if (!permission(Constant.Module.Category, Constant.Action.insert)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			CategoryReq req = gson.fromJson(categoryAdminModel, CategoryReq.class);

			if (req == null) {
				rs.setStatus(7);
				rs.setMessage("Request is null");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			String s = categoryService.save(req, null, file);
			if (s.isEmpty()) {
				rs.setStatus(0);
				rs.setMessage("Ok");
				httpStatus = HttpStatus.OK;

				return new ResponseEntity<ResponseGet>(rs, httpStatus);
			}
			rs.setStatus(1);
			rs.setMessage("Error when process the data. ");
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(rs, httpStatus);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}

		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/admin/category/edit/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> edit(@RequestParam(value = "file", required = false) MultipartFile file,
			@PathVariable Long id, @RequestParam String categoryAdminModel) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		try {
			// permission
			if (!permission(Constant.Module.Category, Constant.Action.edit)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (id == null || id <= 0) {
				rs.setStatus(7);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			CategoryReq req = gson.fromJson(categoryAdminModel, CategoryReq.class);

			if (req == null) {
				rs.setStatus(7);
				rs.setMessage("Request is null");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			String s = categoryService.save(req, id, file);
			if (s.isEmpty()) {
				rs.setStatus(0);
				rs.setMessage("Ok");
				httpStatus = HttpStatus.OK;

				return new ResponseEntity<ResponseGet>(rs, httpStatus);
			}
			rs.setStatus(1);
			rs.setMessage("Error when process the data. ");
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		} catch (Exception e) {
			logger.error(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}
		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/admin/category/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			// permission
			if (!permission(Constant.Module.Category, Constant.Action.delete)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (id == null || id <= 0) {
				rs.setStatus(7);
				rs.setMessage("Input not valid.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			Category c = categoryService.getById(id);
			if (c == null) {
				rs.setStatus(7);
				rs.setMessage("Category invalid.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (categoryService.delete(c)) {
				rs.setStatus(0);
				rs.setMessage("Ok");
				httpStatus = HttpStatus.OK;

				return new ResponseEntity<ResponseGet>(rs, httpStatus);
			}

			rs.setStatus(1);
			rs.setMessage("Error when process the data.");
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
			return new ResponseEntity<ResponseGet>(rs, httpStatus);

		} catch (Exception e) {
			logger.info(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}
		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	/**
	 * function use for: get all and search category by value translate. if language
	 * null search and get all translate, else get and search by language.
	 */
	@RequestMapping(value = "/api/category/getAllByLanguage/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllByLanguage(@PathVariable int pageIndex, @PathVariable int pageSize,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "languageCode", required = false) String languageCode) {
		ResponseGetPaging rs = new ResponseGetPaging();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			// permission
			if (!permission(Constant.Module.Category, Constant.Action.getAllByLanguage)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.BAD_REQUEST);
			}
			if (languageCode != null) {
				if (languageCode.isEmpty() || null == languageService.getLanguageByCode(languageCode)) {
					rs.setStatus(7);
					rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
					rs.setMessage("language not exits");
					return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.BAD_REQUEST);
				}
			}
			rs = this.categoryService.getCategoryByLanguageId(pageIndex, pageSize, title, status, languageCode);
			if (rs.getStatus() != 0)
				return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.OK);
			return new ResponseEntity<ResponseGetPaging>(rs, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			rs.setStatus(1);
			rs.setMessage(e.getMessage());
			rs.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}
		return new ResponseEntity<ResponseGetPaging>(rs, httpStatus);
	}

	@RequestMapping(value = "/admin/category/deleteMany", method = RequestMethod.POST)
	public ResponseEntity<?> deleteMany(@RequestBody CategoryRequest cr) {
		ResponseGet rs = new ResponseGet();
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		try {
			// permission
			if (!permission(Constant.Module.Category, Constant.Action.deleteMany)) {
				rs.setStatus(7);
				rs.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				rs.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}
			if (cr == null || cr.getIds().length <= 0) {
				rs.setStatus(7);
				rs.setMessage("Could not found item.");
				rs.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);
				return new ResponseEntity<ResponseGet>(rs, HttpStatus.BAD_REQUEST);
			}

			String msg = categoryService.deleteMany(cr.getIds());
			if (msg.isEmpty()) {
				rs.setStatus(0);
				rs.setMessage("Delete success.");
				httpStatus = HttpStatus.OK;

				return new ResponseEntity<ResponseGet>(rs, httpStatus);
			}
			rs.setStatus(99);
			rs.setMessage(msg);
			httpStatus = HttpStatus.OK;

			return new ResponseEntity<ResponseGet>(rs, httpStatus);
		} catch (Exception e) {
			logger.info(e.toString());
			rs.setStatus(1);
			rs.setMessage(e.toString());
		}
		return new ResponseEntity<ResponseGet>(rs, httpStatus);
	}

	@RequestMapping(value = "/api/category/get-by-district/{languageCode}", method = RequestMethod.GET)
	public ResponseEntity<?> getByDistrict(@PathVariable @Validated String languageCode) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ResponseGet responseGet = new ResponseGet();
		try {
			// permission
			if (!permission(Constant.Module.Category, Constant.Action.getByDistrict)) {
				responseGet.setStatus(7);
				responseGet.setErrorType(Constant.ErrorTypeCommon.Access_Denied);
				responseGet.setMessage("Access Denied!");
				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);
			}
			Language l = langService.getLanguageByCode(languageCode);
			if (l == null) {
				responseGet.setStatus(7);
				responseGet.setMessage("language invalid.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.INVALID_INPUT);

				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.BAD_REQUEST);

			}

			List<CategoryResponse> res = categoryService.getByDistrict(l);
			if (res == null || res.size() <= 0) {
				responseGet.setStatus(0);
				responseGet.setMessage("Could not found items.");
				responseGet.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);

				return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
			}
			responseGet.setStatus(0);
			responseGet.setMessage("Ok");
			responseGet.setContent(res);
			return new ResponseEntity<ResponseGet>(responseGet, HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			responseGet.setStatus(1);
			responseGet.setMessage(e.getMessage());
			responseGet.setErrorType(Constant.ErrorTypeCommon.ERROR_PROCESS_DATA);
		}
		return new ResponseEntity<ResponseGet>(responseGet, httpStatus);
	}

}
