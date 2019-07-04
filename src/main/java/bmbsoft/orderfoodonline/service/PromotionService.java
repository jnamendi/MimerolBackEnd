package bmbsoft.orderfoodonline.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bmbsoft.orderfoodonline.dao.PromotionDAO;
import bmbsoft.orderfoodonline.entities.Language;
import bmbsoft.orderfoodonline.entities.Promotion;
import bmbsoft.orderfoodonline.entities.PromotionLineitem;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.entities.Voucher;
import bmbsoft.orderfoodonline.model.PromotionRequest;
import bmbsoft.orderfoodonline.model.PromotionResponse;
import bmbsoft.orderfoodonline.model.shared.CurrencyResponse;
import bmbsoft.orderfoodonline.model.shared.PromotionLineitemResponse;
import bmbsoft.orderfoodonline.model.shared.PromotionLiteResponse;
import bmbsoft.orderfoodonline.model.shared.VoucherLiteResponse;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.util.Constant;

@Service
public class PromotionService {
	public static final Logger logger = LoggerFactory.getLogger(PromotionService.class);

	@Autowired
	private PromotionDAO promotionDAO;

	@Autowired
	LanguageService languageService;

	public String create(PromotionRequest pr, Restaurant r) {
		return promotionDAO.Create(pr, r);
	}

	public String update(PromotionRequest pr, Promotion p, Restaurant r) {
		return promotionDAO.Update(pr, p, r);
	}

	@Transactional
	public Promotion getById(final long id) {
		Promotion p = promotionDAO.getById(id);
		return p;
	}

	@Transactional
	public PromotionResponse viewDetail(final long id) {
		Promotion p = promotionDAO.getById(id);
		return (p == null) ? null : this.enityToModel(p, null);
	}
	
	@Transactional
	public PromotionResponse getResponseById(final long id) {
		Promotion p = promotionDAO.getById(id);
		return (p == null) ? null : this.getAllEnityToModel(p, null);
	}
	
	@Transactional
	public PromotionResponse getMultiCodeByid(final long id) {
		Promotion p = promotionDAO.getById(id);
		return (p == null) ? null : this.enityToMultiCodeModel(p, null);
	}

	public void delete(final Promotion promotion) {
		promotionDAO.delete(promotion);
	}

	@Transactional
	public ResponseGetPaging getAll(int pageIndex, int pageSize, String codeLang, Integer status) {
		int totalRecord = promotionDAO.getAll(0, 0, status).size();
		int currentPage = (pageIndex < 1) ? 1 : pageIndex;
		int firstResult = (currentPage - 1) * pageSize;
		int maxResult = currentPage * pageSize;
		List<Promotion> promotions = promotionDAO.getAll(firstResult, maxResult, status);

		ResponseGetPaging rs = new ResponseGetPaging();
		Data content = new Data();
		if (promotions == null || promotions.isEmpty()) {
			rs.setStatus(0);
			rs.setMessage("Could not found items.");
			content.setTotalCount(0);
			rs.setContent(content);
			rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
			return rs;
		}

		List<PromotionResponse> prs = new LinkedList<>();
		if (promotions != null && !promotions.isEmpty()) {
			promotions.forEach(p -> {
				prs.add(this.getAllEnityToModel(p, codeLang));
			});
		}

		if (totalRecord > 0 && !prs.isEmpty()) {
			content.setData(prs);
			content.setPageIndex(pageIndex);
			content.setPageSize(pageSize);
			content.setTotalCount(totalRecord);
			rs.setContent(content);
			rs.setStatus(0);
			rs.setMessage("Success.");
		}
		return rs;
	}

	@Transactional
	public ResponseGetPaging getAllByOwner(int pageIndex, int pageSize, String codeLang, Long userId, Integer status) {
		int totalRecord = promotionDAO.getAllByOwner(0, 0, userId, status).size();
		int currentPage = (pageIndex < 1) ? 1 : pageIndex;
		int firstResult = (currentPage - 1) * pageSize;
		int maxResult = currentPage * pageSize;
		List<Promotion> promotions = promotionDAO.getAllByOwner(firstResult, maxResult, userId, status);

		ResponseGetPaging rs = new ResponseGetPaging();
		Data content = new Data();
		if (promotions == null || promotions.isEmpty()) {
			rs.setStatus(0);
			rs.setMessage("Could not found items.");
			content.setTotalCount(0);
			rs.setContent(content);
			rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
			return rs;
		}

		List<PromotionResponse> prs = new LinkedList<>();
		if (promotions != null && !promotions.isEmpty()) {
			promotions.forEach(p -> {
				prs.add(this.getAllEnityToModel(p, codeLang));
			});
		}

		if (totalRecord > 0 && !prs.isEmpty()) {
			content.setData(prs);
			content.setPageIndex(pageIndex);
			content.setPageSize(pageSize);
			content.setTotalCount(totalRecord);
			rs.setContent(content);
			rs.setStatus(0);
			rs.setMessage("Success.");
		}
		return rs;
	}

