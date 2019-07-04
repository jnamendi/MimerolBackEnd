package bmbsoft.orderfoodonline.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.controller.UserController;
import bmbsoft.orderfoodonline.dao.ContentEmailDAO;
import bmbsoft.orderfoodonline.dao.CountryDAO;
import bmbsoft.orderfoodonline.entities.ContentEmail;
import bmbsoft.orderfoodonline.entities.Country;
import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.model.ContentEmaiLViewModel;
import bmbsoft.orderfoodonline.util.Constant;

@Service
public class ContentEmailService {
	public static final Logger logger = LoggerFactory.getLogger(ContentEmailService.class);

	@Autowired
	ContentEmailDAO fda;

	public List<ContentEmaiLViewModel> getAll() {
		List<ContentEmaiLViewModel> ce = new ArrayList<>();

		return ce;
	}

	public String save(final ContentEmaiLViewModel c) {
		StringBuilder sb = new StringBuilder();
		try {
			ContentEmail ce = new ContentEmail();
			ce.setSubject(c.getSubject());
			ce.setTitle(c.getTitle());
			ce.setContentBody(c.getBody());
			ce.setIsStatus(Constant.Status.Publish.getValue());
			ce.setCreatedDate(new Date());
			ce.setModifiedDate(new Date());

			boolean s = fda.save(ce);
			if (s)
				return "";
			return "Error when process the data";

		} catch (Exception e) {
			logger.error(e.toString());
			sb.append(e.toString());
		}

		return sb.toString();
	}

	public String update(final ContentEmaiLViewModel c) {
		StringBuilder sb = new StringBuilder();
		try {
			ContentEmail ce = fda.getById(c.getContentEmailId());
			ce.setSubject(c.getSubject());
			ce.setTitle(c.getTitle());
			ce.setContentBody(c.getBody());
			ce.setIsStatus(c.getStatus().getValue());
			ce.setModifiedDate(new Date());

			boolean s = fda.save(ce);
			if (s)
				return "";
			return "Error when process the data";

		} catch (Exception e) {
			logger.error(e.toString());
			sb.append(e.toString());
		}

		return sb.toString();
	}

	public String delete(final ContentEmaiLViewModel c) {

		StringBuilder sb = new StringBuilder();
		try {
			ContentEmail ce = fda.getById(c.getContentEmailId());
			if (ce == null) {
				sb.append("Could not found item.");
			} else {
				ce.setIsStatus(Constant.Status.Deleted.getValue());
				ce.setModifiedDate(new Date());

				boolean s = fda.delete(ce);

				if (s)
					return "";
				return "Error when process the data";

			}

		} catch (Exception e) {
			logger.error(e.toString());
			sb.append(e.toString());
		}

		return sb.toString();
	}

	public ContentEmaiLViewModel getById(final long id) {

		try {
			ContentEmail ce = fda.getById(id);
			if (ce == null) {
				return null;
			} else {
				ContentEmaiLViewModel vm = new ContentEmaiLViewModel();
				vm.setSubject(ce.getSubject());
				vm.setTitle(ce.getTitle());
				vm.setBody(ce.getContentBody());
				vm.setStatus(Constant.Status.valueOf(ce.getIsStatus()));
				return vm;
			}

		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}

	}

	public ContentEmaiLViewModel getByType(int type, String code) {
		try {
			ContentEmail ce = fda.getByType(type,code);
			if (ce == null) {
				return null;
			} else { 
				return MaptoModel(ce);
			}

		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}

	}

	private ContentEmaiLViewModel MaptoModel(ContentEmail ce) {
		ContentEmaiLViewModel vm = new ContentEmaiLViewModel();
		vm.setSubject(ce.getSubject());
		vm.setTitle(ce.getTitle());
		vm.setBody(ce.getContentBody());
		vm.setBcc(ce.getCc());
		return vm;
	}

}
