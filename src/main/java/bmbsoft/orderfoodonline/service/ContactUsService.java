package bmbsoft.orderfoodonline.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.dao.ContactUsDAO;
import bmbsoft.orderfoodonline.entities.ContactUs;
import bmbsoft.orderfoodonline.model.ContactUsViewModel;
import bmbsoft.orderfoodonline.util.Constant;

@Service
public class ContactUsService {
	public static final Logger logger = LoggerFactory.getLogger(ContactUsService.class);

	@Autowired
	ContactUsDAO fda;

	public List<ContactUsViewModel> getAll() {
		List<ContactUsViewModel> ce = new ArrayList<>();
		List<ContactUs> e = fda.getAll();
		e.forEach(c -> { 
			ce.add(MaptoModel(c));
		});
		return ce;
	}

	public String save(final ContactUsViewModel c) {
		StringBuilder sb = new StringBuilder();
		try {
			ContactUs ce = new ContactUs();
			ce.setSubject(c.getSubject());
			ce.setCName(c.getContactName());
			ce.setCPhone(c.getContactPhone());
			ce.setContactType(c.getContactType().getValue());
			ce.setCEmail(c.getContactEmail());
			ce.setIsStatus(Constant.Status.Publish.getValue());
			ce.setCreatedDate(new Date());
			ce.setCMessage(c.getContactMessage());

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

	public String update(final ContactUsViewModel c) {
		StringBuilder sb = new StringBuilder();
		try {
			ContactUs ce = fda.getById(c.getContactId());
			ce.setSubject(c.getSubject());
			ce.setCName(c.getContactName());
			ce.setContactType(c.getContactType().getValue());
			ce.setCEmail(c.getContactEmail());
			ce.setContactType(c.getContactType().getValue());

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

	public String delete(final ContactUsViewModel c) {

		StringBuilder sb = new StringBuilder();
		try {
			ContactUs ce = fda.getById(c.getContactId());
			if (ce == null) {
				sb.append("Could not found item.");
			} else {
				ce.setIsStatus(Constant.Status.Deleted.getValue());

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

	public ContactUsViewModel getById(final long id) {

		try {
			ContactUs ce = fda.getById(id);
			if (ce == null) {
				return null;
			} else {
				ContactUsViewModel vm = new ContactUsViewModel();
				vm.setSubject(ce.getSubject());
				return vm;
			}

		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}

	}

	private ContactUsViewModel MaptoModel(ContactUs ce) {
		ContactUsViewModel vm = new ContactUsViewModel();
		vm.setSubject(ce.getSubject());
		vm.setContactName(ce.getCName());
		vm.setContactMessage(ce.getCMessage());
		vm.setContactPhone(ce.getCPhone()); 
		//vm.setContactType(Constant.ContactType.valueOf(ce.getContactType().toString())); 
		vm.setContactEmail(ce.getCEmail());
		vm.setStatus(ce.getIsStatus());
		return vm;
	}

}
