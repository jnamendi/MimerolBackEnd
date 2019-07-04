package bmbsoft.orderfoodonline.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.dao.CurrencyDAO;
import bmbsoft.orderfoodonline.dao.VoucherDAO;
import bmbsoft.orderfoodonline.entities.Category;
import bmbsoft.orderfoodonline.entities.Currency;
import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.entities.Voucher;
import bmbsoft.orderfoodonline.model.VoucherRequest;
import bmbsoft.orderfoodonline.model.VoucherResponse;
import bmbsoft.orderfoodonline.model.shared.CurrencyResponse;
import bmbsoft.orderfoodonline.model.shared.VoucherLiteResponse;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.util.Constant;

@Service
public class VoucherService {
	public static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

	@Autowired
	private VoucherDAO voucherDAO;
	@Autowired
	private CurrencyDAO currencyDAO;

	public Boolean create(VoucherRequest pr) {
		return voucherDAO.Create(pr);
	}

	public Boolean update(VoucherRequest pr, Voucher p) {
		return voucherDAO.Update(pr, p);
	}

	public Boolean updateBase(Voucher p) {
		return voucherDAO.updateBase(p);
	}

	public Voucher getById(final long id) {
		Voucher p = voucherDAO.getById(id);
		return p;
	}

	public VoucherResponse viewDetail(final long id) {
		Voucher p = voucherDAO.getById(id);
		return (p == null) ? null : this.enityToModel(p);
	}

	@Transactional
	public VoucherLiteResponse getByCode(String code, Language l) {
		Voucher p = voucherDAO.getByCode(code);

		return (p == null) ? null : this.mapToModel(p, null);
	}

	public void delete(final Voucher promotion) {
		voucherDAO.delete(promotion);
	}

	public ResponseGetPaging getAll(int pageIndex, int pageSize) {
		int totalRecord = voucherDAO.getAll(0, 0).size();
		int currentPage = (pageIndex < 1) ? 1 : pageIndex;
		int firstResult = (currentPage - 1) * pageSize;
		int maxResult = currentPage * pageSize;
		List<Voucher> vouchers = voucherDAO.getAll(firstResult, maxResult);

		ResponseGetPaging rs = new ResponseGetPaging();
		Data content = new Data();
		if (vouchers == null || vouchers.isEmpty()) {
			rs.setStatus(0);
			rs.setMessage("could not found items.");
			content.setTotalCount(0);
			rs.setContent(content);
			rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
			return rs;
		}

		List<VoucherResponse> vcrs = new LinkedList<>();
		if (vouchers != null && !vouchers.isEmpty()) {
			vouchers.forEach(p -> {
				vcrs.add(this.enityToModel(p));
			});
		}

		if (totalRecord > 0 && !vcrs.isEmpty()) {
			content.setData(vcrs);
			content.setPageIndex(pageIndex);
			content.setPageSize(pageSize);
			content.setTotalCount(totalRecord);
			rs.setContent(content);
			rs.setStatus(0);
			rs.setMessage("Success.");
		}
		return rs;
	}

	private VoucherLiteResponse mapToModel(Voucher p, CurrencyResponse c) {
		VoucherLiteResponse pr = new VoucherLiteResponse();
		pr.setVoucherId(p.getVoucherId());
		//pr.setValue(p.getValue() * (long) c.getRate());
		pr.setValue(p.getValue());
		return pr;
	}

	private VoucherResponse enityToModel(Voucher p) {
		VoucherResponse pr = new VoucherResponse();
		pr.setVoucherId(p.getVoucherId());
		pr.setName(p.getName());
		pr.setCode(p.getCode());
		pr.setStartDate(p.getStartDate());
		pr.setEndDate(p.getEndDate());
		pr.setCreatedDate(p.getCreatedDate());
		pr.setCreatedBy(pr.getCreatedBy());
		pr.setModifiedBy(pr.getModifiedBy());
		pr.setModifiedDate(p.getModifiedDate());
		pr.setValue(p.getValue());
		return pr;
	}

	public String deleteMany(final String[] ids) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < ids.length; i++) {
			long id = Long.valueOf(ids[i]);
			Voucher c = voucherDAO.getById(id);
			if (c != null) {
				c.setStatus(Constant.Status.Deleted.getValue());
				c.setModifiedDate(new Date());

				voucherDAO.updateBase(c);
			} else {
				s.append("Could not found item: " + ids[i]);
			}
		}
		return s.toString();
	}
}
