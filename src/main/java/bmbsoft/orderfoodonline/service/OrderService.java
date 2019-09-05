package bmbsoft.orderfoodonline.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import bmbsoft.orderfoodonline.dao.OrderInfoDAO;
import bmbsoft.orderfoodonline.model.shared.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.dao.CurrencyDAO;
import bmbsoft.orderfoodonline.dao.OrderDAO;
import bmbsoft.orderfoodonline.entities.Order;
import bmbsoft.orderfoodonline.entities.OrderHistory;
import bmbsoft.orderfoodonline.entities.OrderInfo;
import bmbsoft.orderfoodonline.entities.OrderLineItem;
import bmbsoft.orderfoodonline.entities.OrderPayment;
import bmbsoft.orderfoodonline.entities.Restaurant;
import bmbsoft.orderfoodonline.entities.User;
import bmbsoft.orderfoodonline.model.OrderInfoResponse;
import bmbsoft.orderfoodonline.model.OrderResponse;
import bmbsoft.orderfoodonline.model.OrderViewModel;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;

@Service
public class OrderService {
	public static final Logger logger = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private CurrencyDAO currencyDAO;

	@Autowired
	private OrderInfoDAO orderInfoDAO;

	@Transactional
	public ResponseGetPaging getAll(int pageIndex, int pageSize, String orderCode, String restaurantName,
			Integer status) {
		ResponseGetPaging rs = orderDAO.getAll(pageIndex, pageSize, orderCode, restaurantName, status);

		return rs;
	}

	@Transactional
	public boolean update(final OrderRequest OrderModel) {
		Order Order = convertModelToEntity(OrderModel);
		return orderDAO.update(Order);
	}

