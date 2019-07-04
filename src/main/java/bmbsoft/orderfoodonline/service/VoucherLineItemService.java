package bmbsoft.orderfoodonline.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bmbsoft.orderfoodonline.dao.VoucherDAO;
import bmbsoft.orderfoodonline.dao.VoucherLineItemDAO;
import bmbsoft.orderfoodonline.entities.Voucher;
import bmbsoft.orderfoodonline.entities.VoucherLineitem;
import bmbsoft.orderfoodonline.model.VoucherLineItemRequest;
import bmbsoft.orderfoodonline.util.Constant;

@Service
public class VoucherLineItemService {
	public static final Logger logger = LoggerFactory.getLogger(VoucherLineItemService.class);

	@Autowired
	private VoucherLineItemDAO pliDAO;
	@Autowired
	private VoucherDAO pDao;

	@Transactional
	public String create(VoucherLineItemRequest vlirs, Voucher p) {
		return pliDAO.createVoucherLineItem(vlirs,p);
	}

	@Transactional
	public Boolean update(VoucherLineitem pli) {
		return pliDAO.update(pli);
	}

	public VoucherLineitem getById(final long id) {
		VoucherLineitem p = pliDAO.getById(id);
		return p;
	}

	// public VoucherLineItemResponse viewDetail(final long id) {
	// VoucherLineItem p = voucherDAO.getById(id);
	// return (p == null) ? null : this.enityToModel(p);
	// }

	public void delete(final VoucherLineitem voucher) {
		pliDAO.delete(voucher);
	}

}
