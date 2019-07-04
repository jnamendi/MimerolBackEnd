package bmbsoft.orderfoodonline.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.controller.UserController;
import bmbsoft.orderfoodonline.dao.PaymentProviderDAO;
import bmbsoft.orderfoodonline.dao.CountryDAO;
import bmbsoft.orderfoodonline.entities.PaymentProvider;
import bmbsoft.orderfoodonline.model.PaymentProviderViewModel;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.util.Constant;
import bmbsoft.orderfoodonline.entities.PaymentProvider;
import bmbsoft.orderfoodonline.entities.Country;

@Service
public class PaymentProviderService {
	public static final Logger logger = LoggerFactory.getLogger(PaymentProviderService.class);

	@Autowired
	PaymentProviderDAO PaymentProvider;

	public ResponseGetPaging getAll(int pageIndex, int pageSize, String paymentName) {
		return PaymentProvider.getAll(pageIndex, pageSize, paymentName);
	}

	public boolean save(final PaymentProvider c) {
		return PaymentProvider.save(c);
	}

	public PaymentProviderViewModel getById(final long id) {
		return convertEntityToModel(PaymentProvider.getById(id));
	}

	public PaymentProvider getBaseById(final long id) {
		return PaymentProvider.getById(id);
	}

	public boolean delete(PaymentProvider c) {
		c.setIsStatus(Constant.Status.Deleted.getValue());
		c.setModifiedDate(new Date());
		return PaymentProvider.save(c);
	}

	private PaymentProviderViewModel convertEntityToModel(PaymentProvider r) {
		if (r == null)
			return null;
		PaymentProviderViewModel vm = new PaymentProviderViewModel();
		vm.setPaymentProviderId(r.getPaymentProviderId());
		vm.setName(r.getName());
		vm.setStatus(r.getIsStatus());
		vm.setSortOrder(r.getSortOrder());

		return vm;
	}

}