	@Transactional
	public boolean updateBase(Order o) {
		try {
			return orderDAO.update(o);
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public boolean saveHis(OrderHistory o) {
		try {
			return orderDAO.createHis(o);
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public OrderViewModel getById(final long id, String orderCode) {
		try {
			Order o = orderDAO.getById(id);
			if (o == null)
				return null;

			String fw = id + "_" + orderCode;
			boolean isTrue = CommonHelper.CheckPw(fw, o.getCheckSum());
			if (!isTrue)
				return null;

			return convertEntityToModel(o);
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
	}

	@Transactional
	public OrderResponse getFullById(final long id, String orderCode) {
		try {
			Order o = orderDAO.getById(id);

			if (o == null)
				return null;

			String fw = id + "_" + orderCode;
			boolean isTrue = CommonHelper.CheckPw(fw, o.getCheckSum());
			if (!isTrue)
				return null;
			// currency
			CurrencyResponse cur = currencyDAO.getByDefault();
			if (cur == null) {
				cur.setRate(1);
			}
			return toModelResponse(o, cur.getSymbolLeft());
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
	}

	@Transactional
	public Order getBaseById(final long id) {
		return orderDAO.getById(id);
	}

	// miss Function get Order by code -> check exists?

	private Order convertModelToEntity(final OrderRequest OrderModel) {
		Order Order = new Order();
		// if (null == OrderModel.getOrderId()) {
		// // new
		// Order.setOrderName(OrderModel.getName());
		// Order.setOrderCode(OrderModel.getCode());
		// Order.setStatus(OrderModel.getStatus());
		// // Order.setCreatedBy(createdBy); miss
		// Order.setCreatedDate(new Date());
		// // check countryId -> true get country by id
		// if (null != OrderModel.getCountry().getCountryId())
		// Order.setCountry(countryDAO.getById(OrderModel.getCountry().getCountryId()));
		// } else {
		// // update
		// Order = OrderDAO.getById(OrderModel.getOrderId());
		// Order.setOrderName(OrderModel.getName());
		// Order.setOrderCode(OrderModel.getCode());
		// Order.setStatus(OrderModel.getStatus());
		// // Order.setModifiedBy(modifiedBy); miss
		// if (null != OrderModel.getCountry().getCountryId())
		// Order.setCountry(countryDAO.getById(OrderModel.getCountry().getCountryId()));
		// Order.setModifiedDate(new Date());
		// }
		return null;
	}

	public OrderViewModel convertEntityToModel(final Order o) {
		if (null == o)
			return null;
		OrderViewModel vm = new OrderViewModel();
		vm.setOrderId(o.getOrderId());
		vm.setCheckSum(o.getCheckSum());
		vm.setStatus(o.getStatus());
		vm.setOrderCode(o.getOrderCode());
		vm.setOrderDate(o.getOrderDate());
		vm.setTotalPrice(o.getTotalPrice());

		Restaurant r = o.getRestaurant();
		if (r != null) {
			vm.setCityName(r.getCity());
			vm.setDistrictName(r.getDistrictName());
			vm.setRestaurantName(r.getName());
			vm.setOpenTime(r.getOpenTime());
			vm.setCloseTime(r.getCloseTime());
		}
		User u = o.getUser();
		if (u != null) {
			vm.setUserName(u.getFullName());
			vm.setEmail(u.getEmail());
		}
		vm.setOrder(o);
		return vm;
	}

	public boolean delete(Order c) {
		c.setStatus(Constant.Status.Deleted.getValue());

		return orderDAO.update(c);
	}

	@Transactional
	public List<OrderResponse> getOderByUser(Long userId) {
		List<Order> ods = orderDAO.getOderByUser(userId);
		List<OrderResponse> odrs = new LinkedList<>();

		// currency
		CurrencyResponse cur = currencyDAO.getByDefault();
		if (cur == null) {
			cur.setRate(1);
		}

		if (ods != null && ods.size() > 0) {
			ods.forEach(o -> {
				odrs.add(this.toModelResponse(o, cur.getSymbolLeft()));
			});
		}
		return odrs;
	}

	@Transactional
	public List<OrderResponse> getOrderByOwner(Long ownerId) {

		List<Order> ods = orderDAO.getOrderByOwner(ownerId);
		List<OrderResponse> odrs = new LinkedList<>();

		// currency
		CurrencyResponse cur = currencyDAO.getByDefault();
		if (cur == null) {
			cur.setRate(1);
		}

		if (ods != null && ods.size() > 0) {
			ods.forEach(o -> {
				odrs.add(this.toModelResponse(o, cur.getSymbolLeft()));
			});
		}
		return odrs;

	}

	@Transactional
	public List<OrderResponse> getAllOrder() {

		List<Order> ods = orderDAO.getAllOrder();
		List<OrderResponse> odrs = new LinkedList<>();

		// currency
		CurrencyResponse cur = currencyDAO.getByDefault();
		if (cur == null) {
			cur.setRate(1);
		}

		if (ods != null && ods.size() > 0) {
			ods.forEach(o -> {
				odrs.add(this.toModelResponse(o, cur.getSymbolLeft()));
			});
		}
		return odrs;

	}

	@Transactional
	public PaymentResponse getOderPaymentById(Long orderId, String orderCode) {
		Order o = orderDAO.getById(orderId);
		if (o == null)
			return null;
		if (o.getCheckSum() == null)
			return null;
		boolean isTrue = CommonHelper.CheckPw(orderId + "_" + orderCode, o.getCheckSum());
		if (!isTrue) {
			return null;
		}

		PaymentResponse ps = new PaymentResponse();

		OrderInfo orderInfo = orderInfoDAO.getByOrderId(orderId);

		if(orderInfo != null) {
			ps.setDistrict(orderInfo.getDistrict());
			ps.setCity(orderInfo.getCity());
			ps.setAddress(orderInfo.getAddress());
			ps.setAddressDesc(orderInfo.getAddressDesc());
			ps.setCompanyName(orderInfo.getCompanyName());
			ps.setDileverTime(orderInfo.getTime());
		}

		Restaurant r = o.getRestaurant();
		if (r != null) {
			ps.setName(r.getName());
			ps.setPhone1(r.getPhone1());
			ps.setAddressLine(r.getAddressLine());
			ps.setImageUrl(r.getImageUrl());
			ps.setLatitude(r.getLatitude());
			ps.setLongitude(r.getLongitude());
			ps.setRestaurantId(r.getRestaurantId());
		}
		ps.setOrderCode(o.getOrderCode());
		return ps;

	}

	private OrderResponse toModelResponse(Order o, String symbo) {
		OrderResponse or = new OrderResponse();
		or.setOrderId(o.getOrderId());
		if (o.getRestaurant() != null) {
			Restaurant r = o.getRestaurant();
			or.setRestaurantName(r.getName());
			or.setRestaurantId(r.getRestaurantId());
			or.setImageUrl(r.getImageUrl());
			or.setDeliveryCost(r.getDeliveryCost());
		}
		User u = o.getUser();
		if (u != null) {
			or.setUserName(u.getFullName());
			or.setEmail(u.getEmail());
			or.setPhone(u.getPhone());
		}
		or.setOrderDate(o.getOrderDate());
		or.setTotalPrice(o.getTotalPrice());
		or.setStatus(o.getStatus());
		or.setTaxTotal(o.getTaxTotal());
		or.setCurrencyCode(o.getCurrencyCode());
		or.setOrderCode(o.getOrderCode());
		or.setSymbolLeft(symbo);
		or.setPaymentWith(o.getPaymentWith());
		or.setReasonCancel(o.getReasonCancel());
		or.setReasonReject(o.getReasonReject());

		List<OrderInfoResponse> oirs = new LinkedList<>();
		Set<OrderInfo> ois = o.getOrderInfos();
		if (ois != null && ois.size() > 0) {
			for (OrderInfo oi : ois) {
				OrderInfoResponse oir = new OrderInfoResponse();
				oir.setInfoName(oi.getInfoName());
				oir.setInfoEmail(oi.getInfoEmail());
				oir.setInfoNumber(oi.getInfoNumber());
				oir.setCompanyName(oi.getCompanyName());
				oir.setAddress(oi.getAddress());
				oir.setCity(oi.getCity());
				oir.setDistrict(oi.getDistrict());
				oir.setTime(oi.getTime());
				oir.setRemark(oi.getRemark());
				oir.setAddressDescription(oi.getAddressDesc());
				oirs.add(oir);
			}
		}
		or.setOrderInfos(oirs);

//		Set<OrderLineItem> sol = o.getOrderLineItems();
//		List<OrderLineItemResponse> loli = new ArrayList<>();
//		if (sol != null && sol.size() > 0) {
//			sol.forEach(ol -> {
//				OrderLineItemResponse orl = new OrderLineItemResponse();
//				orl.setDiscountTotal(ol.getDiscountTotal());
//				orl.setMenuItemName(ol.getMenuItemName());
//				orl.setQuantity(ol.getQuantity());
//				orl.setTotal(ol.getTotal());
//				orl.setOrderLineItemId(ol.getOrderLineItemId());
//				orl.setUnitPrice(ol.getUnitPrice());
//				orl.setCreatedDate(ol.getCreatedDate());
//				loli.add(orl);
//			});
//
//			or.setOrderLineItems(loli);
//		}
		List<MenuItemLiteResponse> addlist = getDataMenuExtra(o.getOrderReq());
		or.setOrderLineItems(addlist);


		Set<OrderPayment> sop = o.getOrderPayments();
		if (sop != null && sop.size() > 0) {
			sop.forEach(p -> {
				or.setPaymentType(p.getOrderPaymentType());
			});

		}
		return or;
	}
	public static List<MenuItemLiteResponse> getDataMenuExtra(String jsonString){
		List<MenuItemLiteResponse> list = new ArrayList<>();
		Gson s = new Gson();
		PaymentRequest req = s.fromJson(jsonString, PaymentRequest.class);
		if (req != null && !req.getOrderItem().getOrderItemsRequest().isEmpty()) {
				list.addAll(req.getOrderItem().getOrderItemsRequest());
			return  list.size() != 0 ? list : null;
		}else{
			return null;
		}
	}

	@Transactional
	public ResponseGetPaging getByRestaurant(int pageIndex, int pageSize, Long restaurantId, Long status)
			throws Exception {
		int currentPage = (pageIndex < 1) ? 1 : pageIndex;
		int firstResult = (currentPage - 1) * pageSize;
		int maxResult = currentPage * pageSize;
		List<Order> result = orderDAO.getOrderBy(firstResult, maxResult, restaurantId, status);
		int totalRecord = orderDAO.countGetAll(restaurantId, status);

		ResponseGetPaging rs = new ResponseGetPaging();
		Data content = new Data();

		if (result == null || result.isEmpty()) {
			rs.setStatus(0);
			rs.setMessage("Could not found items.");
			content.setTotalCount(0);
			rs.setContent(content);
			rs.setErrorType(Constant.ErrorTypeCommon.NOT_FOUND_ITEM);
			return rs;
		}

		// currency
		CurrencyResponse cur = currencyDAO.getByDefault();
		if (cur == null) {
			cur.setRate(1);
		}

		List<OrderResponse> lmr = new LinkedList<>();
		if (result != null && !result.isEmpty()) {
			result.forEach(res -> {
				lmr.add(toModelResponse(res, cur.getSymbolLeft()));
			});
		}
		if (totalRecord > 0 && !lmr.isEmpty()) {
			content.setData(lmr);
			content.setPageIndex(pageIndex);
			content.setPageSize(pageSize);
			content.setTotalCount(totalRecord);
			rs.setContent(content);
			rs.setStatus(0);
			rs.setMessage("Success.");
		}
		return rs;
	}
}
