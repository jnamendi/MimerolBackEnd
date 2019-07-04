package bmbsoft.orderfoodonline.service;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bmbsoft.orderfoodonline.dao.MenuItemDAO;
import bmbsoft.orderfoodonline.dao.PromotionDAO;
import bmbsoft.orderfoodonline.dao.PromotionLineItemDAO;
import bmbsoft.orderfoodonline.dao.RestaurantDAO;
import bmbsoft.orderfoodonline.entities.MenuItem;
import bmbsoft.orderfoodonline.entities.Promotion;
import bmbsoft.orderfoodonline.entities.PromotionLineitem;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.model.PromotionLineItemRequest;

@Service
public class PromotionLineItemService {
	public static final Logger logger = LoggerFactory.getLogger(PromotionLineItemService.class);

	@Autowired
	private PromotionLineItemDAO pliDAO;
	@Autowired
	private RestaurantDAO rDAO;
	@Autowired
	private PromotionDAO pDao;

	@Transactional
	public String create(List<PromotionLineItemRequest> plirs) {
		StringBuilder message = new StringBuilder();
		List<PromotionLineitem> pils = new LinkedList<>();
		for (PromotionLineItemRequest plir : plirs) {
			Restaurant m = this.rDAO.getById(plir.getRestaurantId());
			if (m == null)
				return message.append("Not exists restaurant item with id is " + plir.getRestaurantId()).toString();
			Promotion p = this.pDao.getById(plir.getPromotionId());
			if (p == null)
				return message.append("Not exists promotion with id is " + plir.getPromotionId()).toString();
			PromotionLineitem pli = new PromotionLineitem();
			if (plir.getCode() != null && !plir.getCode().isEmpty())
				pli.setCode(plir.getCode());
			pli.setRestaurant(m);
			pli.setPromotion(p);
			pils.add(pli);
		}
		if (plirs.size() > 0) {
			for (PromotionLineitem pli : pils) {
				if (!pliDAO.create(pli))
					return message.append("Can't create PromotionLineItem").toString();
			}
		}
		return null;
	}

	@Transactional
	public Boolean update(PromotionLineitem pli) {
		return pliDAO.update(pli);
	}

	public PromotionLineitem getById(final long id) {
		PromotionLineitem p = pliDAO.getById(id);
		return p;
	}

	public boolean getByCode(final String code, long resId) {
		return pliDAO.getByCode(code, resId);
	}
	
	public boolean getByOnlyCode(final String code) {
		return pliDAO.getByCode(code);
	}

	public boolean getByProId(final String code, long resId) {
		return pliDAO.getByProId(code, resId);
	}
	// public PromotionLineItemResponse viewDetail(final long id) {
	// PromotionLineItem p = promotionDAO.getById(id);
	// return (p == null) ? null : this.enityToModel(p);
	// }

	public void delete(final PromotionLineitem promotion) {
		pliDAO.delete(promotion);
	}

}