	private PromotionResponse enityToMultiCodeModel(Promotion p, String codeLang) {
		Language lang = (codeLang == null || codeLang.isEmpty()) ? null : languageService.getLanguageByCode(codeLang);

		PromotionResponse pr = new PromotionResponse();
		pr.setPromotionId(p.getPromotionId());
		pr.setName(p.getName());
		pr.setCode(p.getCode());
		pr.setStartDate(p.getStartDate());
		pr.setEndDate(p.getEndDate());
		pr.setValue(p.getValue());
		pr.setStatus(p.getStatus());
		pr.setLanguageLst(languageService.translate(p.getContentDefinition(), lang));
		pr.setMinOrder(p.getMinOrder());

		// get code
		Set<PromotionLineitem> pl = p.getPromotionLineitems();
		List<PromotionLineitemResponse> ls = new ArrayList<>();

		if (pl != null && pl.size() > 0) {
			pl.forEach(pri -> {
				PromotionLineitemResponse prr = new PromotionLineitemResponse();
				prr.setCode(pri.getCode());
				prr.setRestaurantName(pri.getRestaurant().getName());
				prr.setRestaurantId(pri.getRestaurant().getRestaurantId());
				ls.add(prr);
			});
		}
		pr.setPromotionLineitem(ls);

		return pr;
	}

	private PromotionResponse getAllEnityToModel(Promotion p, String codeLang) {
		Language lang = (codeLang == null || codeLang.isEmpty()) ? null : languageService.getLanguageByCode(codeLang);

		PromotionResponse pr = new PromotionResponse();
		pr.setPromotionId(p.getPromotionId());
		pr.setName(p.getName());
		pr.setStartDate(p.getStartDate());
		pr.setEndDate(p.getEndDate());
		pr.setValue(p.getValue());
		pr.setStatus(p.getStatus());
		pr.setLanguageLst(languageService.translate(p.getContentDefinition(), lang));
		pr.setMinOrder(p.getMinOrder());

		// get code
		Set<PromotionLineitem> pl = p.getPromotionLineitems();
		// List<PromotionLineitemResponse> ls = new ArrayList<>();

		if (pl != null && pl.size() > 0) {
			pl.forEach(pri -> {
				PromotionLineitemResponse prr = new PromotionLineitemResponse();
				pr.setCode(pri.getCode());
				pr.setRestaurantName(pri.getRestaurant().getName());
				pr.setRestaurantId(pri.getRestaurant().getRestaurantId());

				return;
				// ls.add(prr);
			});
		}
		// pr.setPromotionLineitem(ls);

		return pr;
	}

	private PromotionResponse enityToModel(Promotion p, String codeLang) {
		Language lang = (codeLang == null || codeLang.isEmpty()) ? null : languageService.getLanguageByCode(codeLang);

		PromotionResponse pr = new PromotionResponse();
		pr.setPromotionId(p.getPromotionId());
		pr.setName(p.getName());
		pr.setPromotion(p);
		pr.setStartDate(p.getStartDate());
		pr.setEndDate(p.getEndDate());
		pr.setValue(p.getValue());
		pr.setStatus(p.getStatus());
		pr.setLanguageLst(languageService.translate(p.getContentDefinition(), lang));
		pr.setMinOrder(p.getMinOrder());

		// get code
		Set<PromotionLineitem> pl = p.getPromotionLineitems();
		// List<PromotionLineitemResponse> ls = new ArrayList<>();

		if (pl != null && pl.size() > 0) {
			pl.forEach(pri -> {
				PromotionLineitemResponse prr = new PromotionLineitemResponse();
				pr.setCode(pri.getCode());
				pr.setRestaurantName(pri.getRestaurant().getName());
				pr.setRestaurantId(pri.getRestaurant().getRestaurantId());

				return;
				// ls.add(prr);
			});
		}
		// pr.setPromotionLineitem(ls);

		return pr;
	}

	@Transactional
	public PromotionLiteResponse getByCode(String promotionCode, Long restaurantId, Language l) {
		Promotion p = promotionDAO.getByCode(promotionCode, restaurantId);

		return (p == null) ? null : this.mapToModel(p, null);
	};

	private PromotionLiteResponse mapToModel(Promotion p, CurrencyResponse c) {
		PromotionLiteResponse pr = new PromotionLiteResponse();
		pr.setPromotionId(p.getPromotionId());
		// pr.setValue(p.getValue() * (long) c.getRate());
		pr.setValue(p.getValue());
		return pr;
	}
}
