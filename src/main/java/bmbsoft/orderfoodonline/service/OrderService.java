package bmbsoft.orderfoodonline.service;

import bmbsoft.orderfoodonline.dao.CurrencyDAO;
import bmbsoft.orderfoodonline.dao.OrderDAO;
import bmbsoft.orderfoodonline.dao.OrderInfoDAO;
import bmbsoft.orderfoodonline.entities.*;
import bmbsoft.orderfoodonline.model.OrderInfoResponse;
import bmbsoft.orderfoodonline.model.OrderResponse;
import bmbsoft.orderfoodonline.model.OrderViewModel;
import bmbsoft.orderfoodonline.model.shared.*;
import bmbsoft.orderfoodonline.response.Data;
import bmbsoft.orderfoodonline.response.ResponseGetPaging;
import bmbsoft.orderfoodonline.util.CommonHelper;
import bmbsoft.orderfoodonline.util.Constant;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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

		return orderDAO.getAll(pageIndex, pageSize, orderCode, restaurantName, status);
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
			CurrencyResponse cur = currencyDAO.getByDefault() == null ? new CurrencyResponse(1d) : currencyDAO.getByDefault();
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
		List<OrderResponse> orderResponses = new LinkedList<>();

		// currency
		CurrencyResponse cur = currencyDAO.getByDefault() == null ? new CurrencyResponse(1d) : currencyDAO.getByDefault();

		if (ods != null && ods.size() > 0) {
			ods.forEach(o -> orderResponses.add(this.toModelResponse(o, cur.getSymbolLeft())));
		}
		return orderResponses;
	}

	@Transactional
	public List<OrderResponse> getOrderByOwner(Long ownerId) {

		List<Order> ods = orderDAO.getOrderByOwner(ownerId);
		List<OrderResponse> orderResponses = new LinkedList<>();

		// currency
		CurrencyResponse cur = currencyDAO.getByDefault() == null ? new CurrencyResponse(1d) : currencyDAO.getByDefault();

		if (ods != null && ods.size() > 0) {
			ods.forEach(o -> orderResponses.add(this.toModelResponse(o, cur.getSymbolLeft())));
		}
		return orderResponses;

	}

	@Transactional
	public List<OrderResponse> getAllOrder() {

		List<Order> ods = orderDAO.getAllOrder();
		List<OrderResponse> orderResponses = new LinkedList<>();

		// currency
		CurrencyResponse cur = currencyDAO.getByDefault();
		if (cur != null) {
			cur.setRate(1d);
		}

		if (ods != null && ods.size() > 0) {
			ods.forEach(o -> orderResponses.add(this.toModelResponse(o, Objects.requireNonNull(cur).getSymbolLeft())));
		}
		return orderResponses;

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

	private OrderResponse toModelResponse(Order o, String symbol) {
		OrderResponse or = new OrderResponse();
		or.setOrderId(o.getOrderId());
		if (o.getRestaurant() != null) {
			Restaurant r = o.getRestaurant();
			or.setRestaurantName(r.getName());
			or.setRestaurantId(r.getRestaurantId());
			or.setImageUrl(r.getImageUrl());
			or.setDeliveryCost(o.getDeliveryCost());
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
		or.setSymbolLeft(symbol);
		or.setPaymentWith(o.getPaymentWith());
		or.setDiscountPercent(o.getDiscount());
		or.setCharge(o.getChargeFee());
		or.setReasonCancel(o.getReasonCancel());
		or.setReasonReject(o.getReasonReject());
		or.setDiscountPercent(o.getDiscount());

		List<OrderInfoResponse> orderInfoResponses = new LinkedList<>();
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
				oir.setZone(oi.getZone());
				orderInfoResponses.add(oir);
			}
		}
		or.setOrderInfos(orderInfoResponses);

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
		List<MenuItemLiteResponse> menuItemLiteResponses = getDataMenuExtra(o.getOrderReq());
		or.setOrderLineItems(menuItemLiteResponses);


		Set<OrderPayment> sop = o.getOrderPayments();
		if (sop != null && sop.size() > 0) {
			sop.forEach(p -> or.setPaymentType(p.getOrderPaymentType()));

		}
		return or;
	}
	private static List<MenuItemLiteResponse> getDataMenuExtra(String jsonString){
		Gson s = new Gson();
		PaymentRequest req = s.fromJson(jsonString, PaymentRequest.class);
		if (req != null && !req.getOrderItem().getOrderItemsRequest().isEmpty()) {
			List<MenuItemLiteResponse> list = new ArrayList<>(req.getOrderItem().getOrderItemsRequest());
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
		CurrencyResponse cur = currencyDAO.getByDefault() == null ? new CurrencyResponse(1d) : currencyDAO.getByDefault();

		List<OrderResponse> lmr = new LinkedList<>();
		if (!result.isEmpty()) {
			result.forEach(res -> lmr.add(toModelResponse(res, cur.getSymbolLeft())));
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
