package bmbsoft.orderfoodonline.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.dao.FavouriesDAO;
import bmbsoft.orderfoodonline.entities.Address;
import bmbsoft.orderfoodonline.entities.Favouries;
import bmbsoft.orderfoodonline.entities.Media;
import bmbsoft.orderfoodonline.entities.Rating;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.entities.RestaurantComment;
import bmbsoft.orderfoodonline.model.AddressViewModel;
import bmbsoft.orderfoodonline.model.FavouriesViewModel;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;

@Service
public class FavouriesService {
	public static final Logger logger = LoggerFactory.getLogger(FavouriesService.class);

	@Autowired
	private FavouriesDAO fda;

	@Transactional
	@Async
	public ResponseGetPaging getAll(final int pageIndex, final int pageSize, final String title, Integer status)
			throws Exception {
		int totalRecord = fda.countGetAll(title, status);
		int currentPage = (pageIndex < 1) ? 1 : pageIndex;
		int firstResult = (currentPage - 1) * pageSize;
		int maxResult = currentPage * pageSize;
		List<Favouries> favouriess = fda.getAll(firstResult, maxResult, title, status);

		ResponseGetPaging rs = new ResponseGetPaging();
		Data content = new Data();
		if (favouriess == null || favouriess.isEmpty()) {
			rs.setStatus(0);
			rs.setMessage("Could not found items.");
			content.setTotalCount(0);
			rs.setContent(content);
			rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
			return rs;
		}

		List<FavouriesViewModel> fvm = new LinkedList<>();
		if (favouriess != null && !favouriess.isEmpty()) {
			favouriess.forEach(ff -> {
				fvm.add(mappingToViewModel(ff));
			});
		}

		if (totalRecord > 0 && !fvm.isEmpty()) {
			content.setData(fvm);
			content.setPageIndex(pageIndex);
			content.setPageSize(pageSize);
			content.setTotalCount(totalRecord);
			rs.setContent(content);
			rs.setStatus(0);
			rs.setMessage("Success.");
		}
		return rs;
	}

	public boolean save(final Favouries c) {
		return fda.save(c);
	}

	public boolean delete(final Favouries c) {
		return fda.delete(c);
	}

	@Transactional
	@Async
	public FavouriesViewModel getDetail(final long id) {
		Favouries c = fda.getById(id);
		return (c == null) ? null : mappingToViewModel(c);
	}

	@Transactional
	@Async
	public List<FavouriesViewModel> getByUserId(final long userId, Integer status) {
		List<FavouriesViewModel> listModel = new LinkedList<>();
		List<Favouries> rs = fda.getByUserId(null, userId, status);
		if (null == rs) {
			return null;
		}
		rs.forEach(f -> {
			listModel.add(mappingToViewModel(f));
		});
		return listModel;
	}

	@Transactional
	public List<FavouriesViewModel> getByIdUserId(long restaurantId, final long userId, Integer status) {
		List<FavouriesViewModel> listModel = new LinkedList<>();
		List<Favouries> rs = fda.getByUserId(restaurantId, userId, status);
		if (null == rs) {
			return null;
		}
		rs.forEach(f -> {
			listModel.add(mappingToViewModel(f));
		});
		return listModel;
	}

	public Favouries getById(final long id) {
		Favouries c = fda.getById(id);
		return c;
	}

	private FavouriesViewModel mappingToViewModel(Favouries ff) {
		FavouriesViewModel fv = new FavouriesViewModel();

		fv.setUserName(ff.getUser().getFullName());
		fv.setFavoriesId(ff.getFavoriesId());

		fv.setStatus(ff.getIsStatus());
		fv.setUserId(ff.getUser().getUserId());

		Restaurant r = ff.getRestaurant();
		if (r != null) {
			fv.setRestaurantId(r.getRestaurantId());
			fv.setRestaurantName(r.getName());
			fv.setCity(r.getCity());
			fv.setDistrict(r.getDistrictName());
			fv.setImageUrl(r.getImageUrl());
			fv.setUrlSlug(CommonHelper.toPrettyURL(r.getName()));

			Set<Rating> rt = r.getRatings();
			if (rt != null && rt.size() > 0) {

				double delivery = rt.stream()
						.filter(p -> p.getDelivery() > 0 && p.getIsStatus() == Constant.Status.Publish.getValue())
						.count();
				double quanlity = rt.stream()
						.filter(p -> p.getQuality() > 0 && p.getIsStatus() == Constant.Status.Publish.getValue())
						.count();
				int count = rt.size();
				double sum = delivery + quanlity;

				fv.setRating(sum / (sum * count));

			}

			Set<RestaurantComment> srco = r.getRestaurantComments();
			if (srco != null && srco.size() > 0) {
				int c = (int) srco.stream().filter(p -> p.getStatus() == Constant.Status.Publish.getValue()).count();
				fv.setNumOfReview(c);
			}

		}

		return fv;
	}

}
